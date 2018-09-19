<%-- 
    Document   : BuscarComprobante
    Created on : 08-29-2018, 10:04:25 AM
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
        <script>
            $(function () {
                $("#grupoTablas").tabs();
            });
        </script>
        <script type="text/javascript">
            //FUNCION PARA VALIDAR EL INGRESO DE PARAMETROS
            function valida() {
                var ok = true;
                var msg = "Ingrese un Parametro al menos Ó Seleccione Busqueda Por Fecha";
                var NumeroReferencia = $('#form-NumeroReferencia').val();
                var FechaDesde = $("#FechaDesde").datepicker("getDate");
                var FechaHasta = $("#FechaHasta").datepicker("getDate");
                var Check = document.getElementById('BuscarFecha').checked;
                if (NumeroReferencia.length === 0 && Check === false)
                {
                    msg;
                    ok = false;
                }

                if (Check === true && NumeroReferencia.length === 0) {
                    if (FechaDesde > FechaHasta) {
                        msg = "Fecha Inicial no puede ser mayor a Fecha Final.";
                        var FechaDesde = $('#FechaDesde').focus();
                        ok = false;
                    }
                }

                if (ok === false)
                    alert(msg);
                return ok;
            }
        </script>
        <title>Buscar Comprobante</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Buscar Comprobante</h1>
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Datos Comprobante</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A BUSCAR UN COMPROBANTE --%>
            <form id="BuscaComprobante" role='form' action='ListarComprobante.jsp' method='POST' onsubmit="return valida(this)">
                <div id="tab-1"><%-- DIV DE PARAMETROS PARA LA BUSQUEDA DE COMPROBANTE --%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Referencia</strong></span>
                        <input id="form-NumeroReferencia" name="form-NumeroReferencia" type="text" class="form-control col-sm-6" placeholder="Ingresa Numero Referencia" style="text-align: center">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Rango Fecha</strong>
                            <input type="checkbox" id="BuscarFecha" name="BuscarFecha" onclick="Validar();">
                        </span>
                        <span class="input-group-addon col-sm-1"><strong>Desde</strong></span>
                        <input type="text" class="form-control col-sm-2" id="FechaDesde" name="FechaDesde" style="text-align: center" readonly="true">
                        <span class="input-group-addon col-sm-1"><strong>Hasta</strong></span>
                        <input type="text" class="form-control col-sm-2" id="FechaHasta" name="FechaHasta" style="text-align: center" readonly="true">
                    </div>                   
                </div><%--FIN DIV DE PARAMETROS PARA LA BUSQUEDA DE COMPROBANTE --%>
                <br>
                <div class="row form-group">
                    <div class="col-sm-3"> </div>
                    <div class="col-sm-2"> 
                        <button type="submit" class="btn btn-primary" id="btnBuscar" name="btnBuscar" >Ir a Buscar Comprobante</button>
                    </div>
                    <button type='button' onclick='location.href = "SeleccionPlantilla.jsp"' class='btn btn-success'>Agregar Nuevo Comprobante</button>
                </div>
            </form> <%--FIN FORMULARIO PARA MANDAR A BUSCAR UN COMPROBANTE--%>
        </div><%--FIN DIV GRUPO DE TABS--%>       
        <script type="text/javascript">
            $('#FechaDesde, #FechaHasta').datepicker({
                //format: "dd/mm/yyyy",
                dateFormat: "yy-mm-dd",
                language: 'es',
                changeYear: true,
                changeMonth: true,
                yearRange: "2018:2050",
                showAnim: "slide",
                autoclose: true
            }).datepicker({}).datepicker("setDate", new Date());
        </script>
    </body>
</html>
