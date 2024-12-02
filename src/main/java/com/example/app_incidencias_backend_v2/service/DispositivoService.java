package com.example.app_incidencias_backend_v2.service;

import com.example.app_incidencias_backend_v2.dao.DispositivoDao;
import com.example.app_incidencias_backend_v2.dto.request.VincularDispositivoRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositivoService {

    private final DispositivoDao dispositivoDao;

    public DispositivoService(DispositivoDao dispositivoDao) {
        this.dispositivoDao = dispositivoDao;
    }

    public List<Object> obtenerDispositivosPorCliente(Integer idCliente) {
        return dispositivoDao.obtenerDispositivosPorCliente(idCliente);
    }

    public List<Object> listarDispositivos(String nombre) {
        return dispositivoDao.listarDispositivos(nombre);
    }

    public void vincularDispositivo(VincularDispositivoRequestDto vincularDispositivoRequestDto) {
        dispositivoDao.vincularDispositivo(vincularDispositivoRequestDto);
    }
}
