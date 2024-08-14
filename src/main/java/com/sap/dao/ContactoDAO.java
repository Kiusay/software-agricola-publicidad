package com.sap.dao;

import com.sap.model.Contacto;
import com.sap.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactoDAO {

    public boolean registrarContacto(Contacto contacto) {
        String sql = "INSERT INTO contactos (nombre, telefono, correo, soy_un, mensaje) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, contacto.getNombre());
            statement.setString(2, contacto.getTelefono());
            statement.setString(3, contacto.getCorreo());
            statement.setString(4, contacto.getSoyUn());
            statement.setString(5, contacto.getMensaje());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
