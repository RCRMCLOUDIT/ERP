<%-- 
    Document   : AddPlantillaContable
    Created on : 08-29-2018, 01:23:34 PM
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
        <script>
            // To style only selects with the selectpicker class
            $('.selectpicker').selectpicker();
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
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos de Plantilla</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A GUARDAR LOS DATOS DE LA CUENTA CONTABLE --%>
            <form id="PlantillaComprobante" role='form' action='../ServletContabilidad' method='POST'>
                <div id="tab-1"><%-- DIV PARA EL CONTROL DE LOS DATOS DE LA CUENTA CONTABLE --%>                  
                    <%--PARAMETRO PARA LA ACCION A EJECUTAR EN EL SERVLET--%>
                    <input type="text" class="form-control" id="form-Accion" name="form-Accion" value="AddPlantillaComprobante" hidden="true">
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre Plantilla</strong></span>
                        <input id="form-NombrePlantilla" name="form-NombrePlantilla" type="text" class="form-control col-sm-6" placeholder="Ingresa Nombre..." required="true" style="text-align: center" maxlength="80">
                    </div>                                 
                </div><%--FIN DIV PARA EL CONTROL DE DATOS DE LA CUENTA CONTABLE --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type='button' onclick='location.href = "PlantillaComprobante.jsp"' class='btn btn-primary'>Volver</button>
                    </div>
                    <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Guardar</button>
                </div>
                
            </form><%--FIN FORMULARIO PARA EL ENVIO DE DATOS DE LA CUENTA CONTABLE --%>
            <div class="panel-body">
                <table class="table table-hover" id="tblPlantillaContable">
                    <thead style="background-color: #4682B4">
                        <tr>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Cuenta Contable</strong></th>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Monto</strong></th>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Descripcion</strong></th>
                            <th style="color: #FFFFFF; text-align: center;"><strong>Tipo</strong></th>
                            <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Centro de Costo</strong></th>
                        </tr>
                    </thead>
                    <tbody style="background-color: #C7C6C6;">
                        <tr>
                            <td>
                                <select class="form-control" id="form-CtaContable" name="form-CtaContable" style="text-align: center">
                                    <option value='0'>Mostrar Todos</option>
                                    <option value='0'>Cuenta 1</option>
                                    <option value='0'>Cuenta 2</option>
                                    <option value='0'>Cuenta 3</option>
                                </select>                            
                            </td>
                            <td>
                                <input id="Monto" name="Monto" step="any" type="number" class="form-control" required="true" min="0">
                            </td>
                            <td>
                                <input id="Descripcion" name="Descripcion" type="text" class="form-control" min="0">
                            </td>
                            <td>
                                <select class="form-control" id="form-TipoMov" name="form-TipoMov" style="text-align: center">
                                    <option value='0'>Debito</option>
                                    <option value='0'>Credito</option>
                                </select>                            
                            </td>                            
                            <td>
                                <select class="form-control" id="form-TipoCC" name="form-TipoCC" style="text-align: center">
                                    <option value='0'>Division</option>
                                    <option value='0'>Empleado</option>
                                    <option value='0'>Proveedor</option>
                                    <option value='0'>Cliente</option>
                                </select>                            
                            </td>                            
                            <td>
                                <select class="form-control" id="form-CtaCC" name="form-CtaCC" style="text-align: center">
                                    <option value='0'>Mostrar Todos</option>
                                    <option value='0'>Cuenta 1</option>
                                    <option value='0'>Cuenta 2</option>
                                    <option value='0'>Cuenta 3</option>
                                </select>                            
                            </td>                            
                        </tr>
                    </tbody>
                </table>
            </div>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </body>
</html>
