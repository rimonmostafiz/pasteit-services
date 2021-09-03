package com.miu.pasteit.service.user;

import com.miu.pasteit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", username)));

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
