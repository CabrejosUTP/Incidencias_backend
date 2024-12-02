package com.example.app_incidencias_backend_v2.dao;

import com.example.app_incidencias_backend_v2.dto.request.ClienteRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClienteDao {

    private final Logger logger = LoggerFactory.getLogger(ClienteDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder encoder;

    public ClienteDao(JdbcTemplate jdbcTemplate, PasswordEncoder encoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.encoder = encoder;
    }

    public List<Object> listarClientes() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ListarClientes").withoutProcedureColumnMetaDataAccess();
            Map<String, Object> response = simpleJdbcCall.execute();
            logger.debug(response.toString());
            return (List<Object>) response.get("#result-set-1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void registraCliente(ClienteRequestDto clienteRequestDto) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("RegistrarCliente")
                    .declareParameters(
                            new SqlParameter("_nombre", Types.VARCHAR),
                            new SqlParameter("_apellido", Types.VARCHAR),
                            new SqlParameter("_direccion", Types.VARCHAR),
                            new SqlParameter("_telefono", Types.VARCHAR),
                            new SqlParameter("_usuario", Types.VARCHAR),
                            new SqlParameter("_contrasena", Types.VARCHAR)
                    );

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_nombre", clienteRequestDto.getNombre());
            parameters.put("_apellido", clienteRequestDto.getApellido());
            parameters.put("_direccion", clienteRequestDto.getDireccion());
            parameters.put("_telefono", clienteRequestDto.getTelefono());
            parameters.put("_usuario", clienteRequestDto.getUsuario());
            parameters.put("_contrasena", encoder.encode(clienteRequestDto.getContrasena()));

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
