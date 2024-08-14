package com.sap.servlets;

import com.sap.dao.ProductoDAO;
import com.sap.model.Producto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VerPublicacionesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener todos los productos publicados
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        
        // Normalizar la ruta de la imagen para cada producto
        productos.forEach(this::normalizarRutaImagen);

        // Establecer la lista de productos en el request
        request.setAttribute("productos", productos);

        // Redirigir a la página JSP
        request.getRequestDispatcher("productos-publicados.jsp").forward(request, response);
    }

    private void normalizarRutaImagen(Producto producto) {
        String ruta = producto.getRutaImagen();
        if (ruta.endsWith(".png.png")) {
            ruta = ruta.substring(0, ruta.length() - 4); // Eliminar el ".png" extra al final
        }
        producto.setRutaImagen(ruta);
        System.out.println("Ruta de imagen normalizada: " + ruta);
    }
}
