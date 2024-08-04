package org.example.ecommerceapp.Features.Authentication.Service;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;

import org.example.ecommerceapp.Exception.LoginFailedException;
import org.example.ecommerceapp.Features.Authentication.DTOs.LoginResponse;
import org.example.ecommerceapp.Features.Authentication.Entity.TokenEntity;
import org.example.ecommerceapp.Features.Authentication.Repository.TokenRepository;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;
import org.example.ecommerceapp.Features.User.Repository.UserRepository;
import org.example.ecommerceapp.JwtService.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }


    public LoginResponse register(UserEntity request) {
        try {
            validateRegistrationRequest(request);
            if (repository.findByUserName(request.getUsername()).isPresent()) {
                return new LoginResponse(null, null, "User already exists");
            }

            UserEntity user = new UserEntity();
            user.setEmail(request.getEmail());
            user.setUserName(request.getUsername());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setMobileNumber(request.getMobileNumber());
            user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));

            user = repository.save(user);
            logger.debug("UserEntity after save: {}", user);
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new LoginResponse(accessToken, refreshToken, "User registration was successful");
        }catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }

    public LoginResponse authenticate(UserEntity request) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getUserPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserEntity user = (UserEntity) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new LoginResponse(accessToken, refreshToken, "User login was successful");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
 private void saveUserToken(String accessToken, String refreshToken, UserEntity user) {
        TokenEntity token = new TokenEntity();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setIsLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
    private void validateRegistrationRequest(UserEntity request) {
        if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
            throw new LoginFailedException("Username, email, and password are required");
        }

    }
}