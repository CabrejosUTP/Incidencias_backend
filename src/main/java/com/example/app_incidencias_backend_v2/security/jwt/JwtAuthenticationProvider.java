package com.example.app_incidencias_backend_v2.security.jwt;

import com.example.app_incidencias_backend_v2.dto.response.CuentaResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtAuthenticationProvider {

    @Value("${jwt-secret}")
    private String jwtsecret;

    @Value("${jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication, JwtPayload payload) {
        String usuario = authentication.getName();

        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(payload, Map.class);

        return Jwts.builder()
                .setSubject(usuario)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .claim("user", map)
                .signWith(Keys.hmacShaKeyFor(jwtsecret.getBytes()))
                .compact();
    }

    public String obtenerUsuarioDelJwt(String token) {
        if (validarToken(token)) {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtsecret.getBytes())).build();
            Claims claims = parser.parseClaimsJws(token).getBody();
            return claims.getSubject();
        }

        return null;
    }

    public boolean validarToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtsecret.getBytes())).build();
            parser.parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Firma JWT no valida");
        } catch (MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jwt no valido");
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jwt expirado");
        } catch (UnsupportedJwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jwt no compatible");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jwt incompleto");
        }
    }
}
