package com.example.app_incidencias_backend_v2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDto {
    private Integer idUsuario;
    private String nombre;
}
