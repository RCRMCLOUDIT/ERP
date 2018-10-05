<%-- 
    Document   : AgregarPoliza
    Created on : 06-22-2018, 01:55:54 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="controller.ServletPoliza"%>
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
        <script type="text/javascript">
            //FUNCION PARA VALIDAR EL INGRESO DE PARAMETROS
            function valida() {
                var ok = true;
                var msg = "";
                var TasaCambio = $('#form-TasaCambio').val();
                var TotalLineas = $('#form-TotalLineas').val();
                var CstTotalFOB = $('#form-CstTotalFOB').val();
                if (TasaCambio === "0") {
                    msg = "Parametro no puede ser Cero";
                    ok = false;
                    $('#form-TasaCambio').focus();
                }
                if (TotalLineas === "0") {
                    msg = "Cantidad Tiene que ser mayor a Cero";
                    ok = false;
                    $('#form-TotalLineas').focus();
                }
                if (CstTotalFOB === "0") {
                    msg = "Costo Total FOB No puede ser cero";
                    ok = false;
                    $('#form-CstTotalFOB').focus();
                }
                if (ok === false)
                    alert(msg);
                return ok;
            }
        </script>
        <title>Sistema Liquidaciones</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Registo de Parametros de la Liquidacion</h1>
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Parametros Liquidacion</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A REGISTRAR UNA POLIZA --%>
            <form id="AddPoliza" role='form' action='../ServletPoliza' method='POST' onsubmit="return valida(this)">          
                <%-- PARAMETRO A EJECUTAR EN EL SERVLET --%>
                <input id="Accion" name="Accion" type="text" class="form-control col-sm-2" value="ADDPOLIZA" hidden="true"> 
                <div id="tab-1"><%-- DIV DE PARAMETROS DE LA POLIZA --%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Poliza</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Numero Liquidacion</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Numero Pedido</strong></span>
                        <span class="input-group-addon col-sm-2"><strong># Recibo Bodega</strong></span>                        
                    </div>
                    <div class="input-group">
                        <input id="form-NumeroPoliza" name="form-NumeroPoliza" type="text" class="form-control col-sm-2" placeholder="# Numero Poliza" style="text-align: center" required="true">
                        <input id="form-NumeroLiquidacion" name="form-NumeroLiquidacion" type="number" class="form-control col-sm-2" placeholder="# Numero Liquidacion"  style="text-align: center" required="true">
                        <input id="form-NumeroPedido" name="form-NumeroPedido" type="number" class="form-control col-sm-2" placeholder="# Numero Pedido"  style="text-align: center" required="true">
                        <input id="form-RecBod" name="form-RecBod" type="number" class="form-control col-sm-2" placeholder="# Numero Recibo"  style="text-align: center" required="true">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Nombre del Proveedor</strong></span>
                        <input id="form-Proveedor" name="form-Proveedor" type="text" class="form-control col-sm-6" placeholder="Ingrese Nombre Proveedor"  style="text-align: center" required="true">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Pais de Origen</strong></span>
                        <input id="form-PaisOrigen" name="form-PaisOrigen" type="text" class="form-control col-sm-6" placeholder="Ingrese Pais Origen"  style="text-align: center" required="true">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Fecha Poliza</strong></span>
                        <input type="text" class="form-control col-sm-2" id="FechaPoliza" name="FechaPoliza" style="text-align: center" readonly="true">
                        <span class="input-group-addon col-sm-2"><strong>T/C</strong></span>
                        <input id="form-TasaCambio" name="form-TasaCambio" type="number" step="any" class="form-control col-sm-2" placeholder="Tasa Cambio"  style="text-align: center" required="true">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Total Lineas</strong></span>
                        <input id="form-TotalLineas" name="form-TotalLineas" type="number" class="form-control col-sm-2" value="0" min="0" style="text-align: center" required="true">
                        <span class="input-group-addon col-sm-2"><strong>Costo Total FOB</strong></span>
                        <input id="form-CstTotalFOB" name="form-CstTotalFOB" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center" required="true">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-6"><strong>Gastos Totales CIF U$</strong></span>
                        <span class="input-group-addon col-sm-6"><strong>Gastos Aduana C$</strong></span>
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
                        <input id="form-CstTotaSeguro" name="form-CstTotaSeguro" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center">
                        <input id="form-CstTotaFlete" name="form-CstTotaFlete" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center">
                        <input id="form-CstTotalOtrosCIF" name="form-CstTotalOtrosCIF" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center">
                        <input id="form-CstTotaDAI" name="form-CstTotaDAI" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center">
                        <input id="form-CstTotaISC" name="form-CstTotaISC" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center">
                        <input id="form-CstTotaOtrosImp" name="form-CstTotaOtrosImp" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center">                                                    
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-3"><strong>Total de Otros Gastos Importacion</strong></span>
                        <input id="form-TotalOtrosGastosImportacion" name="form-TotalOtrosGastosImportacion" type="number" step="any" class="form-control col-sm-3" value="0.00" min="0" style="text-align: center">
                    </div>
                </div><%--FIN DIV DE PARAMETROS DE POLIZA --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-3"> 
                        <button type='button' onclick='location.href = "BuscarPolizas.jsp"' class='btn btn-primary'><<< Volver Busqueda</button>        
                    </div>
                    <button type="submit" class="btn btn-success" id="btnAgregar" name="btnAgregar" >Guardar & Continuar</button>
                </div>
            </form> <%--FIN FORMULARIO PARA INGRESAR PARAMETROS DE LA POLIZA--%>
        </div><%--FIN DIV GRUPO DE TABS--%>
        <script type="text/javascript">
            $('#FechaPoliza').datepicker({
                //dateFormat: "dd/mm/yy",
                dateFormat: "yy-mm-dd",
                language: "es",
                changeYear: true,
                changeMonth: true,
                yearRange: "2018:2050",
                autoclose: true
            }).datepicker("setDate", new Date());
        </script>
    </body>
</html>