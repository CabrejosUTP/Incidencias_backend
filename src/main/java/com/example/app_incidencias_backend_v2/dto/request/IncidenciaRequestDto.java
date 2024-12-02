package com.example.app_incidencias_backend_v2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaRequestDto {
    private String asunto;
    private String detalle;
    private String usuarioAsignado;
    private Integer idContrato;
}
