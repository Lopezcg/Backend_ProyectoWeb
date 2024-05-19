package com.backend.backend_web.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.dto.ArrendatarioDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService {
    // @Value("${jwt.secret}")
    // private String secret = "DES6123";

    // @Value("${jwt.expiration}")
    private long jwtExpiration = 99999999;
    private Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);; // You need to set this key appropriately

    public String generarToken(ArrendadorDTO usuario) {

        // byte[] secretBytes = secret.getBytes();
        // Key jwtKey = new SecretKeySpec(secretBytes,
        // SignatureAlgorithm.HS512.getJcaName());
        ObjectMapper objectMapper = new ObjectMapper();
        String username = "";
        try {
            username = objectMapper.writeValueAsString(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .signWith(jwtKey, SignatureAlgorithm.HS512) // Use your appropriate signing algorithm
                .compact();
    }

    public String generarToken(ArrendatarioDTO usuario) {
        // byte[] secretBytes = secret.getBytes();
        // Key jwtKey = new SecretKeySpec(secretBytes,
        // SignatureAlgorithm.HS512.getJcaName());
        ObjectMapper objectMapper = new ObjectMapper();
        String username = "";
        try {
            username = objectMapper.writeValueAsString(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        System.out.println(username + "SI HP ACA");
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .signWith(jwtKey, SignatureAlgorithm.HS512) // Use your appropriate signing algorithm
                .compact();
    }

    public boolean validarToken(String jwtToken) {
        try {
            decodificarToken(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String jwtToken) {
        return decodificarToken(jwtToken).getSubject();
    }

    public Date getFechaExpiracion(String jwtToken) {
        return decodificarToken(jwtToken).getExpiration();
    }

    public Claims decodificarToken(String jwtToken) {
        // byte[] secretBytes = secret.getBytes();
        // Key jwtKey = new SecretKeySpec(secretBytes,
        // SignatureAlgorithm.HS512.getJcaName());

        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}