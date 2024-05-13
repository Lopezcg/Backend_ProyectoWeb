package com.backend.backend_web.jwt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Set<GrantedAuthority> authorities =  new HashSet<>();
        authorities.add(  new SimpleGrantedAuthority("Prueba")  ) ;

        return new org.springframework.security.core.userdetails.User(
                                                                            usernameOrEmail,
                                                                            usernameOrEmail,
                                                                            authorities
                                                                    );
    }
}