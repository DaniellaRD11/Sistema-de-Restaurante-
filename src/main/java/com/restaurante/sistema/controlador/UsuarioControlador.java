package com.restaurante.sistema.controlador;

import com.restaurante.sistema.dto.UsuarioDTO;
import com.restaurante.sistema.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios") // Prefijo para todas las rutas de usuario
public class UsuarioControlador {

    @Autowired
    private UsuarioService usuarioService;

    // --- 1. LISTAR USUARIOS ---
    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuario/listarUsuarios"; // Asegúrate de tener este HTML
    }

    // --- 2. FORMULARIO NUEVO USUARIO ---
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        // Pasamos un DTO vacío al formulario
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "usuario/registroUsuario"; // Tu HTML de registro
    }

    // --- 3. GUARDAR USUARIO ---
    @PostMapping("/guardar")
    public String registrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
                                   RedirectAttributes redirectAttributes) {
        try {
            usuarioService.registrarUsuario(usuarioDTO);
            redirectAttributes.addFlashAttribute("exito", "Usuario registrado con éxito.");
            return "redirect:/usuarios/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "redirect:/usuarios/nuevo";
        }
    }

    // --- 4. ELIMINAR USUARIO ---
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("exito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/usuarios/listar";
    }
}