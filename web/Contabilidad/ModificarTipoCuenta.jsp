<%-- 
    Document   : ModificarTipoCuenta
    Created on : 08-07-2018, 05:08:09 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="model.DaoContabilidad"%>
<%@page import="controller.ServletContabilidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int GLTPCLSID = Integer.valueOf(request.getParameter("GLTPCLSID"));
    DaoContabilidad datos = new DaoContabilidad();
    datos.VerificarCod(GLTPCLSID);
    String GLTPNAME = datos.GetGLTPNAME;
    String GLTPACCID = datos.GetGLTPACCID;
    datos.VerificarRelacion(GLTPCLSID);
    int GetRelacion = datos.GetRelacion;
    if (GLTPACCID.equals("A")) {
        GLTPACCID = "Activo";
    }
    if (GLTPACCID.equals("P")) {
        GLTPACCID = "Pasivo";
    }
    if (GLTPACCID.equals("C")) {
        GLTPACCID = "Capital";
    }
    if (GLTPACCID.equals("I")) {
        GLTPACCID = "Ingresos";
    }
    if (GLTPACCID.equals("G")) {
        GLTPACCID = "Gastos";
    }
    int Msg = 0;
    if (request.getParameter("Msg") != null) {
        Msg = Integer.valueOf(request.getParameter("Msg"));
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/bootstrap.css">
        <link rel="stylesheet" href="../css/jquery-ui-1.12.1.css">
        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/calendario.js"></script>
        <script src="../js/jquery-ui.min.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>   
        <title>Editar Tipo Cta</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body style="align-content: center">
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Editar Tipo de Cuenta Contable</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de Tipo de Cuenta</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DE LA CUENTA CONTABLE --%>
            <form id="AddCtaContable" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE DATOS DE LA CUENTA --%>
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
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="UpdateTipoCta" hidden="true">
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Codigo Tipo Cuenta</strong></span>
                        <input id="form-NumeroCuenta" name="form-NumeroCuenta" type="number" class="form-control col-sm-2" style="text-align: center" value="<%=GLTPCLSID%>" readonly="true">
                        <span class="input-group-addon col-sm-2"><strong>Clase Tipo De Cta</strong></span>
                        <select class="form-control col-sm-2" id="form-TipoCuenta" name="form-TipoCuenta" style="text-align: center">
                            <%if (GetRelacion == 0) {%>
                            <option value="<%=GLTPACCID%>"><%=GLTPACCID%></option>
                            <option disabled>──────────</option>
                            <option value="A">Activo</option>
                            <option value="P">Pasivo</option>
                            <option value="C">Capital</option>
                            <option value="I">Ingresos</option>
                            <option value="G">Gastos</option>
                            <%} else {%>
                            <option value="<%=GLTPACCID%>"><%=GLTPACCID%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Tipo Cuenta</strong></span>
                        <input id="form-NombreCuenta" name="form-NombreCuenta" type="text" class="form-control col-sm-6" required="true" style="text-align: center" value="<%=GLTPNAME%>">
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "TiposCuenta.jsp"' class='btn btn-primary'> <<< Volver</button>
                    </div>
                    <button type="submit" class="btn btn-primary" id="btnAgregar" name="btnAgregar" >Guardar</button>
                </div>
            </form> <%--FIN FORMULARIO PARA EL ENVIO DE DATOS A LA RESERVA--%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>