<%-- 
    Document   : EditarCuentaContable
    Created on : 08-13-2018, 08:09:28 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="model.DaoContabilidad"%>
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
    int IdCatalogo = Integer.valueOf(request.getParameter("IDCATALOGO"));
    DaoContabilidad datos = new DaoContabilidad();
    datos.BuscarIBGLACCNTS(IdCatalogo);
    String AccountName = datos.GetAccountName;
    String AccountNumber = datos.GetAccountNumber;
    int GLTPCLSID = datos.GetGLTPCLSID;
    String Commnets = "";
    if (datos.GetComments != null) {
        Commnets = datos.GetComments;
    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="../css/jquery-ui-1.12.1.css">
        <link rel="stylesheet" href="../css/bootstrap.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/bootstrap-grid.css">
        <link rel="stylesheet" href="../css/bootstrap-grid.min.css">
        <link rel="stylesheet" href="../css/bootstrap-reboot.css">
        <link rel="stylesheet" href="../css/bootstrap-reboot.min.css">
        <link rel="stylesheet" href="../css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="../css/responsive.bootstrap.min.css">
        <link rel="stylesheet" href="../css/font-awesome.min.css">
        <script src="../js/bootstrap.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/bootstrap.bundle.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/jquery.js"></script>
        <script src="../js/jquery.dataTables.js"></script>
        <script src="../js/dataTables.bootstrap.min.js"></script>
        <script src="../js/dataTables.responsive.min.js"></script>
        <script src="../js/responsive.bootstrap.min.js"></script>
        <script src="../js/jquery-ui.min.js"></script>
        <script src="../js/calendario.js"></script>
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
                <h1 style="color: #FFFFFF; text-align: center;">Editando Cuenta Contable</h1>                
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
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="UpdateCtaContable" hidden="true">
                    <input type="text" class="form-control" id="form-IdCatalogo" name="form-IdCatalogo" value="<%=IdCatalogo%>" hidden="true">
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Tipo Cuenta</strong></span>
                        <select class="form-control col-sm-2" id="form-TipoCuenta" name="form-TipoCuenta" style="text-align: center" disabled="true">
                            <% try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    String consulta = "Select GLTPCLSID,GLTPNAME From `IBGLTYPACC` WHERE GLTPCLSID=" + GLTPCLSID + " ";
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
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Cuenta</strong></span>
                        <input id="form-NombreCuenta" name="form-NombreCuenta" type="text" class="form-control col-sm-6" placeholder="Ingresa Nombre Cuenta..." required="true" style="text-align: center" maxlength="80" value="<%=AccountName%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Cuenta</strong></span>
                        <input id="form-NumeroCuenta" name="form-NumeroCuenta" type="text" class="form-control col-sm-6" required="true" style="text-align: center" maxlength="15" value="<%=AccountNumber%>">
                    </div>
                    <div class="input-group checkbox-inline">
                        <span class="input-group-addon col-sm-2"><strong>Sub-Cuenta De</strong>
                            <input type="checkbox" id="SubCuenta" name="SubCuenta" disabled="">
                        </span>
                        <select class="form-control col-sm-5" id="form-CuentaPadre" name="form-CuentaPadre" disabled="true">
                            <option value=""></option>
                            <%  try {
                                    ConexionDB conn = new ConexionDB();
                                    conn.Conectar();
                                    String consulta = "SELECT IDCATALOGO, AccountNumber, AccountName FROM `IBGLACCNTS` WHERE Active='S'  ORDER BY `AccountNumber` ASC";
                                    ResultSet rs = null;
                                    PreparedStatement pst = null;
                                    pst = conn.conexion.prepareStatement(consulta);
                                    rs = pst.executeQuery();
                                    while (rs.next()) {
                                        out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + " - " + rs.getString(3) + "</option>");
                                    }; // fin while 
                                    conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                    rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                    pst.close(); //CIERRO EL PREPARED STATEMENT.
                                } //fin try no usar ; al final de dos o mas catchs 
                                catch (SQLException e) {
                                };%>
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Descripcion</strong></span>
                        <input type="text" class="form-control col-sm-6" id="form-Descripcion" name="form-Descripcion" maxlength="200" value="<%=Commnets%>">
                    </div>
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "CatalogoContable.jsp"' class='btn btn-primary'>Volver al Catalago</button>
                    </div>
                    <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Guardar Cambios</button>
                </div>
            </form> <%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>
