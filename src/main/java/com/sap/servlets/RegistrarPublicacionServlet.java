package com.sap.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.dao.PublicacionDAO;
import com.sap.model.Publicacion;

@WebServlet("/RegistrarPublicacionServlet")
public class RegistrarPublicacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario de publicación
        String titulo = request.getParameter("titulo");
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        // Crear un nuevo objeto Publicacion
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(titulo);
        publicacion.setFechaPublicacion(new Date());
        publicacion.setIdProducto(idProducto);

        // Usar PublicacionDAO para guardar la publicación en la base de datos
        PublicacionDAO publicacionDAO = new PublicacionDAO();
        publicacionDAO.agregarPublicacion(publicacion);

        // Redirigir a una página de éxito o mostrar un mensaje de confirmación
        response.sendRedirect("publicacion-exitosa.html");
    }
}
