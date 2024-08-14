package com.sap.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.dao.UsuarioDAO;
import com.sap.model.Usuario;

public class RegistrarUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Capturar los datos del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String clave = request.getParameter("password");

        // Debug: Mostrar los valores recibidos
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Clave: " + clave);

        // Crear el objeto Usuario y asignar los valores
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(correo);
        usuario.setTelefono(telefono);
        usuario.setPassword(clave);

        // Registrar el usuario en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean registrado = usuarioDAO.registrarUsuario(usuario);

        // Redirigir según el resultado del registro
        if (registrado) {
            response.sendRedirect("iniciar.html?success=1");
        } else {
            response.sendRedirect("registro.html?error=1");
        }
    }
}
