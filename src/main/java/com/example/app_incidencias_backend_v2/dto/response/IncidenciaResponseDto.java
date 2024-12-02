package com.example.app_incidencias_backend_v2.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class IncidenciaResponseDto {
    private Integer idIncidencia;
    private String asunto;
    private String detalle;
    private String imagen;
    private Integer calificacion;
    private String pendiente;
    private String presencial;
    private String solucionado;
    private String fechaRegistro;
    private String usuarioAsignado;
    private ContratoResponseDto contrato;
}
