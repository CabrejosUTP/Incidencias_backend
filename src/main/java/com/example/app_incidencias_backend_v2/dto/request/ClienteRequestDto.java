package com.example.app_incidencias_backend_v2.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteRequestDto {
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String usuario;
    private String contrasena;
}
