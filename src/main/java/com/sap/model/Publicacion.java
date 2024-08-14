package com.sap.model;

public class Publicacion {
    private int idPublicacion;
    private String titulo;
    private String descripcion;
    private double precio;
    private java.util.Date fechaPublicacion;
    private int idProducto;
    private int idUsuario;

    // Getters y setters
    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public java.util.Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(java.util.Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
