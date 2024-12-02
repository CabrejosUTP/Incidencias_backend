package com.example.app_incidencias_backend_v2.security.jwt;


import com.example.app_incidencias_backend_v2.security.UserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private UserDetailService userDetailService;

    public JwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider, UserDetailService userDetailService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = obtenerJwtDeLaSolicitud(request);

        if (StringUtils.hasText(token) && jwtAuthenticationProvider.validarToken(token)) {
            String usuario = jwtAuthenticationProvider.obtenerUsuarioDelJwt(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(usuario);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerJwtDeLaSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
            return bearerToken.substring(7);
        return null;
    }
}
