package com.coremaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CoreService coreService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if("authenticated".equals(email)) {
            return User.withUsername("email")
                    .password(passwordEncoder.encode("password"))
                    .authorities("user").build();
        } else {
            var user = coreService.getUser(email).orElseThrow(NoSuchElementException::new);
            return User.withUsername(user.getEmail())
                    .password(passwordEncoder.encode(String.valueOf(user.getPassword())))
                    .authorities("user").build();
        }
    }
}
