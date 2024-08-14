package com.sap.model;

public class Contacto {
    private String nombre;
    private String telefono;
    private String correo;
    private String soyUn;
    private String mensaje;

    public Contacto(String nombre, String telefono, String correo, String soyUn, String mensaje) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.soyUn = soyUn;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSoyUn() {
        return soyUn;
    }

    public void setSoyUn(String soyUn) {
        this.soyUn = soyUn;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
