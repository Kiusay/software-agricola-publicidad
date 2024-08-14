package com.sap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.sap.model.Documento;
import com.sap.util.DatabaseUtil;

public class DocumentoDAO {

    public boolean guardarDocumento(Documento documento) {
        String sql = "INSERT INTO documentos (idUsuario, nombreComprador, cedulaComprador, nombreVendedor, cedulaVendedor, producto, cantidadKg, precioKg, fechaEntrega, condiciones, valorTotal, fechaGeneracion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, documento.getIdUsuario());
            statement.setString(2, documento.getNombreComprador());
            statement.setString(3, documento.getCedulaComprador());
            statement.setString(4, documento.getNombreVendedor());
            statement.setString(5, documento.getCedulaVendedor());
            statement.setString(6, documento.getProducto());
            statement.setDouble(7, documento.getCantidadKg());
            statement.setDouble(8, documento.getPrecioKg());
            statement.setDate(9, new java.sql.Date(documento.getFechaEntrega().getTime()));
            statement.setString(10, documento.getCondiciones());
            statement.setDouble(11, documento.getValorTotal());
            statement.setDate(12, new java.sql.Date(documento.getFechaGeneracion().getTime()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
