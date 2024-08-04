package org.example.ecommerceapp.Features.Authentication.Controller;
import org.example.ecommerceapp.Features.Authentication.DTOs.LoginResponse;
import org.example.ecommerceapp.Features.Authentication.Service.AuthenticationService;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/ecommerce/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;


    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login/PK")
    public String get(

    ) {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> registerUser(@RequestBody UserEntity userEntity) {
        LoginResponse response = authenticationService.register(userEntity);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserEntity request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
