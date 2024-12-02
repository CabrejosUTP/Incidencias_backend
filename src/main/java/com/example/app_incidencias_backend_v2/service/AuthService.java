package com.example.app_incidencias_backend_v2.service;

import com.example.app_incidencias_backend_v2.dao.AuthDao;
import com.example.app_incidencias_backend_v2.dto.request.LoginRequestDto;
import com.example.app_incidencias_backend_v2.dto.response.CuentaResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.LoginResponseDto;
import com.example.app_incidencias_backend_v2.security.UserDetailService;
import com.example.app_incidencias_backend_v2.security.jwt.JwtAuthenticationProvider;
import com.example.app_incidencias_backend_v2.security.jwt.JwtPayload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationProvider authenticationProvider;
    private final AuthDao authDao;
    private final UserDetailService userDetailService;

    public AuthService(AuthenticationManager authenticationManager, JwtAuthenticationProvider authenticationProvider, AuthDao authDao, UserDetailService userDetailService) {
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
        this.authDao = authDao;
        this.userDetailService = userDetailService;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(), loginRequestDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtPayload payload = authDao.obtenerJwtPayload(loginRequestDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String token = authenticationProvider.generarToken(authentication, payload);

        return LoginResponseDto.builder()
                .tipoToken("Bearer")
                .token(token)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
