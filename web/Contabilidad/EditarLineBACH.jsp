<%-- 
    Document   : EditarLineBACH
    Created on : 09-13-2018, 10:15:25 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="model.DaoContabilidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int IdComprobante = Integer.valueOf(request.getParameter("IdComprobante"));
    int IdLinea = Integer.valueOf(request.getParameter("IdLine"));
    String DescComp = request.getParameter("DescComp");
    String RefNum = request.getParameter("RefNum");
    String Fecha = request.getParameter("Fecha");
    DaoContabilidad datos = new DaoContabilidad();
    datos.GetDetailLineBATCH(IdComprobante, IdLinea);
    int IdCatalogo = datos.GetIdCatalogo;
    String GLTMMEMODET = datos.GetGLBACHMEMODET;
    Double GLTMAMOUNT = datos.GetGLBACHAMOUNT;
    String MOVEMENTTYPE = datos.GetGLBACHMOVEMENTTYPE;
    int GLTMCCTYPID = datos.GetGLBACHCCTYPID;
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
                <h1 style="color: #FFFFFF; text-align: center;">Editando Linea de Comprobante</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de Linea de Comprobante</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DEL COMPROBANTE CONTABLE --%>
            <form id="PlantillaComprobante" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE LOS DATOS DE LA PLANTILLA CONTABLE --%>                  
                    <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="UpdateLineBATCH" hidden="true">
                    <%if (request.getParameter("IdComprobante") != null) {%>
                    <input type="text" class="form-control" id="form-IdComprobante" name="form-IdComprobante" value="<%=request.getParameter("IdComprobante")%>" hidden="true">
                    <input type="text" class="form-control" id="form-IdLinea" name="form-IdLinea" value="<%=request.getParameter("IdLine")%>" hidden="true">
                    <input type="text" class="form-control col-sm-8" id="form-DescripcionComprobante" name="form-DescripcionComprobante" required="true" style="text-align: center" maxlength="255" value="<%=request.getParameter("DescComp")%>" hidden="true">
                    <input type="text" class="form-control col-sm-4" id="form-NumeroReferencia" name="form-NumeroReferencia" required="true" style="text-align: center" maxlength="20" value="<%=request.getParameter("RefNum")%>" hidden="true">
                    <input type="text" class="form-control col-sm-4" id="Fecha" name="Fecha" required="true" style="text-align: center" maxlength="10" value="<%=request.getParameter("Fecha")%>" hidden="true">
                    <%} else {%>
                    <input type="text" class="form-control" id="form-IdComprobante" name="form-IdComprobante" value="0" hidden="true">
                    <%}%>
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
                                    String consulta = "";
                                    consulta = "SELECT IDCATALOGO, AccountNumber, AccountName, AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6 FROM `IBGLACCNTS` WHERE Active='S'  ORDER BY `AccountNumber` ASC";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement(consulta);
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        if (IdCatalogo > 0 && rs.getInt(1) == IdCatalogo) {
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
                                    conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                    rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                    pst.close(); //CIERRO EL PREPARED STATEMENT.
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                        <input class="form-control col-sm-2" id="form-Monto" name="form-Monto" type="number" step="any" value="<%=GLTMAMOUNT%>" min="0" style="text-align: center" required="true">
                        <input class="form-control col-sm-3" id="form-DescLinea" name="form-DescLinea" type="text" value="<%=GLTMMEMODET%>" maxlength="255">
                        <select class="form-control col-sm-1" id="form-TipoMov" name="form-TipoMov" style="text-align: center">
                            <%if (MOVEMENTTYPE.equals("C")) {%>
                            <option value='C'>Credito</option>
                            <option value='D'>Debito</option>
                            <%} else {%>
                            <option value='D'>Debito</option>
                            <option value='C'>Credito</option>
                            <%}%>
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
                        <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Guardar Cambios</button>
                        <div class="col-sm-1"> </div>
                        <button type='button' onclick='location.href = "AgregarComprobante.jsp?IdComprobante=<%=IdComprobante%>&DescComp=<%=DescComp%>&RefNum=<%=RefNum%>&Fecha=<%=Fecha%>"' class="btn btn-primary">Volver A Comprobante</button>
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>              
            </form><%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
            <div class="panel-body">

            </div>
        </div><%--FIN DIV GRUPO DE TABS--%>
        <br>
    </body>
</html>