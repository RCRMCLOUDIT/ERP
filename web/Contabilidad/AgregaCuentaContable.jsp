<%-- 
    Document   : AgregaCuentaContable
    Created on : 08-09-2018, 09:37:05 AM
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
        <script type="text/javascript">
            // FUNCION PARA MOSTRAR O OCULTAR, LAS CUENTAS, EN CASO DE QUE LA NUEVA CUENTA VAYA A SER UNA SUB-CUENTA
            $(document).ready(function () {
                $('#SubCuenta').click(function () {
                    var mostrar = $('#SubCuenta').is(":checked");
                    if (mostrar === true) {
                        document.getElementById("form-CuentaPadre").disabled = '';
                        //alert("Mostrando Cuentas");
                    } else {
                        document.getElementById("form-CuentaPadre").disabled = 'true';
                        //alert("Oculto Cuentas");
                    }
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                $("#form-TipoCuenta").change(function () {
                    var IdTipoCuenta = $('#form-TipoCuenta').val();
                    //alert(IdTipoCuenta);
                    $('#form-TipoCuenta').val($(this).val());
                    location.href = "AgregaCuentaContable.jsp?IdTipoCuenta=" + IdTipoCuenta;
                });
            });
        </script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>        
        <title>Nueva Cuenta Contable</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body style="align-content: center">
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Agregando Nueva Cuenta</h1>                
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de Cuenta</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DE LA CUENTA CONTABLE --%>
            <form id="AddCtaContable" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE LOS DATOS DE LA CUENTA CONTABLE --%>
                    <%if (Msg == 1) {%>
                    <div class="alert alert-danger alert-dismissible" id="myAlert">
                        <a href="#" class="close">&times;</a>
                        <strong>Error!</strong> Nombre de Cuenta Contable Ya existe!
                    </div>                    
                    <%}%>
                    <%if (Msg == 2) {%>
                    <div class="alert alert-danger alert-dismissible" id="myAlert">
                        <a href="#" class="close">&times;</a>
                        <strong>Error!</strong> Numero de Cuenta Contable Ya existe!
                    </div>
                    <%}%>                    
                    <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="AddCtaContable" hidden="true">
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Tipo Cuenta</strong></span>
                        <select class="form-control col-sm-2" id="form-TipoCuenta" name="form-TipoCuenta" style="text-align: center">
                            <option value=''></option>
                            <% try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    String consulta = "Select GLTPCLSID,GLTPNAME From `IBGLTYPACC`";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement(consulta);
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        if (request.getParameter("IdTipoCuenta") != null) {
                                            if (rs.getInt(1) == Integer.valueOf(request.getParameter("IdTipoCuenta"))) {
                                                out.println("<option value='" + rs.getInt(1) + "' selected='true'>" + rs.getString(2) + "</option>");
                                            } else {
                                                out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
                                            }
                                        } else {
                                            out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
                                        }
                                    }; // fin while 
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Cuenta</strong></span>
                        <%if (request.getParameter("Name") != null){%>
                        <input id="form-NombreCuenta" name="form-NombreCuenta" type="text" class="form-control col-sm-6" placeholder="Ingresa Nombre Cuenta..." required="true" style="text-align: center" maxlength="80" value="<%=request.getParameter("Name")%>">
                        <%}else {%>
                        <input id="form-NombreCuenta" name="form-NombreCuenta" type="text" class="form-control col-sm-6" placeholder="Ingresa Nombre Cuenta..." required="true" style="text-align: center" maxlength="80">
                        <%}%>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Cuenta</strong></span>
                        <%if (request.getParameter("Num") != null){%>
                        <input id="form-NumeroCuenta" name="form-NumeroCuenta" type="text" class="form-control col-sm-6" required="true" style="text-align: center" maxlength="15" value="<%=request.getParameter("Num")%>">
                        <%}else {%>
                        <input id="form-NumeroCuenta" name="form-NumeroCuenta" type="text" class="form-control col-sm-6" required="true" style="text-align: center" maxlength="15">
                        <%}%>
                    </div>
                    <div class="input-group checkbox-inline">
                        <span class="input-group-addon col-sm-2"><strong>Sub-Cuenta De</strong>
                            <input type="checkbox" id="SubCuenta" name="SubCuenta">
                        </span>
                        <select class="form-control col-sm-6" id="form-CuentaPadre" name="form-CuentaPadre" disabled="true" data-live-search="true">
                            <option value="" disabled="true" selected="true">Selecciona una Cuenta</option>
                            <%  try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    //String consulta = "SELECT IDCATALOGO, AccountNumber, AccountName FROM `IBGLACCNTS` WHERE Active='S' AND GLTPCLSID = " + IdTipoCuenta + " ORDER BY `AccountNumber` ASC";
                                    //String consulta = "SELECT IDCATALOGO, AccountNumber, AccountName FROM `IBGLACCNTS` WHERE Active='S' ORDER BY `AccountNumber` ASC";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.IDCATALOGO, IBGLACCNTS.AccountNumber, IBGLACCNTS.AccountName, IBGLTYPACC.GLTPNAME, IBGLACCNTS.AccountLevel1, IBGLACCNTS.AccountLevel2, IBGLACCNTS.AccountLevel3, IBGLACCNTS.AccountLevel4, IBGLACCNTS.AccountLevel5, IBGLACCNTS.AccountLevel6, IBGLTYPACC.GLTPCLSID FROM `IBGLACCNTS` INNER JOIN `IBGLTYPACC` ON IBGLACCNTS.GLTPCLSID=IBGLTYPACC.GLTPCLSID WHERE IBGLACCNTS.Active ='S' ORDER BY IBGLACCNTS.AccountNumber ASC");
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        // MANDO A VERIFICAR LOS NIVELES
                                        //INDEX 5 = ACCOUNT LEVEL1; INDEX 6 = ACCOUNT LEVEL2; INDEX 7 = ACCOUNT LEVEL3
                                        //INDEX 8 = ACCOUNT LEVEL4; INDEX 9 = ACCOUNT LEVEL5; INDEX 10 = ACCOUNT LEVEL6
                                        if (request.getParameter("IdTipoCuenta") != null) {
                                            if (Integer.valueOf(rs.getString(5)) > 0 && Integer.valueOf(rs.getString(6)) == 0 && Integer.valueOf(rs.getString(7)) == 0 && Integer.valueOf(request.getParameter("IdTipoCuenta")) == rs.getInt(11)) {
                                                //PARA CUENTAS DE NIVEL 1
                                                out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(6)) > 0 && Integer.valueOf(rs.getString(7)) == 0 && Integer.valueOf(rs.getString(8)) == 0 && Integer.valueOf(request.getParameter("IdTipoCuenta")) == rs.getInt(11)) {
                                                //PARA CUENTAS DE NIVEL 2
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(7)) > 0 && Integer.valueOf(rs.getString(8)) == 0 && Integer.valueOf(rs.getString(9)) == 0 && Integer.valueOf(request.getParameter("IdTipoCuenta")) == rs.getInt(11)) {
                                                //PARA CUENTAS DE NIVEL 3
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(8)) > 0 && Integer.valueOf(rs.getString(9)) == 0 && Integer.valueOf(rs.getString(10)) == 0 && Integer.valueOf(request.getParameter("IdTipoCuenta")) == rs.getInt(11)) {
                                                //PARA CUENTAS DE NIVEL 4
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(9)) > 0 && Integer.valueOf(rs.getString(10)) == 0 && Integer.valueOf(request.getParameter("IdTipoCuenta")) == rs.getInt(11)) {
                                                //PARA CUENTAS DE NIVEL 5
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                            if (Integer.valueOf(rs.getString(10)) > 0 & Integer.valueOf(request.getParameter("IdTipoCuenta")) == rs.getInt(11)) {
                                                //PARA CUENTAS DE NIVEL 6
                                                out.println("<option value='" + rs.getInt(1) + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                            }
                                        }

                                    }; // fin while 
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Descripcion</strong></span>
                        <%if (request.getParameter("Desc") != null){%>
                        <input type="text" class="form-control col-sm-6" id="form-Descripcion" name="form-Descripcion" maxlength="200" value="<%=request.getParameter("Desc")%>">
                        <%}else{%>
                        <input type="text" class="form-control col-sm-6" id="form-Descripcion" name="form-Descripcion" maxlength="200">
                        <%}%>
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "CatalogoContable.jsp"' class='btn btn-primary'>Volver al Catalago</button>
                    </div>
                    <button type="submit" class="btn btn-primary" id="btnAgregar" name="btnAgregar" >Guardar</button>
                </div>
            </form> <%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>

