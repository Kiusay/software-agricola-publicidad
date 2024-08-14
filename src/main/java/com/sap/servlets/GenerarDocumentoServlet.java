package com.sap.servlets;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.sap.dao.DocumentoDAO;
import com.sap.model.Documento;

public class GenerarDocumentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Capturar los datos del formulario
        String nombreComprador = request.getParameter("Nombrec");
        String nombreVendedor = request.getParameter("Nombrev");
        String cedulaComprador = request.getParameter("Cedulac");
        String cedulaVendedor = request.getParameter("Cedulav");
        String producto = request.getParameter("Nombrep");
        String cantidadKg = request.getParameter("Cantidadkg");
        String precioKg = request.getParameter("Preciokg");
        String fechaEntrega = request.getParameter("Fechae");
        String condiciones = request.getParameter("Condiciones");

        // Calcular el valor total
        double cantidad = Double.parseDouble(cantidadKg);
        double precio = Double.parseDouble(precioKg);
        double valorTotal = cantidad * precio;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale.Builder().setRegion("CO").build());

        // Configurar la respuesta HTTP para que sea un PDF
        response.setContentType("application/pdf");
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Añadir el logo del SAP
            String logoPath = getServletContext().getRealPath("/images/Logo.png");
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);

            // Añadir el título centrado
            Paragraph titulo = new Paragraph("DOCUMENTO DE ACUERDO DE COMPRA");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Espaciado
            document.add(new Paragraph(" "));

            // Añadir contenido al documento con mayor espaciado entre párrafos
            document.add(new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("El presente documento hace constar que el comprador, " + nombreComprador + 
                ", identificado con la cédula " + cedulaComprador + 
                ", se compromete a adquirir del vendedor, " + nombreVendedor + 
                ", identificado con la cédula " + cedulaVendedor + 
                ", el siguiente producto:"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Producto: " + producto));
            document.add(new Paragraph("Cantidad acordada: " + cantidadKg + " KG"));
            document.add(new Paragraph("Precio por KG: $" + precioKg));
            document.add(new Paragraph("Valor total a pagar: " + currencyFormatter.format(valorTotal) + " COP"));
            document.add(new Paragraph("Fecha de entrega pactada: " + fechaEntrega));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Así mismo, el vendedor, " + nombreVendedor + 
                ", se compromete a cumplir con la entrega de la cantidad mencionada de producto, respetando el precio acordado de $" + precioKg + 
                " por KG, sin realizar incrementos de precio, y a cumplir con la fecha de entrega pactada."));
            document.add(new Paragraph(" "));

            if (condiciones != null && !condiciones.isEmpty()) {
                document.add(new Paragraph("Condiciones Adicionales: " + condiciones));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Este documento es un acuerdo de buena fe entre ambas partes y sirve como referencia para cualquier reclamación o ajuste futuro en el proceso de compra-venta."));

            // Guardar datos en la base de datos
            DocumentoDAO documentoDAO = new DocumentoDAO();
            Documento nuevoDocumento = new Documento();
            nuevoDocumento.setIdUsuario(1); // Aquí deberías poner el ID del usuario que está creando el documento
            nuevoDocumento.setNombreComprador(nombreComprador);
            nuevoDocumento.setCedulaComprador(cedulaComprador);
            nuevoDocumento.setNombreVendedor(nombreVendedor);
            nuevoDocumento.setCedulaVendedor(cedulaVendedor);
            nuevoDocumento.setProducto(producto);
            nuevoDocumento.setCantidadKg(cantidad);
            nuevoDocumento.setPrecioKg(precio);
            nuevoDocumento.setFechaEntrega(new SimpleDateFormat("dd/MM/yyyy").parse(fechaEntrega));
            nuevoDocumento.setCondiciones(condiciones);
            nuevoDocumento.setValorTotal(valorTotal);
            nuevoDocumento.setFechaGeneracion(new Date());

            documentoDAO.guardarDocumento(nuevoDocumento);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
