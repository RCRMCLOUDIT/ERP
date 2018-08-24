<%-- 
    Document   : Principal
    Created on : 08-20-2018, 02:36:05 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/estilos.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>Principal</title>
    </head>
    <%-- MENU --%> 
    <nav class="navbar navbar-expand-sm bg-primary navbar-light">
        <!-- Brand -->
        <a class="navbar-brand" style="color: #FFFFFF;" href="Principal.jsp">Infinity Business</a>
        <!-- Links -->
        <ul class="navbar-nav">
            <%--
            <li class="nav-item">
                <a class="nav-link" href="#">Link 1</a>
            </li>
            --%>
            <!-- Dropdown -->
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown" style="color: #FFFFFF;">Contabilidad</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="Contabilidad/TiposCuenta.jsp">Tipos Cuentas</a>
                    <a class="dropdown-item" href="Contabilidad/CatalogoContable.jsp">Catalogo Contable</a>
                </div>
            </li>
        </ul>
    </nav>
    <%-- END OF MENU --%> 
</html>