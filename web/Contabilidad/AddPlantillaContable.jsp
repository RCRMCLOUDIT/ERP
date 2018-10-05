<%-- 
    Document   : AddPlantillaContable
    Created on : 08-29-2018, 01:23:34 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <%--ESTILOS DEL FRAMEWORK BOOTSTRAP --%>
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/bootstrap-select.css">
        <%--JS DEL FRAMEWORK BOOTSTRAP Y JQUERY--%>
        <script src="../js/jquery.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/bootstrap-select.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>   
        <script type="text/javascript">
            function confirmar()
            {
                if (!confirm("¿Desea Eliminar esta Linea de la Plantilla?"))
                {
                    return false; //no se borra 
                } else
                {
                    //si se borra 
                    return true;
                }
            }
        </script>
        <title>Plantilla Comprobante</title>
    </head>
    <%@include file="../Commons/Menu.jsp"%>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Plantillas de Comprobante Contable</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de Plantilla</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DE LA PLANTILLA CONTABLE --%>
            <form id="PlantillaComprobante" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE LOS DATOS DE LA PLANTILLA CONTABLE --%>                  
                    <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="AddPlantillaComprobante" hidden="true">
                    <%if (request.getParameter("IdPlantilla") != null) {%>
                    <input type="text" class="form-control" id="form-IdPlantilla" name="form-IdPlantilla" value="<%=request.getParameter("IdPlantilla")%>" hidden="true">
                    <%} else {%>
                    <input type="text" class="form-control" id="form-IdPlantilla" name="form-IdPlantilla" value="0" hidden="true">
                    <%}%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Descripcion</strong></span>
                        <%if (request.getParameter("DescPlant") != null) {%>
                        <input class="form-control col-sm-6" id="form-DescripcionPlantilla" name="form-DescripcionPlantilla" type="text" required="true" style="text-align: center" maxlength="255" value="<%=request.getParameter("DescPlant")%>">
                        <%} else {%>
                        <input class="form-control col-sm-6" id="form-DescripcionPlantilla" name="form-DescripcionPlantilla" type="text" placeholder="Ingresa una Descripcion..." required="true" style="text-align: center" maxlength="255">
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
                        <select class="selectpicker col-sm-6" id="form-CtaContable" name="form-CtaContable" data-live-search="true" title="Buscar Cuenta...">
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
                        <button type='button' onclick='location.href = "PlantillaComprobante.jsp"' class='btn btn-primary'>Volver A Lista de Plantillas</button>
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>              
            </form><%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
            <div class="panel-body">
                <table class="table table-hover" id="tblPlantillaContable">
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
                                if (request.getParameter("IdPlantilla") != null) {
                                    pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.AccountNumber, IBGLACCNTS.AccountName, IBGLTDPST.GLTMAMOUNT, IBGLTDPST.GLTMMEMODET, IBGLTDPST.MOVEMENTTYPE, IBGLTDPST.GLTMID, IBGLTDPST.GLTMLINEID,IBGLTDPST.IDCATALOGO, IBGLTDPST.GLTMMEMO FROM IBGLTDPST INNER JOIN IBGLACCNTS ON IBGLTDPST.IDCATALOGO=IBGLACCNTS.IDCATALOGO WHERE IBGLTDPST.GLTMID=" + request.getParameter("IdPlantilla") + " ");
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
                                    out.println("<TD> <a class='btn btn-primary text-white' href='EditarLineTemp.jsp?IdPlantilla=" + rs.getInt(6) + "&IdLine=" + rs.getInt(7) + "&DescPlant=" + rs.getString(9) + "'>Editar</a></TD>");//EDITAR LINEA
                                    //FORMULARIO OCULTO PARA MANDAR A ELIMINAR LA LINEA DE LA PLANTILLA
                                    out.println("<TD>"
                                            + "<form id='BorraLinePlantilla' role='form' action='../ServletContabilidad' method='POST' onsubmit='return confirmar();'>"
                                            + "<input id='form-Accion' name='form-Accion' type='text' value='DeleteLineTemplate' hidden='true'>"
                                            + "<input id='form-IdPlantilla' name='form-IdPlantilla' type='text' value='" + rs.getInt(6) + "' hidden='true'>"
                                            + "<input id='form-IdLinea' name='form-IdLinea' type='text' value='" + rs.getInt(7) + "' hidden='true'>"
                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(8) + "' hidden='true'>"
                                            + "<input id='form-DescripcionPlantilla' name='form-DescripcionPlantilla' type='text' value='" + rs.getString(9) + "' hidden='true'>"
                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarLinea' name='btnEliminarLinea'>Eliminar</button>"
                                            + "</form>"
                                            + "</TD>");
                                    out.println("</TR>");
                                }; // fin while 
                                conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                pst.close(); //CIERRO EL PREPARED STATEMENT
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
                                out.println("<TD></TD>");
                                out.println("</TR>");
                            } //fin try no usar ; al final de dos o mas catchs 
                            catch (SQLException e) {
                            };%>
                    </tbody>
                </table>
            </div>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>
