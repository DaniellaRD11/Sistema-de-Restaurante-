package com.restaurante.sistema.dto;

public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String username;
    private String password;
    private String rol;

    // --- CONSTRUCTORES ---
    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String nombre, String username, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.rol = rol;
    }

    // --- GETTERS Y SETTERS ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}