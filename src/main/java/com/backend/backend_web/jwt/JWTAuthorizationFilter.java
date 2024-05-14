package com.backend.backend_web.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.backend_web.service.JWTTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
@Service
public class JWTAuthorizationFilter extends OncePerRequestFilter{


    public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";

    @Autowired
	private CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTTokenService jwtTokenService;

	

	@Override
	protected void doFilterInternal( @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
		System.out.println( "-------->>>-------->>> Filtro"  );
		System.out.println( "-------->>>-------->>> Filtro"  );
		System.out.println( "-------->>>-------->>> Filtro"  );
		System.out.println( "-------->>>-------->>> Filtro"  );
		try {
			if (existeJWTToken(request)) {
				Claims claims = validarToken(request);
				if (claims.get("authorities") != null) {
					String username = getUsername(request);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, userDetails, null);
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           			SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
	}	

	private Claims validarToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return jwtTokenService.decodificarToken(jwtToken);
	}
	private String getUsername(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return jwtTokenService.getUsername(jwtToken);
	}
	private boolean existeJWTToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX));
	}
}