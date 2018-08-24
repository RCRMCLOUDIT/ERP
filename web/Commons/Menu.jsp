<%-- 
    Document   : Menu
    Created on : 08-08-2018, 01:09:31 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    </head>
    <body>
        <nav class="navbar navbar-expand-sm bg-primary navbar-light">
            <!-- Brand -->
            <a class="navbar-brand" style="color: #FFFFFF;" href="../Principal.jsp">Infinity Business</a>
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
                        <a class="dropdown-item" href="../Contabilidad/TiposCuenta.jsp">Tipos Cuentas</a>
                        <a class="dropdown-item" href="../Contabilidad/CatalogoContable.jsp">Catalogo Contable</a>
                    </div>
                </li>
            </ul>
        </nav>
    </body>
</html>
