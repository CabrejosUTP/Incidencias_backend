package com.example.app_incidencias_backend_v2.dao;

import com.example.app_incidencias_backend_v2.dto.response.ClienteResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.CuentaResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.RolResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.UsuarioResponseDto;
import com.example.app_incidencias_backend_v2.security.jwt.JwtPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthDao {

    private final Logger logger = LoggerFactory.getLogger(AuthDao.class);

    private final JdbcTemplate jdbcTemplate;

    public AuthDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CuentaResponseDto> buscarCuentaPorNombreUsuario(String username) {
        try {
            String sql = "SELECT \n" +
                    "    c.nombre_usuario,\n" +
                    "    c.contrasena,\n" +
                    "    r.nombre AS nombre_rol\n" +
                    "FROM Cuenta c\n" +
                    "INNER JOIN Rol r ON (r.id_rol = c.id_rol)\n" +
                    "WHERE c.nombre_usuario = ?";

            RowMapper<CuentaResponseDto> rowMapper = (rs, rowNum) -> CuentaResponseDto.builder()
                    .username(rs.getString("nombre_usuario"))
                    .password(rs.getString("contrasena"))
                    .rol(rs.getString("nombre_rol"))
                    .build();

            List<CuentaResponseDto> cuentas = jdbcTemplate.query(sql, rowMapper, username);
            Optional<CuentaResponseDto> response = cuentas.isEmpty() ? Optional.empty() : Optional.of(cuentas.get(0));

            logger.info(response.toString());

            return response;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<JwtPayload> obtenerJwtPayload(String username) {
        try {
            String sql = "SELECT \n" +
                    "\tc.id_cuenta,\n" +
                    "    c.nombre_usuario AS nombre_usuario_cuenta,\n" +
                    "    r.id_rol,\n" +
                    "    r.nombre AS nombre_rol,\n" +
                    "    u.id_usuario,\n" +
                    "    u.nombre AS nombre_usuario,\n" +
                    "    cl.id_cliente,\n" +
                    "    cl.nombre AS nombre_cliente,\n" +
                    "    cl.apellido AS apellido_cliente,\n" +
                    "    cl.direccion AS direccion_cliente,\n" +
                    "    cl.telefono AS telefono_cliente\n" +
                    "FROM Cuenta c\n" +
                    "INNER JOIN Rol r ON (r.id_rol = c.id_rol)\n" +
                    "LEFT JOIN Usuario u ON (u.cuenta_id = c.id_cuenta)\n" +
                    "LEFT JOIN Cliente cl ON (cl.cuenta_id = c.id_cuenta)\n" +
                    "WHERE c.nombre_usuario = ?";

            RowMapper<JwtPayload> rowMapper = (rs, rowNum) -> {
                UsuarioResponseDto usuario = null;
                if (rs.getInt("id_usuario") != 0) {
                    usuario = UsuarioResponseDto.builder()
                            .idUsuario(rs.getInt("id_usuario"))
                            .nombre(rs.getString("nombre_usuario"))
                            .build();
                }

                ClienteResponseDto cliente = null;
                if (rs.getInt("id_cliente") != 0) {
                    cliente = ClienteResponseDto.builder()
                            .idCliente(rs.getInt("id_cliente"))
                            .nombre(rs.getString("nombre_cliente"))
                            .apellido(rs.getString("apellido_cliente"))
                            .direccion(rs.getString("direccion_cliente"))
                            .telefono(rs.getString("telefono_cliente"))
                            .build();
                }

                return JwtPayload.builder()
                        .idCuenta(rs.getInt("id_cuenta"))
                        .nombreUsuario(rs.getString("nombre_usuario_cuenta"))
                        .rol(RolResponseDto.builder()
                                .idRol(rs.getInt("id_rol"))
                                .nombre(rs.getString("nombre_rol"))
                                .build())
                        .usuario(usuario)
                        .cliente(cliente)
                        .build();
            };

            List<JwtPayload> cuentas = jdbcTemplate.query(sql, rowMapper, username);
            Optional<JwtPayload> response = cuentas.isEmpty() ? Optional.empty() : Optional.of(cuentas.get(0));

            logger.info(response.toString());

            return response;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }
}
