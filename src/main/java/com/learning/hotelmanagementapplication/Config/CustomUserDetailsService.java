package com.learning.hotelmanagementapplication.Config;

import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.User;
import com.learning.hotelmanagementapplication.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("user not found!");
        }
        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}
