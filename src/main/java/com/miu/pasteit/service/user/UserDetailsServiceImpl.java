package com.miu.pasteit.service.user;

import com.miu.pasteit.model.entity.db.sql.UserRoles;
import com.miu.pasteit.repository.mysql.UserRepository;
import com.miu.pasteit.repository.mysql.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<SimpleGrantedAuthority> roles = user.getRoles()
                .stream()
                .map(UserRoles::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), roles);
    }
}
