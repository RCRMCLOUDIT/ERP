<%-- 
    Document   : DetalleTransCuenta
    Created on : 10-01-2018, 09:38:24 AM
    Author     : Ing. Moises Romero Mojica
    Owner:     : Cloud IT Systems, S.A
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String DirActual = request.getContextPath();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <%--ESTILOS DEL FRAMEWORK BOOTSTRAP --%>
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap-select.css">
        <link rel="stylesheet" href="<%=DirActual%>/css/jquery-ui-1.12.1.css">
        <%--ESTILOS DEL FRAMEWORK IONICONS --%>
        <link rel="stylesheet" href="<%=DirActual%>/ionicons/css/ionicons.min.css">
        <%--JS DEL FRAMEWORK BOOTSTRAP Y JQUERY--%>
        <script src="<%=DirActual%>/js/jquery.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap.bundle.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap-select.js"></script>
        <script src="<%=DirActual%>/js/jquery-ui.min.js"></script>
        <script src="<%=DirActual%>/js/popper.min.js"></script>
        <script src="<%=DirActual%>/js/calendario.js"></script>
        <script src="<%=DirActual%>/js/blocker.js"></script>
        <script src="<%=DirActual%>/js/cross-browser.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>
        <script type="text/javascript">
            //FUNCION PARA VALIDAR EL INGRESO DE PARAMETROS
            function valida() {
                var ok = true;
                var msg = "Seleccione La Fecha";
                var FechaDesde = $("#FechaDesde").datepicker("getDate");
                var FechaHasta = $("#FechaHasta").datepicker("getDate");
                var Check = document.getElementById('BuscarFecha').checked;
                if (NumeroPoliza === "" && NumeroLiquidacion === "0" && Check === false)
                {
                    msg;
                    ok = false;
                }

                if (Check === true && NumeroPoliza === "" && NumeroLiquidacion === "0") {
                    if (FechaDesde > FechaHasta) {
                        msg = "Fecha Inicial no puede ser mayor a Fecha Final.";
                        var FechaDesde = $('#FechaDesde').focus();
                        ok = false;
                    }
                }

                if (ok === false)
                    alert(msg);
                return ok;
            }
        </script>
        <title>Detalle de Transacciones</title>
    </head>
    <%@include file="../../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Detalle de Transacciones Por Cuenta</h1>
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de la Cuenta</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A BUSCAR LAS TRANSACCIONES X CUENTA CONTABLE --%>
            <form id="BuscaTransacciones" role='form' action='ListarTransaccionesContables.jsp' method='POST' onsubmit="">
                <div id="tab-1"><%-- DIV DE PARAMETROS PARA LA BUSQUEDA DE TRANSACCIONES CONTABLES X CUENTA --%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Cuenta Contable</strong></span>
                        <select class="selectpicker col-sm-6" id="form-CtaContable" name="form-CtaContable" data-live-search="true" title="Buscar Cuenta..." required="true">
                            <%  try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    String consulta = "SELECT IDCATALOGO, AccountNumber, AccountName, AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, Isacctaccessible, IBGLTYPACC.GLTPNAME FROM `IBGLACCNTS` INNER JOIN IBGLTYPACC ON IBGLACCNTS.GLTPCLSID=IBGLTYPACC.GLTPCLSID WHERE Active='S'  ORDER BY `AccountNumber` ASC";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement(consulta);
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        if (request.getParameter("SubC") != null && Integer.valueOf(request.getParameter("SubC")) > 0) {
                                            //out.println("<option selected='true' value='" + rs.getInt(1) + "'>" + rs.getString(2) + " - " + rs.getString(3) + " | " + rs.getString(11) + " </option>");
                                            out.println("<option class='text-primary' value='" + rs.getInt(1) + "'><span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                        } else {
                                            //INDEX 4 = ACCOUNT LEVEL1; INDEX 5 = ACCOUNT LEVEL2; INDEX 6 = ACCOUNT LEVEL3
                                            //INDEX 7 = ACCOUNT LEVEL4; INDEX 8 = ACCOUNT LEVEL5; INDEX 9 = ACCOUNT LEVEL6
                                            if (Integer.valueOf(rs.getString(4)) > 0 && Integer.valueOf(rs.getString(5)) == 0 && Integer.valueOf(rs.getString(6)) == 0) {
                                                //ES DE NIVEL 1
                                                if (rs.getString(10).equals("S")) {
                                                    out.println("<option class='text-default' value='" + rs.getInt(1) + "'><span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                } else {
                                                    out.println("<option class='text-muted' disabled='true' value='" + rs.getInt(1) + "'><span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                }

                                            }
                                            if (Integer.valueOf(rs.getString(5)) > 0 && Integer.valueOf(rs.getString(6)) == 0 && Integer.valueOf(rs.getString(7)) == 0) {
                                                //ES DE NIVEL 2
                                                if (rs.getString(10).equals("S")) {
                                                    out.println("<option class='text-default' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                } else {
                                                    out.println("<option class='text-muted' disabled='true' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                }

                                            }
                                            if (Integer.valueOf(rs.getString(6)) > 0 && Integer.valueOf(rs.getString(7)) == 0 && Integer.valueOf(rs.getString(8)) == 0) {
                                                //ES DE NIVEL 3
                                                if (rs.getString(10).equals("S")) {
                                                    out.println("<option class='text-default' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                } else {
                                                    out.println("<option class='text-muted' disabled='true' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                }

                                            }
                                            if (Integer.valueOf(rs.getString(7)) > 0 && Integer.valueOf(rs.getString(8)) == 0 && Integer.valueOf(rs.getString(9)) == 0) {
                                                //ES DE NIVEL 4
                                                if (rs.getString(10).equals("S")) {
                                                    out.println("<option class='text-default' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                } else {
                                                    out.println("<option class='text-muted' disabled='true' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                }

                                            }
                                            if (Integer.valueOf(rs.getString(8)) > 0 && Integer.valueOf(rs.getString(9)) == 0) {
                                                //ES DE NIVEL 5
                                                if (rs.getString(10).equals("S")) {
                                                    out.println("<option class='text-default' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                } else {
                                                    out.println("<option class='text-muted' disabled='true' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                }

                                            }
                                            if (Integer.valueOf(rs.getString(9)) > 0) {
                                                //ES DE NIVEL 6
                                                if (rs.getString(10).equals("S")) {
                                                    out.println("<option class='text-default' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                } else {
                                                    out.println("<option class='text-muted' disabled='true' value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span title=" + rs.getString(2) + " - " + rs.getString(3) + ">" + rs.getString(2) + " - " + rs.getString(3) + "&nbsp;</span>&nbsp;<span title=" + rs.getString(11) + "> " + rs.getString(11) + "&nbsp;</span> </option>");
                                                }

                                            }
                                        }
                                    }; // fin while 
                                    conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                    rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                    pst.close(); //CIERRO EL PREPARED STATEMENT
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                    </div>                              
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Rango Fecha</strong></span>
                        <span class="input-group-addon col-sm-1"><strong>Desde</strong></span>
                        <input type="text" class="form-control col-sm-2" id="FechaDesde" name="FechaDesde" style="text-align: center" readonly="true">
                        <span class="input-group-addon col-sm-1"><strong>Hasta</strong></span>
                        <input type="text" class="form-control col-sm-2" id="FechaHasta" name="FechaHasta" style="text-align: center" readonly="true">
                    </div>
                </div><%--FIN DIV DE PARAMETROS PARA LA BUSQUEDA DE TRANSACCIONES CONTABLES X CUENTA --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type="submit" class="btn btn-success" id="btnBuscar" name="btnBuscar" >
                            <i class="icon ion-search"></i>
                            Mostrar Resultados
                        </button>
                    </div>
                    <a href="#" target="" class="btn btn-warning text-white">
                        Imprimir PDF
                        <i class="icon ion-document"></i>
                    </a>
                </div>
            </form>
        </div>
        <script type="text/javascript">
            $('#FechaDesde, #FechaHasta').datepicker({
                //format: "dd/mm/yyyy",
                dateFormat: "yy-mm-dd",
                language: 'es',
                changeYear: true,
                changeMonth: true,
                yearRange: "2018:2050",
                showAnim: "slide",
                autoclose: true
            }).datepicker({}).datepicker("setDate", new Date());
        </script>
    </body>
</html>
