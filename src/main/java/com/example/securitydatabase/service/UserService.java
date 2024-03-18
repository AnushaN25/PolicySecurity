package com.example.securitydatabase.service;
import com.example.securitydatabase.entity.User;

import com.example.securitydatabase.exception.ResourceDBException;
import com.example.securitydatabase.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
    private PasswordEncoder passwordEncoder;


	public User registerUser(User user) {
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public User updateUser(User user, Integer userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update user details with new values from userDto
            existingUser.setUserName(user.getUsername());
            existingUser.setPassword(user.getPassword());
            // Set other fields to update here...

            return userRepo.save(existingUser); // Save and return updated user
        } else {
            throw new RuntimeException("User not found");
        }
	}

	public User getUserById(Integer userId) {
		return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public void deleteUser(Integer userId) {
		 if (userRepo.existsById(userId)) {
	            userRepo.deleteById(userId);
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    }
		
	public User loginUser(User user) {
		Optional<User> userOptional = userRepo.findByEmail(user.getEmail());
      if (userOptional.isPresent()) {
          User existingUser = userOptional.get();
          if (existingUser.getPassword().equals(user.getPassword())) {
             
              return existingUser;
          } else {
              
              throw new RuntimeException("Invalid username or password");
          }
      } else {
          
          throw new RuntimeException("Invalid username or password");
      }
		 
		
	}
}
