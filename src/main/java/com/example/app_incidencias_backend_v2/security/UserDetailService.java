package com.example.app_incidencias_backend_v2.security;

import com.example.app_incidencias_backend_v2.dao.AuthDao;
import com.example.app_incidencias_backend_v2.dto.response.CuentaResponseDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    private final AuthDao authDao;

    public UserDetailService(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CuentaResponseDto cuentaResponseDto = authDao.buscarCuentaPorNombreUsuario(username).orElse(null);
        if (cuentaResponseDto != null)
            return new User(
                    cuentaResponseDto.getUsername(),
                    cuentaResponseDto.getPassword(),
                    mapearRoles(Collections.singletonList(cuentaResponseDto.getRol()))
            );
        else throw new UsernameNotFoundException("El usuario no existe");
    }

    public Collection<? extends GrantedAuthority> mapearRoles(List<String> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority("ROLE_" + rol))
                .collect(Collectors.toList());
    }
}
