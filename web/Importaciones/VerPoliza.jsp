<%-- 
    Document   : VerPoliza
    Created on : 07-11-2018, 06:52:26 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="dao.DaoPolizas"%>
<%@page import="controller.ServletPoliza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int IdPoliza = Integer.parseInt(request.getParameter("IdPoliza"));
    DaoPolizas datos = new DaoPolizas();
    datos.BuscarPoliza(IdPoliza);
    int NumeroLiquidacion, NumeroPedido, RecBod;
    String NumeroPoliza, NombreProveedor, FechaPoliza, PaisOrigen;
    double TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion;

    NumeroPoliza = datos.NumeroPoliza;
    NumeroLiquidacion = datos.NumeroLiquidacion;
    NumeroPedido = datos.NumeroPedido;
    RecBod = datos.RecBod;
    NombreProveedor = datos.NombreProveedor;
    FechaPoliza = datos.FechaPoliza;
    PaisOrigen = datos.PaisOrigen;
    TasaCambio = datos.TasaCambio;
    TotalFOB = datos.TotalFOB;
    TotalSeguro = datos.TotalSeguro;
    TotalFlete = datos.TotalFlete;
    TotalOtrosCIF = datos.TotalOtrosCIF;
    TotalDAI = datos.TotalDAI;
    TotalISC = datos.TotalISC;
    TotalOtrosImp = datos.TotalOtrosImp;
    TotalOtrosGastosImportacion = datos.TotalOtrosGastosImportacion;
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
        <title>Sistema Liquidaciones</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Detalles de la Liquidacion</h1>
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Liquidacion</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A REGISTRAR UNA POLIZA --%>
            <form id="UpdatePoliza" role='form' action='ServletPoliza' method='POST'>
                <%-- PARAMETRO A EJECUTAR EN EL SERVLET --%>
                <input id="Accion" name="Accion" type="text" class="form-control col-sm-2" value="UPDATEPOLIZA" hidden="true">
                <input id="form-IdPoliza" name="form-IdPoliza" type="text" class="form-control col-sm-2" value="<%=IdPoliza%>" hidden="true">
                <div id="tab-1"><%-- DIV DE PARAMETROS DE LA POLIZA --%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Poliza</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Numero Liquidacion</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Numero Pedido</strong></span>
                        <span class="input-group-addon col-sm-2"><strong># Recibo Bodega</strong></span>                        
                    </div>
                    <div class="input-group">
                        <input id="form-NumeroPoliza" name="form-NumeroPoliza" type="text" class="form-control col-sm-2" placeholder="# Numero Poliza" style="text-align: center" required="true" value="<%=NumeroPoliza%>">
                        <input id="form-NumeroLiquidacion" name="form-NumeroLiquidacion" type="number" class="form-control col-sm-2" placeholder="# Numero Liquidacion"  style="text-align: center" required="true" value="<%=NumeroLiquidacion%>">
                        <input id="form-NumeroPedido" name="form-NumeroPedido" type="number" class="form-control col-sm-2" placeholder="# Numero Pedido"  style="text-align: center" value="<%=NumeroPedido%>">
                        <input id="form-RecBod" name="form-RecBod" type="number" class="form-control col-sm-2" placeholder="# Numero Recibo"  style="text-align: center" value="<%=RecBod%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre del Proveedor</strong></span>
                        <input id="form-Proveedor" name="form-Proveedor" type="text" class="form-control col-sm-6" placeholder="Ingrese Nombre Proveedor"  style="text-align: center" value="<%=NombreProveedor%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Pais de Origen</strong></span>
                        <input id="form-PaisOrigen" name="form-PaisOrigen" type="text" class="form-control col-sm-6" placeholder="Ingrese Pais Origen"  style="text-align: center" value="<%=PaisOrigen%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Fecha Poliza</strong></span>
                        <input type="text" class="form-control col-sm-2" id="FechaPoliza" name="FechaPoliza" style="text-align: center" readonly="true" value="<%=FechaPoliza%>">
                        <span class="input-group-addon col-sm-2"><strong>T/C</strong></span>
                        <input id="form-TasaCambio" name="form-TasaCambio" type="number" step="any" class="form-control col-sm-2" placeholder="Tasa Cambio"  style="text-align: center" value="<%=TasaCambio%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Costo Total FOB</strong></span>
                        <input id="form-CstTotalFOB" name="form-CstTotalFOB" type="number" step="any" class="form-control col-sm-4" style="text-align: center" value="<%=TotalFOB%>">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-6"><strong>Gastos Totales CIF</strong></span>
                        <span class="input-group-addon col-sm-6"><strong>Gastos Aduana</strong></span>
                    </div>
                    <div class="input-group">   
                        <span class="input-group-addon col-sm-2"><strong>Gasto Seguro</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Gasto Flete</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Otros Gastos CIF</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>DAI</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>ISC</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Otros Impuestos</strong></span>
                    </div>
                    <div class="input-group">
                        <input id="form-CstTotaSeguro" name="form-CstTotaSeguro" type="number" step="any" class="form-control col-sm-2" style="text-align: center" value="<%=TotalSeguro%>">
                        <input id="form-CstTotaFlete" name="form-CstTotaFlete" type="number" step="any" class="form-control col-sm-2" style="text-align: center" value="<%=TotalFlete%>">
                        <input id="form-CstTotalOtrosCIF" name="form-CstTotalOtrosCIF" type="number" step="any" class="form-control col-sm-2" style="text-align: center" value="<%=TotalOtrosCIF%>">
                        <input id="form-CstTotaDAI" name="form-CstTotaDAI" type="number" step="any" class="form-control col-sm-2" style="text-align: center" value="<%=TotalDAI%>">
                        <input id="form-CstTotaISC" name="form-CstTotaISC" type="number" step="any" class="form-control col-sm-2" style="text-align: center" value="<%=TotalISC%>">
                        <input id="form-CstTotaOtrosImp" name="form-CstTotaOtrosImp" type="number" step="any" class="form-control col-sm-2" style="text-align: center" value="<%=TotalOtrosImp%>">                                 
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-3"><strong>Total de Otros Gastos Importacion</strong></span>
                        <input id="form-TotalOtrosGastosImportacion" name="form-TotalOtrosGastosImportacion" type="number" step="any" class="form-control col-sm-4" style="text-align: center" value="<%=TotalOtrosGastosImportacion%>">
                    </div>
                </div><%--FIN DIV DE PARAMETROS DE POLIZA --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-3"> 
                        <button type='button' onclick='location.href = "BuscarPolizas.jsp"' class='btn btn-primary'><<< Volver Busqueda</button>        
                    </div>
                    <button type="submit" class="btn btn-primary" id="btnAgregar" name="btnAgregar" >Guardar & Continuar</button>
                </div>
            </form> <%--FIN FORMULARIO PARA INGRESAR PARAMETROS DE LA POLIZA--%>
        </div><%--FIN DIV GRUPO DE TABS--%>
        <script type="text/javascript">
            $('#FechaPoliza').datepicker({
                dateFormat: "dd/mm/yy",
                //dateFormat: "dd MM, yy",
                language: "es",
                changeYear: true,
                changeMonth: true,
                yearRange: "2018:2050",
                autoclose: true
            }).datepicker("setDate", new Date());
        </script>
    </body>
</html>
