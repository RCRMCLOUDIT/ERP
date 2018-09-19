<%-- 
    Document   : ListarComprobante
    Created on : 09-11-2018, 01:52:24 PM
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
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/bootstrap.css">
        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/calendario.js"></script>
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>
        <script type="text/javascript">
            function confirmar()
            {
                if (!confirm("¿Desea Eliminar Este Comprobante?"))
                {
                    return false; //no se borra 
                } else
                {
                    //si se borra 
                    return true;
                }
            }
        </script>
        <title>Listado Comprobante</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Resultados de Busqueda</h1>
            </center>
        </div>
        <section id="Comprobante" class="container">
            <div class="row" id="Comprobante">
                <div  class="col-xs-2"></div>
                <div  class="col-md-12">
                    <div class="panel-default">
                        <div id="BuscaCuenta">
                            <form id="FormBuscaComprobante" role="form" action="#">
                                <div class="input-group">
                                    <span class="input-group-addon">Nombre Plantilla:</span>
                                    <input id="filtro" name="filtro" type="text" value='' class="form-control" placeholder="Ingrese Dato para Filtrar..." required="true">
                                    <a href="SeleccionPlantilla.jsp" class="btn btn-success">Agregar Nuevo Comprobante</a>
                                </div>
                            </form>
                        </div>
                        <div class="panel-heading" style="background-color: #4682B4;">
                            <h3 class="panel-title" style="color: #FFFFFF; text-align: center;">Listado de Comprobantes Contables</h3>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover" id="tblComprobantes">
                                <thead style="background-color: #4682B4">
                                    <tr>
                                        <th colspan="1" style="color: #FFFFFF; text-align: center;"><strong>Fecha</strong></th>
                                        <th colspan="1" style="color: #FFFFFF; text-align: center;"><strong>Numero de Referencia</strong></th>
                                        <th colspan="3" style="color: #FFFFFF; text-align: center;"><strong>Descripcion</strong></th>
                                        <th colspan="1" style="color: #FFFFFF; text-align: center;"><strong>Estado</strong></th>
                                        <th colspan="1" style="color: #FFFFFF; text-align: center;"><strong>Error</strong></th>
                                        <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Acciones</strong></th>
                                    </tr>
                                </thead>
                                <tbody style="background-color: #C7C6C6;">
                                    <%try {
                                            ConexionDB conn = new ConexionDB();
                                            conn.Conectar();
                                            ResultSet rs = null;
                                            PreparedStatement pst = null;
                                            pst = conn.conexion.prepareStatement("SELECT `IdComprobante`, `GLBACHDATE`, `GLBACHMREF`, `GLBACHMEMO`, `GLBACHSTATUS` FROM `IBGLBATCHDST` GROUP BY IdComprobante ORDER BY `GLBACHDATE` DESC");
                                            rs = pst.executeQuery();
                                            rs = pst.executeQuery();
                                            while (rs.next()) {
                                                out.println("<TR style='text-align: center;'>");
                                                out.println("<TD style='color: #000000; text-align: center;' colspan='1'>" + rs.getString(2) + "</TD>");//FECHA DEL COMPROBANTE
                                                out.println("<TD style='color: #000000; text-align: center;' colspan='1'>" + rs.getString(3) + "</TD>");//# REF DEL COMPROBANTE
                                                out.println("<TD style='color: #000000; text-align: center;' colspan='3'>" + rs.getString(4) + "</TD>");//DESCRIPCION DEL COMPROBANTE
                                                out.println("<TD style='color: #000000; text-align: center;' colspan='1'>" + rs.getString(5) + "</TD>");//ESTADO DEL COMPROBANTE
                                                out.println("<TD style='color: #000000; text-align: center;' colspan='1'></TD>");//SI HAY ERROR
                                                out.println("<TD>"
                                                        + "<a class='btn btn-primary' href='AgregarComprobante.jsp?IdComprobante=" + rs.getInt(1) + "&DescComp=" + rs.getString(4) + "&RefNum=" + rs.getString(3) + "&Fecha=" + rs.getString(2) + " '>Editar</a>"
                                                        + "</TD>"
                                                );
                                                out.println("<TD>"
                                                        + "<form id='DeleteBATCH' role='form' action='../ServletContabilidad' method='POST' onsubmit='return confirmar();'>"
                                                        + "<input id='form-Accion' name='form-Accion' type='text' value='DeleteBATCH' hidden='true'>"
                                                        + "<input id='form-IdComprobante' name='form-IdComprobante' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                        + "<button type='submit' class='btn btn-danger' id='btnEliminarBATCH' name='btnEliminarBATCH'>Eliminar</button>"
                                                        + "</form>"
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
