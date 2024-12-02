package com.example.app_incidencias_backend_v2.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiExceptionDto {
    private Boolean ok;
    private String message;
    private String timestamp;
}
