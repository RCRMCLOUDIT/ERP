<%-- 
    Document   : SeleccionPlantilla
    Created on : 09-11-2018, 04:21:37 PM
    Author     : Ing. Moises Romero Mojica
--%>

<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/bootstrap.css">
        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/calendario.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>
        <title>Plantilla Comprobante</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Comprobante - Seleccione Plantilla </h1>
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos Plantilla</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR SELECCIONAR UNA PLANTILLA --%>
            <form id="BuscaComprobante" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV DE PARAMETROS PARA LA BUSQUEDA DE COMPROBANTE --%>
                    <div class="input-group">
                        <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                        <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="PlantillaSeleccion" hidden="true">
                        <span class="input-group-addon col-sm-2"><strong>Seleccione Plantilla</strong></span>
                        <select class="form-control col-sm-2" id="form-IdPlantilla" name="form-IdPlantilla" style="text-align: center">
                            <option value="0" >Seleccione un Tipo</option>
                            <% try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    String consulta = "SELECT GLTMID, GLTMMEMO From `IBGLTDPST` GROUP BY GLTMID";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement(consulta);
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
                                    }; // fin while 
                                    conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                    rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                    pst.close(); //CIERRO EL PREPARED STATEMENT.
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                    </div>
                </div><%--FIN DIV DE PARAMETROS PARA LA BUSQUEDA DE COMPROBANTE --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "BuscarComprobante.jsp"' class='btn btn-primary'> 
                            <i class="icon ion-arrow-return-left"></i> Volver a Busqueda
                        </button>
                    </div>
                    <button type="submit" class="btn btn-success" id="btnBuscar" name="btnBuscar" >Continuar >>></button>
                </div>
            </form> <%--FIN FORMULARIO PARA MANDAR A BUSCAR UN COMPROBANTE--%>
        </div><%--FIN DIV GRUPO DE TABS--%>   
    </body>
</html>
