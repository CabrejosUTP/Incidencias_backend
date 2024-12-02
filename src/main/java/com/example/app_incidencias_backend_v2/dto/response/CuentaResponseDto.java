package com.example.app_incidencias_backend_v2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDto {

    private String username;
    private String password;
    private String rol;

}
