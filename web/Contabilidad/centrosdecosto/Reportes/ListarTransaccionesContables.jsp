<%-- 
    Document   : ListarTransaccionesContables
    Created on : 10-01-2018, 12:06:32 PM
    Author     : Ing. Moises Romero Mojica
    Owner:     :Cloud IT Systems, S.A
--%>
<%@page import="model.DaoContabilidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%
    String DirActual = request.getContextPath();
    int IdCatalogo = Integer.valueOf(request.getParameter("form-CtaContable"));
    DaoContabilidad datos = new DaoContabilidad();
    datos.BuscarIBGLACCNTS(IdCatalogo);
    String NombreCuenta = datos.GetAccountName;
    String NumeroCuenta = datos.GetAccountNumber;
    String FechaDesde = request.getParameter("FechaDesde");
    String FechaHasta = request.getParameter("FechaHasta");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <%--ESTILOS DEL FRAMEWORK BOOTSTRAP --%>
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap-select.css">
        <%--ESTILOS DEL FRAMEWORK IONICONS --%>
        <link rel="stylesheet" href="<%=DirActual%>/ionicons/css/ionicons.min.css">
        <%--JS DEL FRAMEWORK BOOTSTRAP Y JQUERY--%>
        <script src="<%=DirActual%>/js/jquery.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap.bundle.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap-select.js"></script>
        <script src="<%=DirActual%>/js/blocker.js"></script>
        <script src="<%=DirActual%>/js/cross-browser.js"></script>

        <title>Listado Transacciones</title>
    </head>
    <%@include file="../../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Detalle de Transacciones Por Cuenta</h1>             
            </center>
        </div>
        <table class="table table-hover" id="tblPoliza">
            <thead style="background-color: #4682B4">
                <tr><th colspan="12" style="color: #FFFFFF; text-align: center;"><h3>Cuenta Contable: <%=NumeroCuenta + " " + NombreCuenta%> </h3></th></tr>
                <tr>
                    <th style="color: #FFFFFF; text-align: center;">Tipo Doc.</th>
                    <th style="color: #FFFFFF; text-align: center;">Fecha</th>
                    <th style="color: #FFFFFF; text-align: center;">Doc# / Ref#</th>
                    <th style="color: #FFFFFF; text-align: center;">Entidad</th>
                    <th style="color: #FFFFFF; text-align: center;">Memo</th>
                    <th style="color: #FFFFFF; text-align: center;">Cuenta Contable</th>
                    <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Centro de Costo</strong></th>
                    <th style="color: #FFFFFF; text-align: center;">Debito</th>
                    <th style="color: #FFFFFF; text-align: center;">Credito</th>
                    <th style="color: #FFFFFF; text-align: center;">Balance</th>
                </tr>
            </thead>
            <tbody style="background-color: #C7C6C6;">
                <% // declarando y creando objetos globales 
                    //Integer cod = DaoLogin.IdUsuario;
                    // construyendo forma dinamica 
                    // mandando el sql a la base de datos 
                    try {
                        ConexionDB conn = new ConexionDB();
                        conn.Conectar();
                        ResultSet rs = null;
                        PreparedStatement pst = null;
                        pst = conn.conexion.prepareStatement("SELECT IBGLDSTACC.TypeDocument, IBGLDSTACC.DateTransaction, IBGLDSTACC.DocumentNumber, IBGLDSTACC.CommentsDetail, IBGLACCNTS.AccountNumber ,IBGLACCNTS.AccountName, IBGLDSTACC.GLTMCCTYPID, IBGLDSTACC.MOVEMENTTYPE, IBGLDSTACC.AmountCompanyCurrency, IBGLDSTACC.AmountTransactCurrency FROM IBGLDSTACC INNER JOIN IBGLACCNTS ON IBGLDSTACC.IDCATALOGO = IBGLACCNTS.IDCATALOGO WHERE IBGLDSTACC.IDCATALOGO='" + IdCatalogo + "' AND IBGLDSTACC.DateTransaction BETWEEN '" + FechaDesde + "' AND '" + FechaHasta + "' ");
                        rs = pst.executeQuery();
                        if (rs.first()) {//recorre el resultset al siguiente registro si es que existen
                            rs.beforeFirst();//regresa el puntero al primer registro
                            while (rs.next()) {
                                out.println("<TR style='text-align: center;'>");
                                out.println("<TD style='color: #000000;'>" + rs.getString(1) + "</TD>"); //TIPO DE DOCUMENTO
                                out.println("<TD style='color: #000000;'>" + rs.getDate(2) + "</TD>"); //FECHA DE LA TRANSACCION
                                out.println("<TD style='color: #000000;'>" + rs.getString(3) + "</TD>"); //NUMERO DEL DOCUMENTO
                                out.println("<TD style='color: #000000;'></TD>"); //ENTIDAD
                                out.println("<TD style='color: #000000;'>" + rs.getString(4) + "</TD>"); //COMENTARIOS DETALLE
                                out.println("<TD style='color: #000000;'>" + rs.getString(5) + "-" + rs.getString(6) + "</TD>"); //NUMERO Y NOMBRE DE LA CUENTA
                                out.println("<TD colspan='2' style='color: #000000;'></TD>"); //CENTRO COSTO

                                if (rs.getString(8).equals("D")) {
                                    out.println("<TD style='color: #000000;'>" + rs.getDouble(9) + "</TD>");//MONTO DEBITO
                                    out.println("<TD style='color: #000000;'></TD>");//MONTO CREDITO
                                } else {
                                    out.println("<TD style='color: #000000;'></TD>");//MONTO DEBITO
                                    out.println("<TD style='color: #000000;'>" + rs.getDouble(9) + "</TD>");//MONTO CREDITO
                                }
                                out.println("</TR>");
                            }; // Fin while
                            conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                            pst.close(); //CIERRO EL PREPARED STATEMENT.
                            out.println("</tbody>");
                            out.println("</table > ");
                            out.println("<div class='row form-group'>"
                                    + "<div class='col-sm-3'> </div>"
                                    + "<div class='col-sm-2'> "
                                    + "<a href='DetalleTransCuenta.jsp' class='btn btn-primary'>"
                                    + "<i class='icon ion-arrow-return-left'></i> Regresar </a>"
                                    + "</div>"
                                    + "<a href='#' target='' class='btn btn-warning text-white'> "
                                    + "Imprimir a PDF <i class='icon ion-document'></i> </a>"
                                    + "</div>");

                        } else {
                            out.println("</tbody>");
                            out.println("</table > ");
                            out.println("<div class='alert alert-danger' style='text-align: center;'>"
                                    + "<h4><i class='icon ion-alert-circled'></i> Parametros de Busqeda No Coinciden Con Los Registros </h4></div>");
                            out.println("<div class='row form-group'>"
                                    + "<div class='col-sm-3'> </div>"
                                    + "<div class='col-sm-2'> "
                                    + "<a href='DetalleTransCuenta.jsp' class='btn btn-primary'>"
                                    + "<i class='icon ion-arrow-return-left'></i> Regresar </a>"
                                    + "</div>"
                                    + "</div>");
                        }

                    } //fin try no usar ; al final de dos o mas catchs 
                    catch (SQLException e) {
                    };
                    //}; 
                %>
            </tbody>
        </table>
    </body>
</html>
