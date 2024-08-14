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

public class ProductoActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Iniciando ProductoActionServlet");
        request.getParameterMap().forEach((key, value) -> System.out.println("Clave: " + key + ", Valor: " + String.join(", ", value)));

        String action = request.getParameter("action");
        String idProductoStr = request.getParameter("idProducto");
        Integer idProducto = null;
        
        if (idProductoStr != null && !idProductoStr.isEmpty()) {
            try {
                idProducto = Integer.parseInt(idProductoStr);
            } catch (NumberFormatException e) {
                System.out.println("ID de producto no proporcionado o vacío.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto no proporcionado o vacío.");
                return;
            }
        } else {
            System.out.println("ID de producto no proporcionado o vacío.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto no proporcionado o vacío.");
            return;
        }

        ProductoDAO productoDAO = new ProductoDAO();

        if ("edit".equals(action)) {
            String nuevoPrecioStr = request.getParameter("precio");
            String nuevaCantidadStr = request.getParameter("cantidad");
            String nuevaDescripcion = request.getParameter("descripcion");

            try {
                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);

                Producto producto = productoDAO.obtenerProductoPorId(idProducto);
                if (producto != null) {
                    // Normalizar la ruta de la imagen antes de actualizar
                    normalizarRutaImagen(producto);
                    
                    producto.setPrecio(nuevoPrecio);
                    producto.setCantidadDisponible(nuevaCantidad);
                    producto.setDescripcion(nuevaDescripcion);

                    boolean actualizado = productoDAO.actualizarProducto(producto);
                    if (actualizado) {
                        System.out.println("Producto actualizado correctamente.");
                    } else {
                        System.out.println("No se pudo actualizar el producto.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error en los valores numéricos proporcionados: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valores numéricos inválidos.");
                return;
            }
        } else if ("delete".equals(action)) {
            Producto producto = productoDAO.obtenerProductoPorId(idProducto);
            if (producto != null) {
                // Normalizar la ruta de la imagen antes de eliminar
                normalizarRutaImagen(producto);

                boolean eliminado = productoDAO.eliminarProducto(idProducto);
                if (eliminado) {
                    System.out.println("Producto eliminado correctamente.");
                } else {
                    System.out.println("No se pudo eliminar el producto.");
                }
            } else {
                System.out.println("Producto no encontrado para el ID: " + idProducto);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado.");
                return;
            }
        }

        // Volver a cargar la lista de productos y redirigir
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("idUsuario") != null) {
            Integer idUsuario = (Integer) session.getAttribute("idUsuario");
            List<Producto> productos = productoDAO.obtenerProductosPorUsuario(idUsuario);
            productos.forEach(this::normalizarRutaImagen); // Normalizar las rutas de las imágenes
            request.setAttribute("productos", productos);
        }

        request.getRequestDispatcher("gestionar-productos.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    // Función para normalizar la ruta de la imagen
    private void normalizarRutaImagen(Producto producto) {
        String ruta = producto.getRutaImagen();
        if (ruta != null && ruta.endsWith(".png.png")) {
            ruta = ruta.substring(0, ruta.length() - 4); // Eliminar el ".png" extra al final
        }
        producto.setRutaImagen(ruta);
        System.out.println("Ruta de imagen normalizada: " + ruta);
    }
}
