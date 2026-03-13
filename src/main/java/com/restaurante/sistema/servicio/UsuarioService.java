package com.restaurante.sistema.servicio;

import com.restaurante.sistema.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {

    UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO obtenerPorId(Integer id);

    void eliminarUsuario(Integer id);
}