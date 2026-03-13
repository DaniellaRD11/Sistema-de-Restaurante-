package com.restaurante.sistema.repositorio;

import com.restaurante.sistema.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    
    // Método extra para buscar por nombre de usuario (útil para login)
    Optional<Usuario> findByUsername(String username);
    
}