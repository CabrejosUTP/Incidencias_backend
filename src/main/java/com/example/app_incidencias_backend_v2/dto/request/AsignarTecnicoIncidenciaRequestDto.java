package com.example.app_incidencias_backend_v2.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AsignarTecnicoIncidenciaRequestDto {
    private Integer idIncidencia;
    private Integer idTecnico;
}
