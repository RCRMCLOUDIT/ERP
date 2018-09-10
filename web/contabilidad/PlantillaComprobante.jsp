<%-- 
    Document   : PlantillaComprobante
    Created on : 08-29-2018, 10:03:58 AM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.ConexionDB"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.SQLException"%>
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
            //ESTA FUNCION SIRVE PARA FILTRAR LA BUSQUEDA POR NOMBRE
            $(document).ready(function () {
                $("#filtro").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#tblTemplateGL tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
        <script type="text/javascript">
            function confirmar()
            {
                if (!confirm("¿Esta Seguro que Desea Borrar la Plantilla?"))
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
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Plantillas de Comprobante Contable</h1>                
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
                                    <span class="input-group-addon">Nombre Plantilla:</span>
                                    <input id="filtro" name="filtro" type="text" value='' class="form-control" placeholder="Ingrese Dato para Filtrar..." required="true">
                                    <%-- <button class="btn btn-primary" id="Buscar" type="submit">Buscar</button> --%>
                                    <a href="AddPlantillaContable.jsp" class="btn btn-success">Agregar Nueva Plantilla</a>
                                </div>
                            </form>
                        </div>
                        <div class="panel-heading" style="background-color: #4682B4;">
                            <h3 class="panel-title" style="color: #FFFFFF; text-align: center;">Listado de Plantillas Contables</h3>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover" id="tblTemplateGL">
                                <thead style="background-color: #4682B4">
                                    <tr>
                                        <th colspan="3" style="color: #FFFFFF; text-align: center;"><strong>Nombre Plantilla</strong></th>
                                        <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Acciones</strong></th>
                                    </tr>
                                </thead>
                                <tbody style="background-color: #C7C6C6;">
                                    <%try {
                                            ConexionDB conn = new ConexionDB();
                                            conn.Conectar();
                                            ResultSet rs = null;
                                            PreparedStatement pst = null;
                                            pst = conn.conexion.prepareStatement("SELECT GLTMID, GLTMMEMO FROM `IBGLTPDST` GROUP BY GLTMID");
                                            rs = pst.executeQuery();
                                            rs = pst.executeQuery();
                                            while (rs.next()) {
                                                out.println("<TR style='text-align: center;'>");
                                                out.println("<TD style='color: #000000; text-align: center;' colspan='3'>" + rs.getString(2) + "</TD>");//DESCRIPCION DE LA PLANTILLA
                                                out.println("<TD>"
                                                        + "<a class='btn btn-primary' href='AddPlantillaContable.jsp?IdPlantilla=" + rs.getInt(1) + "&DescPlant=" + rs.getString(2) + "'>Editar</a>"
                                                        + "</TD>");
                                                out.println("<TD>"
                                                        + "<form id='DeletePlantilla' role='form' action='../ServletContabilidad' method='POST' onsubmit='return confirmar();'>"
                                                        + "<input id='form-Accion' name='form-Accion' type='text' value='DeletePlantilla' hidden='true'>"
                                                        + "<input id='form-IdPlantilla' name='form-IdPlantilla' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                        + "<button type='submit' class='btn btn-danger' id='btnEliminarPlantilla' name='btnEliminarPlantilla'>Eliminar</button>"
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
