<%-- 
    Document   : TiposCuenta
    Created on : 08-07-2018, 04:37:18 PM
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
        <script>
            //ESTA FUNCION SIRVE PARA FILTRAR LA BUSQUEDA POR NOMBRE
            $(document).ready(function () {
                $("#filtro").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#tblTipoCuenta tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
        <title>Tipo de Cuentas</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Tipos De Cuentas Contables</h1>                
            </center>
        </div>
        <section id="CatalogoContable" class="container">
            <div class="row" id="CaalogoContable">
                <div  class="col-xs-2"></div>
                <div  class="col-md-8">
                    <div class="panel-default">
                        <div id="BuscaCuenta">
                            <form id="FormBuscarCta" role="form" action="#">
                                <div class="input-group">
                                    <span class="input-group-addon">Nombre:</span>
                                    <input id="filtro" name="filtro" type="text" value='' class="form-control" placeholder="Ingresa Cta a  Buscar..." required="true">
                                    <button class="btn btn-primary" id="Buscar" type="submit">Buscar</button>
                                    <a href="AgregarTipoCuenta.jsp" class="btn btn-success">Agregar Nuevo Tipo</a>
                                </div>
                            </form>
                        </div>
                        <div class="panel-heading" style="background-color: #4682B4;">
                            <h3 class="panel-title" style="color: #FFFFFF; text-align: center;">Listado de Tipos de Cuentas</h3>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover" id="tblTipoCuenta">
                                <thead style="background-color: #4682B4">
                                    <tr>
                                        <th style="color: #FFFFFF; text-align: right;"><strong>Codigo de Tipo Ctas</strong></th>
                                        <th style="color: #FFFFFF; text-align: left;"><strong>Nombre de Tipo Ctas</strong></th>
                                        <th style="color: #FFFFFF; text-align: center;"><strong>Clase</strong></th>
                                        <th style="color: #FFFFFF; text-align: center;"><strong></strong></th>
                                    </tr>
                                </thead>
                                <tbody style="background-color: #C7C6C6;">
                                    <%try {
                                            ConexionDB conn = new ConexionDB();
                                            conn.Conectar();
                                            ResultSet rs = null;
                                            PreparedStatement pst = null;
                                            pst = conn.conexion.prepareStatement("SELECT * FROM `IBGLTYPACC`");
                                            rs = pst.executeQuery();
                                            rs = pst.executeQuery();
                                            while (rs.next()) {
                                                out.println("<TR style='text-align: center;'>");
                                                out.println("<TD style='color: #000000; text-align: right;'>" + rs.getInt(2) + "</TD>");//CODIGO DEL TIPO DE CTA
                                                out.println("<TD style='color: #000000; text-align: left;'>" + rs.getString(3) + "</TD>");//NOMBRE DEL TIPO
                                                out.println("<TD style='color: #000000;'>" + rs.getString(4) + "</TD>");//CLASE
                                                out.println("<TD>"
                                                        + "<a class='btn btn-primary' href='ModificarTipoCuenta.jsp?GLTPCLSID=" + rs.getInt(2) + "'>Editar</a>"
                                                        + "</TD>");
                                                out.println("</TR>");
                                            }; // fin while 
                                        } //fin try no usar ; al final de dos o mas catchs 
                                        catch (SQLException e) {
                                        };%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
