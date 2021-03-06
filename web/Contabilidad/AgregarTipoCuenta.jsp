<%-- 
    Document   : AgregarTipoCuenta
    Created on : 08-07-2018, 05:03:40 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page import="controller.ServletContabilidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String DirActual = request.getContextPath();
    int Msg = 0;
    if (request.getParameter("Msg") != null) {
        Msg = Integer.valueOf(request.getParameter("Msg"));
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <%--ESTILOS DEL FRAMEWORK BOOTSTRAP --%>
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap-select.css">
        <%--ESTILOS DEL FRAMEWORK IONICONS --%>
        <link rel="stylesheet" href="<%=DirActual%>/ionicons/css/ionicons.min.css">        
        <%--JS DEL FRAMEWORK BOOTSTRAP Y JQUERY--%>
        <script src="<%=DirActual%>/js/jquery.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap.bundle.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap-select.js"></script>
        <script src="<%=DirActual%>/js/blocker.js"></script>
        <script src="<%=DirActual%>/js/cross-browser.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>        
        <title>Agregar Tipo de Cta Contable</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body style="align-content: center">
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Agregar Tipo de Cuenta</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de Tipo de Cuenta</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DEL TIPO DE CUENTA CONTABLE --%>
            <form id="AddCtaContable" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE DATOS DEL TIPO DE CUENTA CONTABLE --%>
                    <%if (Msg == 1) {%>
                    <div class="alert alert-danger alert-dismissible" id="myAlert">
                        <a href="#" class="close">&times;</a>
                        <strong>Error!</strong> Nombre de Tipo Cuenta Ya existe!
                    </div>                    
                    <%}%>
                    <%if (Msg == 2) {%>
                    <div class="alert alert-danger alert-dismissible" id="myAlert">
                        <a href="#" class="close">&times;</a>
                        <strong>Error!</strong> Codigo de Tipo Cuenta Ya existe!
                    </div>
                    <%}%>
                    <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="AddTipoCta" hidden="true">
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Clase Tipo De Cta</strong></span>
                        <select class="form-control col-sm-2" id="form-TipoCuenta" name="form-TipoCuenta" style="text-align: center">
                            <option value=""></option>
                            <option value="A">Activo</option>
                            <option value="P">Pasivo</option>
                            <option value="C">Capital</option>
                            <option value="I">Ingresos</option>
                            <option value="G">Gastos</option>
                        </select>
                        <span class="input-group-addon col-sm-2"><strong>Codigo Tipo Cuenta</strong></span>
                        <%if (request.getParameter("NombreCuenta") != null) {%>
                        <input id="form-NumeroCuenta" name="form-NumeroCuenta" type="number" class="form-control col-sm-2" required="true" min="0" style="text-align: center" value="<%=request.getParameter("NumeroCuenta")%>">
                        <%} else {%>
                        <input id="form-NumeroCuenta" name="form-NumeroCuenta" type="number" class="form-control col-sm-2" required="true" min="0" style="text-align: center">
                        <%}%>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Cuenta</strong></span>
                        <%if (request.getParameter("NombreCuenta") != null) {%>
                        <input id="form-NombreCuenta" name="form-NombreCuenta" type="text" class="form-control col-sm-6" placeholder="Ingresa Nombre Tipo Cuenta..." required="true" style="text-align: center" value="<%=request.getParameter("NombreCuenta")%>">
                        <%} else {%>
                        <input id="form-NombreCuenta" name="form-NombreCuenta" type="text" class="form-control col-sm-6" placeholder="Ingresa Nombre Tipo Cuenta..." required="true" style="text-align: center">
                        <%}%>
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DEL TIPO DE CUENTA CONTABLE --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "TiposCuenta.jsp"' class='btn btn-primary'> <<< Volver</button>
                    </div>
                    <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Guardar</button>
                </div>
            </form> <%--FIN PARA MANDAR A GUARDAR LOS DATOS DEL TIPO DE CUENTA CONTABLE --%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>