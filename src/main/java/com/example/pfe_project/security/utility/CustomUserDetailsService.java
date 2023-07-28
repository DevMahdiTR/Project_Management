package com.example.pfe_project.security.utility;

import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class
CustomUserDetailsService  implements UserDetailsService {


    private  final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return userRepository.fetchUserWithEmail(email).orElseThrow(() -> new ResourceNotFoundException("The email address provided could not be found in our system."));
    }

}

