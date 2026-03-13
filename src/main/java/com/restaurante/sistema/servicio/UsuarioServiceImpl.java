package com.restaurante.sistema.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurante.sistema.dto.UsuarioDTO;
import com.restaurante.sistema.modelo.Usuario;
import com.restaurante.sistema.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    // "required = false" evita error si aún no has configurado SecurityConfig
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        // 1. Convertir DTO a Entidad
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        // NOTA: No seteamos 'apellido' porque tu entidad Usuario no lo tiene.
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setRol(usuarioDTO.getRol());

        // 2. Encriptar Contraseña (si el encoder existe)
        if (passwordEncoder != null && usuarioDTO.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        } else {
            usuario.setPassword(usuarioDTO.getPassword());
        }

        // 3. Guardar en Base de Datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 4. Actualizar ID en DTO y retornarlo
        usuarioDTO.setId(usuarioGuardado.getId());
        usuarioDTO.setPassword(null); // No devolvemos la password por seguridad
        return usuarioDTO;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        // Convertir lista de Entidades a lista de DTOs
        return usuarioRepository.findAll().stream().map(usuario -> {
            return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getRol()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return new UsuarioDTO(
            usuario.getId(), 
            usuario.getNombre(), 
            usuario.getUsername(), 
            usuario.getRol()
        );
    }

    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}