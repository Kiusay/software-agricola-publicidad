package com.sap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sap.model.Publicacion;
import com.sap.util.DatabaseUtil;

public class PublicacionDAO {

    // Método para agregar una publicación
    public boolean agregarPublicacion(Publicacion publicacion) {
        String sql = "INSERT INTO publicaciones (titulo, descripcion, precio, fechaPublicacion, idProducto, idUsuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, publicacion.getTitulo());
            statement.setString(2, publicacion.getDescripcion());
            statement.setDouble(3, publicacion.getPrecio());
            statement.setDate(4, new java.sql.Date(publicacion.getFechaPublicacion().getTime()));
            statement.setInt(5, publicacion.getIdProducto());
            statement.setInt(6, publicacion.getIdUsuario());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Otros métodos como obtenerPublicacionPorId, actualizarPublicacion, etc.
}
