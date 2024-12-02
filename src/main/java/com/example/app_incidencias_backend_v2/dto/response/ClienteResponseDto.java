package com.example.app_incidencias_backend_v2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponseDto {
    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
}
