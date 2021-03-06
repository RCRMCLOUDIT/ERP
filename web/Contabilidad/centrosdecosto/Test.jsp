<%-- 
    Document   : Test
    Created on : 09-25-2018, 03:42:45 PM
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
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>              
            </form><%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>