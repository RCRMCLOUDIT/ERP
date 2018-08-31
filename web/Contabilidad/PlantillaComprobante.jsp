<%-- 
    Document   : PlantillaComprobante
    Created on : 08-29-2018, 10:03:58 AM
    Author     : Ing. Moises Romero Mojica
--%>
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
                            <table class="table table-hover" id="tblCatalogoContable">
                                <thead style="background-color: #4682B4">
                                    <tr>
                                        <th colspan="3" style="color: #FFFFFF; text-align: center;"><strong>Nombre Plantilla</strong></th>
                                        <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Acciones</strong></th>
                                    </tr>
                                </thead>
                                <tbody style="background-color: #C7C6C6;">
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
