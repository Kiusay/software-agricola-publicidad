package com.sap.servlets;

import com.sap.dao.ContactoDAO;
import com.sap.model.Contacto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrarContactoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Imprimir para verificar la llegada al servlet
        System.out.println("RegistrarContactoServlet: Iniciando proceso de registro de contacto.");

        // Obtener los datos del formulario y mostrarlos
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String soyUn = request.getParameter("soy_un");
        String mensaje = request.getParameter("mensaje");

        System.out.println("Datos recibidos del formulario:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correo);
        System.out.println("Soy un: " + soyUn);
        System.out.println("Mensaje: " + mensaje);

        // Crear un objeto Contacto
        Contacto contacto = new Contacto(nombre, telefono, correo, soyUn, mensaje);

        // Guardar el contacto en la base de datos
        ContactoDAO contactoDAO = new ContactoDAO();
        boolean exito = contactoDAO.registrarContacto(contacto);

        // Verificar si se guardó correctamente
        if (exito) {
            System.out.println("Contacto registrado correctamente en la base de datos.");
            // Redirigir a la página de éxito
            response.sendRedirect("contacto.html?success=true");
        } else {
            System.out.println("Error al registrar el contacto en la base de datos.");
            // Redirigir a la página de error
            response.sendRedirect("contacto.html?error=true");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doGet llamado, redirigiendo a doPost.");
        doPost(request, response);
    }
}
