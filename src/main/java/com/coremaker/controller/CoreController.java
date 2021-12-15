package com.coremaker.controller;

import com.coremaker.model.User;
import com.coremaker.security.JwtTokenProvider;
import com.coremaker.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@Slf4j
public class CoreController {
    private final CoreService coreService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public CoreController(CoreService coreService, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.coreService = coreService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coreService.createUser(user));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = tokenProvider.generateToken(authentication);
        coreService.saveJwtToken(jwtToken);
        return ResponseEntity.ok("{ \"Bearer\" : \"" + jwtToken + "\"}");
    }


    @GetMapping("/auth/getAllAccounts")
    public ResponseEntity<List<User>> getAllAccounts() {
        return ResponseEntity.ok(coreService.getAllAccounts());
    }
}
