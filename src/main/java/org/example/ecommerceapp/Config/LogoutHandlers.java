package org.example.ecommerceapp.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import org.example.ecommerceapp.Constants.AppConstants;
import org.example.ecommerceapp.Features.Authentication.Entity.TokenEntity;
import org.example.ecommerceapp.Features.Authentication.Repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class LogoutHandlers implements LogoutHandler {

    final TokenRepository tokenRepository;

    public LogoutHandlers(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(AppConstants.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(AppConstants.TOKEN)) {
            return;

        }
        String token = authHeader.substring(7);
        TokenEntity storedToken = tokenRepository.findByAccessToken(token).orElse(null);
        if (storedToken != null) {
            storedToken.setIsLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}
