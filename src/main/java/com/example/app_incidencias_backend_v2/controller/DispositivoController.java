package com.example.app_incidencias_backend_v2.controller;

import com.example.app_incidencias_backend_v2.dto.request.VincularDispositivoRequestDto;
import com.example.app_incidencias_backend_v2.service.DispositivoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @RequestMapping(path = "/cliente/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> obtenerDispositivosPorCliente(@PathVariable("id") Integer idCliente) {
        return ResponseEntity.ok(dispositivoService.obtenerDispositivosPorCliente(idCliente));
    }

    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    private ResponseEntity<?> listarDispositivos(@RequestParam(name = "nombre", required = false) String nombre) {
        return ResponseEntity.ok(dispositivoService.listarDispositivos(nombre));
    }

    @RequestMapping(path = "/cliente/vincular", method = RequestMethod.POST)
    private ResponseEntity<?> vincularDispositivo(@RequestBody VincularDispositivoRequestDto vincularDispositivoRequestDto) {
        dispositivoService.vincularDispositivo(vincularDispositivoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
