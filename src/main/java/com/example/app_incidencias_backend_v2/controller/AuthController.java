package com.example.app_incidencias_backend_v2.controller;

import com.example.app_incidencias_backend_v2.dto.request.LoginRequestDto;
import com.example.app_incidencias_backend_v2.dto.response.LoginResponseDto;
import com.example.app_incidencias_backend_v2.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody() LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }
}
