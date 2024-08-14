package com.sap.servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.sap.dao.ProductoDAO;
import com.sap.model.Producto;

@MultipartConfig
public class PublicacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR = "images/productos";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Obtener la sesión y validar el usuario
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            System.out.println("ID de usuario no encontrado en la sesión.");
            response.sendRedirect("iniciar.html");
            return;
        }
        
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        System.out.println("ID de usuario en sesión: " + idUsuario);

        // Obtener los datos del formulario
        String nombreProducto = request.getParameter("Producto");
        String precioKgStr = request.getParameter("Kgdis");
        String cantidadDisponibleStr = request.getParameter("Cantidadd");
        String descripcion = request.getParameter("Descripcion");

        System.out.println("Nombre del producto: " + nombreProducto);
        System.out.println("Precio por KG (texto): " + precioKgStr);
        System.out.println("Cantidad disponible (texto): " + cantidadDisponibleStr);
        System.out.println("Descripción: " + descripcion);

        // Validar los datos
        if (nombreProducto == null || nombreProducto.trim().isEmpty() ||
            precioKgStr == null || precioKgStr.trim().isEmpty() ||
            cantidadDisponibleStr == null || cantidadDisponibleStr.trim().isEmpty() ||
            descripcion == null || descripcion.trim().isEmpty()) {
            System.out.println("Faltan campos requeridos en el formulario.");
            response.sendRedirect("publicar.html?error=CamposRequeridos");
            return;
        }

        double precioKg = 0.0;
        int cantidadDisponible = 0;

        try {
            precioKg = Double.parseDouble(precioKgStr.trim());
            cantidadDisponible = Integer.parseInt(cantidadDisponibleStr.trim());
            System.out.println("Precio por KG (numérico): " + precioKg);
            System.out.println("Cantidad disponible (numérico): " + cantidadDisponible);
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato numérico: " + e.getMessage());
            response.sendRedirect("publicar.html?error=FormatoNumerico");
            return;
        }

        // Obtener la ruta de la imagen seleccionada
        String fileName = nombreProducto.toLowerCase() + ".png";
        String filePath = UPLOAD_DIR + "/" + fileName;

        System.out.println("Ruta de la imagen seleccionada: " + filePath);

        // Crear el producto y establecer los valores
        Producto producto = new Producto();
        producto.setNombreProducto(nombreProducto.trim());
        producto.setDescripcion(descripcion.trim());
        producto.setPrecio(precioKg);
        producto.setCantidadDisponible(cantidadDisponible);
        producto.setRutaImagen(filePath);
        producto.setIdUsuario(idUsuario);

        // Guardar el producto en la base de datos
        ProductoDAO productoDAO = new ProductoDAO();
        boolean registrado = productoDAO.registrarProducto(producto);

        if (registrado) {
            System.out.println("Producto registrado con éxito.");
            response.sendRedirect("home-registrado.html?success=ProductoPublicado");
        } else {
            System.out.println("Error al registrar el producto.");
            response.sendRedirect("publicar.html?error=ErrorRegistro");
        }
    }
}
