<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sap.model.Producto" %>

<%
    // Parámetros para la paginación
    int currentPage = 1;
    int recordsPerPage = 4;
    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }

    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    int noOfRecords = productos.size();
    int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
    int start = (currentPage - 1) * recordsPerPage;
    int end = Math.min(start + recordsPerPage, noOfRecords);
%>

<!DOCTYPE html>
<html data-wf-page="66ad01f9ff1b7a78269e1523" data-wf-site="66aa91834e526d6910a5c431">
<head>
  <meta charset="utf-8">
  <title>Gestionar-Productos</title>
  <meta content="width=device-width, initial-scale=1" name="viewport">
  <link href="css/normalizacion.css" rel="stylesheet" type="text/css">
  <link href="css/web.css" rel="stylesheet" type="text/css">
  <link href="css/sap.v1.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com" rel="preconnect">
  <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
  <script type="text/javascript">
    WebFont.load({
      google: {
        families: ["Changa One:400,400italic","Ubuntu:300,300italic,400,400italic,500,500italic,700,700italic"]
      }
    });
  </script>
  <link href="images/favicon.ico" rel="shortcut icon" type="image/x-icon">
  <link href="images/webclip.png" rel="apple-touch-icon">

  <script type="text/javascript">
    function gestionarProducto(idProducto) {
      const accion = prompt('¿Deseas hacer una modificación?\nEscribe la opción que deseas seleccionar ejemplo: 1 y luego presiona aceptar.\n\n1. Editar Producto\n2. Eliminar Producto');

      if (accion === '1') {
        const nuevoPrecio = parseFloat(prompt('Ingrese el nuevo precio por Kg:'));
        if (isNaN(nuevoPrecio) || nuevoPrecio <= 0) {
          alert('Precio inválido. Operación cancelada.');
          return;
        }

        const nuevaCantidad = parseInt(prompt('Ingrese la nueva cantidad disponible:'));
        if (isNaN(nuevaCantidad) || nuevaCantidad <= 0) {
          alert('Cantidad inválida. Operación cancelada.');
          return;
        }

        const nuevaDescripcion = prompt('Ingrese la nueva descripción:');
        if (!nuevaDescripcion || nuevaDescripcion.trim() === "") {
          alert('Descripción inválida. Operación cancelada.');
          return;
        }

        // Redirigir al servlet de edición con los nuevos valores
        window.location.href = "ProductoActionServlet?action=edit&idProducto=" + encodeURIComponent(idProducto) + "&precio=" + encodeURIComponent(nuevoPrecio) + "&cantidad=" + encodeURIComponent(nuevaCantidad) + "&descripcion=" + encodeURIComponent(nuevaDescripcion);
      } else if (accion === '2') {
        const confirmacion = confirm('¿Está seguro de que desea eliminar este producto?');
        if (confirmacion) {
          // Redirigir al servlet de eliminación
          window.location.href = "ProductoActionServlet?action=delete&idProducto=" + encodeURIComponent(idProducto);
        }
      } else {
        alert('Operación cancelada.');
      }
    }
  </script>
</head>
<body class="body">
  <section class="section-3">
    <div class="text-block-2">© 2024 SAP - Software Agricola de Publicidad. Todos los derechos reservados.</div>
  </section>
  <div class="div-block"><img src="images/Logo.png" loading="lazy" width="85" height="Auto" alt="" srcset="images/Logo-p-500.png 500w, images/Logo-p-800.png 800w, images/Logo.png 1024w" sizes="85px" class="image">
    <h1 class="sap-h">SOFTWARE AGRÍCOLA DE PUBLICIDAD</h1>
    <a href="home-registrado.html" class="button-16 botonera w-button">VOLVER AL INICIO</a>
  </div>
  <section class="section"><img src="images/fondo-1.jpg" loading="lazy" sizes="(max-width: 2640px) 100vw, 2640px" srcset="images/fondo-1-p-500.jpg 500w, images/fondo-1-p-800.jpg 800w, images/fondo-1-p-1080.jpg 1080w, images/fondo-1-p-1600.jpg 1600w, images/fondo-1-p-2000.jpg 2000w, images/fondo-1-p-2600.jpg 2600w, images/fondo-1.jpg 2640w" alt="">
    <section class="section-6">
      <section class="section-7">
        <h1 class="heading-2">Gestionar productos</h1>
      </section>
      <section class="section-8">
        <%
          if (productos != null && !productos.isEmpty()) {
              for (int i = start; i < end; i++) {
                Producto producto = productos.get(i);
        %>
        <!-- Div que envuelve la información del producto con evento onclick -->
        <div class="div-block-12" onclick="gestionarProducto(<%= producto.getIdProducto() %>)" style="cursor: pointer;">
          <div class="div-block-13"><img src="<%=producto.getRutaImagen()%>" alt="<%=producto.getNombreProducto()%>" class="image-7"></div>
          <div class="div-block-13">
            <div class="text-block-9 image-7">Producto:</div>
            <div class="image-7 text-block-11"><%=producto.getNombreProducto()%></div>
            <div class="text-block-9 image-7">Precio por Kg:</div>
            <div class="image-7 text-block-11">$<%=producto.getPrecio()%></div>
            <div class="text-block-9 image-7">Cantidad disponible:</div>
            <div class="image-7 text-block-11"><%=producto.getCantidadDisponible()%> Kg</div>
            <div class="text-block-9 image-7">Descripción:</div>
            <div class="image-7 text-block-11"><%=producto.getDescripcion()%></div>
          </div>
        </div>
        <% 
              }
          } else { 
        %>
        <div>No hay productos para mostrar.</div>
        <% 
          }
        %>
      </section>
      <!-- Paginación -->
      <div style="text-align: center; margin-top: 20px; margin-bottom: 40px; color: white;">
        <%
          if (currentPage > 1) {
        %>
          <a href="?page=<%= currentPage - 1 %>" style="color: white;">&laquo; Anterior</a>
        <%
          }
        %>
        Página <%= currentPage %> de <%= noOfPages %>
        <%
          if (currentPage < noOfPages) {
        %>
          <a href="?page=<%= currentPage + 1 %>" style="color: white;">Siguiente &raquo;</a>
        <%
          }
        %>
      </div>
    </section>
  </section>

  <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=66aa91834e526d6910a5c431" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  <script src="js/Javasap.js" type="text/javascript"></script>
</body>
</html>
