package com.sap.servlets;

import com.sap.dao.ProductoDAO;
import com.sap.model.Producto;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GestionarProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            System.out.println("Sesión no iniciada o ID de usuario no encontrado.");
            response.sendRedirect("iniciar.html");
            return;
        }

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        System.out.println("ID de usuario obtenido: " + idUsuario);

        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerProductosPorUsuario(idUsuario);
        
        productos.forEach(this::normalizarRutaImagen);

        System.out.println("Número de productos recuperados: " + (productos == null ? "null" : productos.size()));

        request.setAttribute("productos", productos);
        request.getRequestDispatcher("gestionar-productos.jsp").forward(request, response);
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
