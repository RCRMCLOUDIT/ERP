<%-- 
    Document   : ListarPolizas
    Created on : 06-25-2018, 03:32:19 PM
    Author     : Ing. Moises Romero Mojica
--%>

<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String NumeroPoliza = request.getParameter("form-NumeroPoliza");
    int NumeroLiquidacion = Integer.valueOf(request.getParameter("form-NumeroLiquidacion"));
    String FechaDesde = request.getParameter("FechaDesde");
    String FechaHasta = request.getParameter("FechaHasta");
    String Check = request.getParameter("BuscarFecha");
    String consulta = "";

    if (NumeroPoliza.length() > 0) {
        consulta = "SELECT IdPoliza, NumeroPoliza, NumeroLiquidacion, FechaPoliza, NombreProveedor, PaisOrigen, TotalFOB, Estado  FROM  `IBPOLIZA` WHERE NumeroPoliza LIKE'" + NumeroPoliza + "%'";
    }
    if (NumeroLiquidacion > 0) {
        consulta = "SELECT IdPoliza, NumeroPoliza, NumeroLiquidacion, FechaPoliza, NombreProveedor, PaisOrigen, TotalFOB, Estado  FROM  `IBPOLIZA` WHERE NumeroLiquidacion = '" + NumeroLiquidacion + "' ";
    }
    if (Check != null) {
        consulta = "SELECT IdPoliza, NumeroPoliza, NumeroLiquidacion, FechaPoliza, NombreProveedor, PaisOrigen, TotalFOB, Estado  FROM  `IBPOLIZA`  WHERE FechaPoliza BETWEEN '" + FechaDesde + "' AND '" + FechaHasta + "' ORDER BY FechaPoliza DESC";
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
        <title>Resultados Busqueda</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Resultados de la Busqueda</h1>              
            </center>
        </div>
        <table class="table table-hover" id="tblMarca">
            <thead style="background-color: #4682B4">
                <tr>
                    <th style="color: #FFFFFF; text-align: center;"># Poliza</th>
                    <th style="color: #FFFFFF; text-align: center;"># Liquidacion</th>
                    <th style="color: #FFFFFF; text-align: center;">Fecha</th>
                    <th style="color: #FFFFFF; text-align: center;">Proveedor</th>
                    <th style="color: #FFFFFF; text-align: center;">Proviene de</th>
                    <th style="color: #FFFFFF; text-align: center;">Total FOB</th>
                    <th style="color: #FFFFFF; text-align: center;">Estado</th>
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
                        pst = conn.conexion.prepareStatement(consulta);
                        rs = pst.executeQuery();
                        if (rs.first()) {//recorre el resultset al siguiente registro si es que existen
                            rs.beforeFirst();//regresa el puntero al primer registro
                            while (rs.next()) {
                                out.println("<TR style='text-align: center;'>");
                                out.println("<TD style='color: #000000;'>" + rs.getString(2) + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getInt(3) + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getDate(4) + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getString(5) + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getString(6) + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble(7) + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getString(8) + "</TD>");
                                if (rs.getString(8).equals("Abierto")) {
                                    out.println("<TD>"
                                            + "<a href='EditarPoliza.jsp?IdPoliza=" + rs.getInt(1) + "' class='btn btn-primary'>Editar</a>"
                                            + "</TD>");
                                } else {
                                    out.println("<TD>"
                                            + "<a href='ReportePoliza.jsp?IdPoliza=" + rs.getInt(1) + "' target='_blank' class='btn btn-warning'>Ver o Imprimir</a>"
                                            + "</TD>");
                                }

                                out.println("</TR>");
                            }; // fin while
                        } else {
                            out.println("</tbody>");
                            out.println("</table > ");
                            out.println("<div class='alert alert-danger' style='text-align: center;'>"
                                    + "<h4>!Parametros de Busqeda No Coinciden Con Los Registros¡</h4></div>");
                        }

                    } //fin try no usar ; al final de dos o mas catchs 
                    catch (SQLException e) {
                    };
                    //}; 
                %>
            </tbody>
        </table>
        <div class="row form-group">
            <div class="col-sm-3"> </div>
            <div class="col-sm-2"> 
                <button type='button' onclick='location.href = "BuscarPolizas.jsp"' class='btn btn-primary'><<< Volver Busqueda</button>        
            </div>
            <button type='button' onclick='location.href = "AgregarPoliza.jsp"' class='btn btn-success'>Agregar Nueva Poliza</button>
        </div>            
    </body>
</html>
