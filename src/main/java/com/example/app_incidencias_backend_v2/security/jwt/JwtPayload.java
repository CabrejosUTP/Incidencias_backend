package com.example.app_incidencias_backend_v2.security.jwt;

import com.example.app_incidencias_backend_v2.dto.response.ClienteResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.RolResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.UsuarioResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtPayload {
    private Integer idCuenta;
    private String nombreUsuario;
    private RolResponseDto rol;
    private UsuarioResponseDto usuario;
    private ClienteResponseDto cliente;
}
