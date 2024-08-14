package com.sap.servlets;

import com.sap.dao.UsuarioDAO;
import com.sap.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String correo = request.getParameter("Correo");
        String clave = request.getParameter("Clave");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.iniciarSesion(correo, clave);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("idUsuario", usuario.getIdUsuario()); // Guardar solo el idUsuario
            response.sendRedirect("home-registrado.html");
        } else {
            response.sendRedirect("iniciar.html?error=true");
        }
    }
}
