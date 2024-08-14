package com.sap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sap.model.Producto;
import com.sap.util.DatabaseUtil;

public class ProductoDAO {

    public boolean registrarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombreProducto, descripcion, precio, cantidadDisponible, idUsuario, rutaImagen) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, producto.getNombreProducto());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getCantidadDisponible());
            statement.setInt(5, producto.getIdUsuario()); 
            statement.setString(6, producto.getRutaImagen());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Producto> obtenerProductosPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM productos WHERE idUsuario = ?";
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(resultSet.getInt("idProducto"));
                producto.setNombreProducto(resultSet.getString("nombreProducto"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setCantidadDisponible(resultSet.getInt("cantidadDisponible"));
                producto.setRutaImagen(resultSet.getString("rutaImagen"));
                producto.setIdUsuario(resultSet.getInt("idUsuario"));
                
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar los productos: " + e.getMessage());
        }

        return productos;
    }

    // Método para obtener un producto por su ID
    public Producto obtenerProductoPorId(int idProducto) {
        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE idProducto = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idProducto);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                producto = new Producto();
                producto.setIdProducto(resultSet.getInt("idProducto"));
                producto.setNombreProducto(resultSet.getString("nombreProducto"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setCantidadDisponible(resultSet.getInt("cantidadDisponible"));
                producto.setRutaImagen(resultSet.getString("rutaImagen"));
                producto.setIdUsuario(resultSet.getInt("idUsuario"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }

        return producto;
    }

    // Método para actualizar un producto
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombreProducto = ?, descripcion = ?, precio = ?, cantidadDisponible = ?, rutaImagen = ? WHERE idProducto = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, producto.getNombreProducto());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getCantidadDisponible());
            statement.setString(5, producto.getRutaImagen());
            statement.setInt(6, producto.getIdProducto());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE idProducto = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idProducto);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        String sql = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(resultSet.getInt("idProducto"));
                producto.setNombreProducto(resultSet.getString("nombreProducto"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setCantidadDisponible(resultSet.getInt("cantidadDisponible"));
                producto.setRutaImagen(resultSet.getString("rutaImagen"));
                producto.setIdUsuario(resultSet.getInt("idUsuario"));

                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar todos los productos: " + e.getMessage());
        }

        return productos;
    }
}
