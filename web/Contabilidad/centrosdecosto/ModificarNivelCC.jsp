<%-- 
    Document   : ModificarTipoCuenta
    Created on : 08-07-2018, 05:08:09 PM
    Owner:     : Cloud IT Systems, S.A
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="model.DaoCentrosCosto"%>
<%@page import="beans.ConexionDB"%>
<%@page import="controller.ServletCentrosCosto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int cclevelId = Integer.valueOf(request.getParameter("cclevelId"));
    DaoCentrosCosto datos = new DaoCentrosCosto();
    datos.nivelCCUPDPRE(cclevelId);
    String levelName1 = datos.GetCLLEVELNM1;
    String levelName2 = datos.GetCLLEVELNM2;
    String levelName3 = datos.GetCLLEVELNM3;
    String levelName4 = datos.GetCLLEVELNM4;
    String levelName5 = datos.GetCLLEVELNM5;
  
    String levelActive = datos.GetCLACTIVE;
    String levelDefault = datos.GetCLDEFAULT;
    
    int Msg = 0;
    if (request.getParameter("Msg") != null) {
        Msg = Integer.valueOf(request.getParameter("Msg"));
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="../../css/jquery-ui-1.12.1.css">
        <link rel="stylesheet" href="../../css/bootstrap.css">
        <link rel="stylesheet" href="../../css/bootstrap.min.css">
        <link rel="stylesheet" href="../../css/bootstrap-grid.css">
        <link rel="stylesheet" href="../../css/bootstrap-grid.min.css">
        <link rel="stylesheet" href="../../css/bootstrap-reboot.css">
        <link rel="stylesheet" href="../../css/bootstrap-reboot.min.css">
        <link rel="stylesheet" href="../../css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="../../css/responsive.bootstrap.min.css">
        <link rel="stylesheet" href="../../css/font-awesome.min.css">
        <script src="../../js/bootstrap.js"></script>
        <script src="../../js/bootstrap.min.js"></script>
        <script src="../../js/bootstrap.bundle.js"></script>
        <script src="../../js/bootstrap.bundle.min.js"></script>
        <script src="../../js/jquery.js"></script>
        <script src="../../js/jquery.dataTables.js"></script>
        <script src="../../js/dataTables.bootstrap.min.js"></script>
        <script src="../../js/dataTables.responsive.min.js"></script>
        <script src="../../js/responsive.bootstrap.min.js"></script>
        <script src="../../js/jquery-ui.min.js"></script>
        <script src="../../js/calendario.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>        
        <title>Editar Tipo Cta</title>
    </head>
    <%@include file="../../commons/Menu.jsp" %>
    <body style="align-content: center">
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Editar Niveles de Centro de Costo</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Niveles de Centro de Costo</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DE LA CUENTA CONTABLE --%>
            <form id="UpdNivelCC" role='form' action='../../ServletCentrosCosto' method='POST'>
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
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="UpdNivelCC" hidden="true">
                    <input id="cclevelId" name="cclevelId" type="number" class="form-control col-sm-2" style="text-align: center" value="<%=cclevelId%>" readonly="true">                    
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Nivel 1</strong></span>
                        <input id="form-nivelcc1" name="form-nivelcc1" type="text" class="form-control col-sm-6" required="true" style="text-align: center" value="<%=levelName1%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Nivel 2</strong></span>
                        <input id="form-nivelcc2" name="form-nivelcc2" type="text" class="form-control col-sm-6" required="true" style="text-align: center" value="<%=levelName2%>">
                    </div>                    
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Nivel 3</strong></span>
                        <input id="form-nivelcc3" name="form-nivelcc3" type="text" class="form-control col-sm-6" required="true" style="text-align: center" value="<%=levelName3%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Nivel 4</strong></span>
                        <input id="form-nivelcc4" name="form-nivelcc4" type="text" class="form-control col-sm-6" required="true" style="text-align: center" value="<%=levelName4%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Nivel 5</strong></span>
                        <input id="form-nivelcc5" name="form-nivelcc5" type="text" class="form-control col-sm-6" required="true" style="text-align: center" value="<%=levelName5%>">
                    </div>                    
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "ListaNivelCC.jsp"' class='btn btn-primary'> <<< Volver</button>
                    </div>
                    <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Guardar</button>
                </div>
            </form> <%--FIN FORMULARIO PARA EL ENVIO DE DATOS A LA RESERVA--%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>