package com.sap.servlets;

import com.sap.dao.UsuarioDAO;
import com.sap.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ObtenerDatosUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el ID del usuario desde los parámetros de la solicitud
        String idUsuarioParam = request.getParameter("idUsuario");
        int idUsuario = Integer.parseInt(idUsuarioParam);
        
        // Obtener los datos del usuario desde la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);

        // Verificar si el usuario existe
        if (usuario != null) {
            // Crear una respuesta con los datos del usuario
            String responseText = "Datos de contacto:\n" +
                                  "Nombre: " + usuario.getNombre() + "\n" +
                                  "Teléfono: " + usuario.getTelefono() + "\n" +
                                  "Email: " + usuario.getEmail();
                                  
            // Enviar la respuesta
            response.setContentType("text/plain");
            response.getWriter().write(responseText);
        } else {
            // Responder con un código de error 404 si el usuario no se encuentra
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
        }
    }
}
