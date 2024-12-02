package com.example.app_incidencias_backend_v2.dao;

import com.example.app_incidencias_backend_v2.dto.request.VincularDispositivoRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DispositivoDao {

    private final Logger logger = LoggerFactory.getLogger(DispositivoDao.class);

    private final JdbcTemplate jdbcTemplate;

    public DispositivoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Object> obtenerDispositivosPorCliente(Integer idCliente) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ObtenerDispositivosPorCliente")
                    .declareParameters(new SqlParameter("_id_cliente", Types.INTEGER));

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_cliente", idCliente);

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
            return (List<Object>) response.get("#result-set-1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Object> listarDispositivos(String nombre) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ListarDispositivos")
                    .declareParameters(new SqlParameter("_nombre", Types.VARCHAR));

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_nombre", nombre);

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
            return (List<Object>) response.get("#result-set-1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void vincularDispositivo(VincularDispositivoRequestDto vincularDispositivoRequestDto) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("VincularDispositivo")
                    .declareParameters(
                            new SqlParameter("_id_cliente", Types.INTEGER),
                            new SqlParameter("_id_dispositivo", Types.INTEGER),
                            new SqlParameter("_ubicacion_referencial", Types.VARCHAR)
                    );

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_cliente", vincularDispositivoRequestDto.getIdCliente());
            parameters.put("_id_dispositivo", vincularDispositivoRequestDto.getIdDispositivo());
            parameters.put("_ubicacion_referencial", vincularDispositivoRequestDto.getUbicacionReferencial());

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
