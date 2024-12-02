package com.example.app_incidencias_backend_v2.service;

import com.example.app_incidencias_backend_v2.dao.ClienteDao;
import com.example.app_incidencias_backend_v2.dto.request.ClienteRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteDao clienteDao;

    public ClienteService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public List<Object> listarClientes() {
        return this.clienteDao.listarClientes();
    }

    public void registrarCliente(ClienteRequestDto clienteRequestDto) {
        this.clienteDao.registraCliente(clienteRequestDto);
    }
}
