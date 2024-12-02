package com.example.app_incidencias_backend_v2.controller;

import com.example.app_incidencias_backend_v2.dto.request.ClienteRequestDto;
import com.example.app_incidencias_backend_v2.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @RequestMapping(path = "/registrar", method = RequestMethod.POST)
    public ResponseEntity<?> listarClientes(@RequestBody ClienteRequestDto clienteRequestDto) {
        clienteService.registrarCliente(clienteRequestDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
