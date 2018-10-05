<%-- 
    Document   : Menu
    Created on : 08-08-2018, 01:09:31 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String contextRoot = request.getContextPath();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="<%=contextRoot%>/css/jquery-ui-1.12.1.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/bootstrap.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/bootstrap-grid.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/bootstrap-grid.min.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/bootstrap-reboot.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/bootstrap-reboot.min.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/responsive.bootstrap.min.css">
        <link rel="stylesheet" href="<%=contextRoot%>/css/font-awesome.min.css">
        <script src="<%=contextRoot%>/js/bootstrap.js"></script>
        <script src="<%=contextRoot%>/js/bootstrap.min.js"></script>
        <script src="<%=contextRoot%>/js/bootstrap.bundle.js"></script>
        <script src="<%=contextRoot%>/js/bootstrap.bundle.min.js"></script>
        <script src="<%=contextRoot%>/js/jquery.js"></script>
        <script src="<%=contextRoot%>/js/jquery.dataTables.js"></script>
        <script src="<%=contextRoot%>/js/dataTables.bootstrap.min.js"></script>
        <script src="<%=contextRoot%>/js/dataTables.responsive.min.js"></script>
        <script src="<%=contextRoot%>/js/responsive.bootstrap.min.js"></script>
        <script src="<%=contextRoot%>/js/jquery-ui.min.js"></script>
        <script src="<%=contextRoot%>/js/calendario.js"></script>              
    </head>
    <body>
        <%-- MENU --%>
        <nav class="navbar navbar-expand-sm bg-light navbar-light">
            <!-- Brand -->
            <a class="navbar-brand" href="<%=contextRoot%>/Principal.jsp"><img src="<%=contextRoot%>/images/LOGOIB.png" title="Infinity Business"></a>
            <!-- Links -->
            <ul class="navbar-nav">
                <!-- Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Administracion</a>
                    <div class="dropdown-menu">

                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Producto</a>
                    <div class="dropdown-menu">

                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Bodega</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="<%=contextRoot%>/Importaciones/BuscarPolizas.jsp">Importaciones</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Ventas</a>
                    <div class="dropdown-menu">

                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Compras</a>
                    <div class="dropdown-menu">

                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">CxC</a>
                    <div class="dropdown-menu">

                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">CxP</a>
                    <div class="dropdown-menu">

                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Contabilidad</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="<%=contextRoot%>/Contabilidad/centrosdecosto/ListaNivelCC.jsp">Nivel de CC</a>
                        <a class="dropdown-item" href="<%=contextRoot%>/Contabilidad/TiposCuenta.jsp">Tipos Cuentas</a>
                        <a class="dropdown-item" href="<%=contextRoot%>/Contabilidad/CatalogoContable.jsp">Catalogo Contable</a>
                        <div class="dropdown-divider"> Comprobantes </div>
                        <a class="dropdown-item" href="<%=contextRoot%>/Contabilidad/PlantillaComprobante.jsp">Plantilla Comprobante</a>
                        <a class="dropdown-item" href="<%=contextRoot%>/Contabilidad/BuscarComprobante.jsp">Buscar Comprobante</a>
                        <div class="dropdown-divider"> Reportes </div>
                        <a class="dropdown-item" href="<%=contextRoot%>/Contabilidad/Reportes/DetalleTransCuenta.jsp">Detalle Trans. Por Cuenta</a>
                    </div>
                </li>
            </ul>
        </nav>
        <%-- END OF MENU --%> 
    </body>
</html>
