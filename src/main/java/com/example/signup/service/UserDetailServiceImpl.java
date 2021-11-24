package com.example.signup.service;

import com.example.signup.model.User;
import com.example.signup.repository.UserRepository;
import com.example.signup.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->new UsernameNotFoundException("No user with email '" + email + "' found"));
        return new UserDetailsImpl(user);
    }
}
