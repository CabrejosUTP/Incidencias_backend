package com.example.app_incidencias_backend_v2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolResponseDto {
    private Integer idRol;
    private String nombre;
}
