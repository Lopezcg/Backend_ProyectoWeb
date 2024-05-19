package com.backend.backend_web.jwt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        if (!StringUtils.hasText(usernameOrEmail)) {
            throw new UsernameNotFoundException("Username or email cannot be null or empty");
        }
        Set<GrantedAuthority> authorities =  new HashSet<>();
        authorities.add(  new SimpleGrantedAuthority("ROLE_USER")  ) ;

        return new org.springframework.security.core.userdetails.User(
                                                                            usernameOrEmail,
                                                                            usernameOrEmail,
                                                                            authorities
                                                                    );
    }
}