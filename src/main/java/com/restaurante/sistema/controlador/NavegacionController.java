package com.restaurante.sistema.controlador;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurante.sistema.dto.UsuarioDTO;
import com.restaurante.sistema.modelo.Pedido;
import com.restaurante.sistema.modelo.Plato;
import com.restaurante.sistema.modelo.Usuario;
import com.restaurante.sistema.repositorio.UsuarioRepositorio;

@Controller
@RequestMapping("/vistas")
public class NavegacionController {

    // --- INYECCIONES NECESARIAS PARA GUARDAR ---
    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- GERENTE: MOSTRAR FORMULARIO (GET) ---
    @GetMapping("/gerente/registro")
    public String verRegistroPersonal(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "gerente/registro-empleado"; 
    }

    // --- GERENTE: PROCESAR REGISTRO (POST) - ¡ESTO FALTABA! ---
    @PostMapping("/gerente/registro")
    public String registrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO) {
        // 1. Crear la entidad Usuario a partir del DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setUsername(usuarioDTO.getUsername());
        nuevoUsuario.setRol(usuarioDTO.getRol());
        
        // 2. ENCRIPTAR LA CONTRASEÑA (Obligatorio para Spring Security)
        String passEncriptada = passwordEncoder.encode(usuarioDTO.getPassword());
        nuevoUsuario.setPassword(passEncriptada);

        // 3. Guardar en la Base de Datos
        usuarioRepository.save(nuevoUsuario);

        // 4. Redireccionar con un mensaje de éxito
        return "redirect:/vistas/gerente/registro?exito";
    }

    // --- GERENTE: INSUMOS ---
    @GetMapping("/gerente/insumos")
    public String verInsumos(Model model) {
        model.addAttribute("insumos", new ArrayList<>()); 
        return "insumo/listaInsumo"; 
    }

    // --- GERENTE: MENÚ ---
    @GetMapping("/gerente/menu")
    public String verMenu(Model model) {
        model.addAttribute("platos", new ArrayList<Plato>()); 
        model.addAttribute("plato", new Plato());
        return "menu/lista_menu"; 
    }

    // --- GERENTE: REPORTES ---
    @GetMapping("/gerente/reportes")
    public String verReportes() {
        return "insumo/reporteInventario"; 
    }

    // --- PEDIDOS: NUEVO PEDIDO ---
    @GetMapping("/pedido/Listar") // Corregí la doble barra '//' que tenías
    public String verNuevoPedido(Model model) {
        return "pedido/menupedido"; 
    }
}