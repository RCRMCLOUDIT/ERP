<%-- 
    Document   : AgregarComprobante
    Created on : 09-11-2018, 01:53:25 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    request.getParameter("form-IdPlantilla");
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
        <script type="text/javascript">
            function confirmar()
            {
                if (!confirm("¿Desea Eliminar esta Linea del Comprobante?"))
                {
                    return false; //no se borra 
                } else
                {
                    //si se borra 
                    return true;
                }
            }
        </script>
        <title>Comprobante Contable</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Comprobante Contable</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos del Comprobante</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DE LA PLANTILLA CONTABLE --%>
            <form id="ComprobanteContable" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE LOS DATOS DE LA PLANTILLA CONTABLE --%>                  
                    <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="AddComprobanteContable" hidden="true">
                    <%if (request.getParameter("IdPlantilla") != null) {%>
                    <input type="text" class="form-control" id="form-IdPlantilla" name="form-IdPlantilla" value="<%=request.getParameter("IdPlantilla")%>" hidden="true">
                    <%} else {%>
                    <input type="text" class="form-control" id="form-IdPlantilla" name="form-IdPlantilla" value="0" hidden="true">
                    <%}%>
                    <%if (request.getParameter("IdComprobante") != null) {%>
                    <input type="text" class="form-control" id="form-IdComprobante" name="form-IdComprobante" value="<%=request.getParameter("IdComprobante")%>" hidden="true">
                    <%} else {%>
                    <input type="text" class="form-control" id="form-IdComprobante" name="form-IdComprobante" value="0" hidden="true">
                    <%}%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Descripcion</strong></span>
                        <%if (request.getParameter("DescComp") != null) {%>
                        <input class="form-control col-sm-8" id="form-DescripcionPlantilla" name="form-DescripcionComprobante" type="text" required="true" style="text-align: center" maxlength="255" value="<%=request.getParameter("DescComp")%>">
                        <%} else {%>
                        <input class="form-control col-sm-8" id="form-DescripcionPlantilla" name="form-DescripcionComprobante" type="text" placeholder="Ingresa una Descripcion..." required="true" style="text-align: center" maxlength="255">
                        <%}%>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Referencia</strong></span>
                        <%if (request.getParameter("RefNum") != null) {%>
                        <input class="form-control col-sm-4" id="form-NumeroReferencia" name="form-NumeroReferencia" type="text" required="true" style="text-align: center" maxlength="20" value="<%=request.getParameter("RefNum")%>">
                        <%} else {%>
                        <input class="form-control col-sm-4" id="form-NumeroReferencia" name="form-NumeroReferencia" type="text" placeholder="Ingresa un # Referencia..." required="true" style="text-align: center" maxlength="20">
                        <%}%>
                        <span class="input-group-addon col-sm-2"><strong>Fecha</strong></span>
                        <%if (request.getParameter("Fecha") != null) {%>
                        <input type="text" class="form-control col-sm-2" id="Fecha" name="Fecha" style="text-align: center" readonly="true" value="<%=request.getParameter("Fecha")%>">
                        <script type="text/javascript">
                            $('#Fecha').datepicker({
                                //format: "dd/mm/yyyy",
                                dateFormat: "yy-mm-dd",
                                language: 'es',
                                changeYear: true,
                                changeMonth: true,
                                yearRange: "2018:2050",
                                showAnim: "slide",
                                autoclose: true
                            });
                        </script>
                        <%} else {%>
                        <input type="text" class="form-control col-sm-2" id="Fecha" name="Fecha" style="text-align: center" readonly="true">
                        <script type="text/javascript">
                            $('#Fecha').datepicker({
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
                        <%}%>
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-6"><strong>Cuenta Contable</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Monto</strong></span>
                        <span class="input-group-addon col-sm-3"><strong>Descripcion</strong></span>
                        <span class="input-group-addon col-sm-1"><strong>Tipo</strong></span>
                    </div>
                    <div class="input-group">
                        <select class="form-control col-sm-6" id="form-CtaContable" name="form-CtaContable" style="text-align: center">
                            <option value='0'></option>
                            <%  try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    String consulta = "SELECT IDCATALOGO, AccountNumber, AccountName, AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6 FROM `IBGLACCNTS` WHERE Active='S'  ORDER BY `AccountNumber` ASC";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement(consulta);
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        if (request.getParameter("IdCatalago") != null && Integer.valueOf(request.getParameter("IdCatalago")) > 0) {
                                            out.println("<option selected='true' value='" + rs.getInt(1) + "'>" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                        } else {
                                            //INDEX 4 = ACCOUNT LEVEL1; INDEX 5 = ACCOUNT LEVEL2; INDEX 6 = ACCOUNT LEVEL3
                                            //INDEX 7 = ACCOUNT LEVEL4; INDEX 8 = ACCOUNT LEVEL5; INDEX 9 = ACCOUNT LEVEL6
                                            if (Integer.valueOf(rs.getString(4)) > 0 && Integer.valueOf(rs.getString(5)) == 0 && Integer.valueOf(rs.getString(6)) == 0) {
                                                //ES DE NIVEL 1
                                                out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(5)) > 0 && Integer.valueOf(rs.getString(6)) == 0 && Integer.valueOf(rs.getString(7)) == 0) {
                                                //ES DE NIVEL 2
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(6)) > 0 && Integer.valueOf(rs.getString(7)) == 0 && Integer.valueOf(rs.getString(8)) == 0) {
                                                //ES DE NIVEL 3
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(7)) > 0 && Integer.valueOf(rs.getString(8)) == 0 && Integer.valueOf(rs.getString(9)) == 0) {
                                                //ES DE NIVEL 4
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(8)) > 0 && Integer.valueOf(rs.getString(9)) == 0) {
                                                //ES DE NIVEL 5
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(9)) > 0) {
                                                //ES DE NIVEL 6
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                        }
                                    }; // fin while 
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                        <input class="form-control col-sm-2" id="form-Monto" name="form-Monto" type="number" step="any" value="0.00" min="0" style="text-align: center" required="true">
                        <input class="form-control col-sm-3" id="form-DescLinea" name="form-DescLinea" type="text" maxlength="255">
                        <select class="form-control col-sm-1" id="form-TipoMov" name="form-TipoMov" style="text-align: center">
                            <option value='C'>Credito</option>
                            <option value='D'>Debito</option>
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-6"><strong>Centro de Costo</strong></span>
                    </div>
                    <div class="input-group" style="text-align: center;">
                        <select class="form-control col-sm-2" id="form-TipoCC" name="form-TipoCC" style="text-align: center">
                            <option value='0'></option>
                            <option value='0'>Division</option>
                            <option value='0'>Empleado</option>
                            <option value='0'>Proveedor</option>
                            <option value='0'>Cliente</option>
                        </select>
                        <select class="form-control col-sm-4" id="form-CtaCentroCost" name="form-CtaCentroCost" style="text-align: center">
                            <option value='0'></option>
                        </select>
                        <div class="col-sm-1"> </div>
                        <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Agregar Linea</button>
                        <div class="col-sm-1"> </div>
                        <button type='button' onclick='location.href = "ListarComprobante.jsp"' class='btn btn-primary'>Volver A Lista de Comprobantes</button>
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>              
            </form><%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
            <div class="panel-body">
                <table class="table table-hover" id="tblComprobantesContable">
                    <thead style="background-color: #4682B4">
                        <tr>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Cuenta Contable</strong></th>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Monto</strong></th>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Descripcion</strong></th>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Tipo</strong></th>
                            <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Centro de Costo</strong></th>
                            <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong></strong></th>
                        </tr>
                    </thead>
                    <tbody style="background-color: #C7C6C6;">
                        <%try {
                                ConexionDB conn = new ConexionDB();
                                conn.Conectar();
                                ResultSet rs = null;
                                PreparedStatement pst = null;
                                double TotalCreditos = 0.00, TotalDebitos = 0.00;
                                if (request.getParameter("IdComprobante") != null) {
                                    pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.AccountNumber, IBGLACCNTS.AccountName, IBGLBATCHDST.GLBACHAMOUNT, IBGLBATCHDST.GLBACHMEMODET, IBGLBATCHDST.GLBACHMOVEMENTTYPE, IBGLBATCHDST.GLTMID, IBGLBATCHDST.GLBACHLINEID, IBGLBATCHDST.IDCATALOGO, IBGLBATCHDST.GLBACHMEMO, IBGLBATCHDST.IdComprobante, IBGLBATCHDST.GLBACHMREF, IBGLBATCHDST.GLBACHDATE, IBGLBATCHDST.GLBACHSTATUS FROM IBGLBATCHDST INNER JOIN IBGLACCNTS ON IBGLBATCHDST.IDCATALOGO=IBGLACCNTS.IDCATALOGO WHERE IBGLBATCHDST.IdComprobante=" + request.getParameter("IdComprobante") + " ");
                                } else {
                                    pst = conn.conexion.prepareStatement("");
                                }

                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    out.println("<TR style='text-align: center;'>");
                                    out.println("<TD style='color: #000000; text-align: left;'>" + rs.getString(1) + " - " + rs.getString(2) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                    out.println("<TD style='color: #000000;'>" + rs.getDouble(3) + "</TD>");//MONTO
                                    out.println("<TD style='color: #000000;'>" + rs.getString(4) + "</TD>");//DESCRIPCION LINEA
                                    out.println("<TD style='color: #000000;'>" + rs.getString(5) + "</TD>");//TIPO MOV. CREDITO Ó DEBITO
                                    if (rs.getString(5).equals("C")) {
                                        TotalCreditos = TotalCreditos + rs.getDouble(3);
                                    }
                                    if (rs.getString(5).equals("D")) {
                                        TotalDebitos = TotalDebitos + rs.getDouble(3);
                                    }
                                    out.println("<TD style='color: #000000;'></TD>");//TIPO DE CENTRO DE COSTO
                                    out.println("<TD style='color: #000000;'></TD>");//CUENTA CENTRO COSTO
                                    out.println("<TD><a class='btn btn-primary text-white' href='EditarLineBACH.jsp?IdComprobante=" + rs.getInt(10) + "&DescComp=" + rs.getString(4) + "&RefNum=" + rs.getString(11) + "&Fecha=" + rs.getString(12) + "&IdLine=" + rs.getString(7) + " '>Editar</a></TD>");//EDITAR LINEA
                                    //FORMULARIO OCULTO PARA MANDAR A ELIMINAR LA LINEA DEL COMPROBANTE
                                    out.println("<TD>"
                                            + "<form id='DeleteLineBATCH' role='form' action='../ServletContabilidad' method='POST' onsubmit='return confirmar();'>"
                                            + "<input id='form-Accion' name='form-Accion' type='text' value='DeleteLineBATCH' hidden='true'>"
                                            + "<input id='form-IdComprobante' name='form-IdComprobante' type='text' value='" + rs.getInt(10) + "' hidden='true'>"
                                            + "<input id='form-IdLinea' name='form-IdLinea' type='text' value='" + rs.getInt(7) + "' hidden='true'>"
                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(8) + "' hidden='true'>"
                                            + "<input id='form-DescripcionComprobante' name='form-DescripcionComprobante' type='text' value='" + rs.getString(9) + "' hidden='true'>"
                                            + "<input id='form-NumeroReferencia' name='form-NumeroReferencia' type='text' value='" + rs.getString(11) + "' hidden='true'>"
                                            + "<input id='Fecha' name='Fecha' type='text' value='" + rs.getString(12) + "' hidden='true'>"
                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarLinea' name='btnEliminarLinea'>Eliminar</button>"
                                            + "</form>"
                                            + "</TD>");
                                    out.println("</TR>");
                                }; // fin while 
                                //LINEA PARA MOSTRAR LOS TOTAL CREDITOS Y DEBITOS.
                                out.println("<TR>");
                                out.println("<TD style='color: #000000;'></TD>");
                                if (TotalCreditos == TotalDebitos) {
                                    out.println("<TD colspan='2' style='color: #FFFFFF;'><input class='form-control bg-success text-white' id='form-Message' name='form-Message' type='text' value='Creditos Y Debitos Balanceados' readonly></TD>");
                                } else {
                                    out.println("<TD colspan='2' style='color: #FFFFFF;'><input class='form-control bg-danger text-white' id='form-Message' name='form-Message' type='text' value='Creditos Y Debitos Desbalanceados' readonly></TD>");
                                }
                                out.println("<TD style='color: #000000; text-align: right;'><Strong>Total Creditos</Strong></TD>");
                                out.println("<TD style='color: #000000;'>" + TotalCreditos + "</TD>");
                                out.println("<TD style='color: #000000; text-align: right;'><Strong>Total Debitos</Strong></TD>");
                                out.println("<TD style='color: #000000;'>" + TotalDebitos + "</TD>");
                                out.println("<TD style='color: #000000;'>"
                                        + "<form id='DeleteLineBATCH' role='form' action='../ServletContabilidad' method='POST' onsubmit='return confirmar();'>"
                                        + "<input id='form-Accion' name='form-Accion' type='text' value='DeleteLineBATCH' hidden='true'>"
                                        + "<input id='form-IdComprobante' name='form-IdComprobante' type='text' value='" + request.getParameter("IdComprobante") + "' hidden='true'>"
                                        + "<input id='form-DescripcionComprobante' name='form-DescripcionComprobante' type='text' value='" + request.getParameter("DescComp") + "' hidden='true'>"
                                        + "<input id='form-NumeroReferencia' name='form-NumeroReferencia' type='text' value='" + request.getParameter("RefNum") + "' hidden='true'>"
                                        + "<input id='Fecha' name='Fecha' type='text' value='" + request.getParameter("Fecha") + "' hidden='true'>"
                                        + "<button type='submit' class='btn btn-success' id='btnEliminarLinea' name='btnEliminarLinea'>Aprobar Comprobante</button>"
                                        + "</form>"
                                        + "</TD>");
                                out.println("</TR>");
                            } //fin try no usar ; al final de dos o mas catchs 
                            catch (SQLException e) {
                            };%>
                    </tbody>
                </table>
            </div>
        </div><%--FIN DIV GRUPO DE TABS--%>
        <br>
        <div class="col-2 offset-6"> 
            <%if (request.getParameter("IdComprobante") != null) {%>
            <a href="ReporteComprobante.jsp?IdComprobante=<%=request.getParameter("IdComprobante")%>" target="_blank" class="btn btn-warning text-white">Imprimir Comprobante</a>
            <%}%>        
        </div>
        <br>
    </body>
</html>
