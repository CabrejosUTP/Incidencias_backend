package com.example.app_incidencias_backend_v2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DispositivoResponseDto {
    private Integer idDispositivo;
    private String nombre;
    private String ubicacionReferencial;
    private Integer idContrato;
}
