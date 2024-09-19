package com.enrique.login.controller;


import com.enrique.login.entity.User;
import com.enrique.login.entity.request.AuthRequest;
import com.enrique.login.entity.request.AuthResponse;
import com.enrique.login.service.AuthService;
import com.enrique.login.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService tokenProvider;
    @Autowired
    private AuthService authService;


    public AuthController(AuthenticationManager authenticationManager, JwtService tokenProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = "";
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);

            token = tokenProvider.generateToken(authentication);
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Credenciales incorrectas"));

        }

        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Registro de usuarios
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest authRequest) {

        if (authService.existsByUsername(authRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        }
        User user = new User(null,authRequest.getUsername(),authRequest.getPassword(),"USER");
        try {
            authService.saveUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while registering user");
        }

    }

}
