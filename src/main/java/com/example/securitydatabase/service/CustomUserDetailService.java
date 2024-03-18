package com.example.securitydatabase.service;

import com.example.securitydatabase.entity.User;
import com.example.securitydatabase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    //loads user from database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=  userRepo.findByEmail(username).orElseThrow(()-> new RuntimeException("user not found"));
         return user;
    }
}
