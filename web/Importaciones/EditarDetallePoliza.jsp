<%-- 
    Document   : EditarDetallePoliza
    Created on : 06-26-2018, 03:09:19 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="beans.ConexionDB"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="model.DaoPolizas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int IdDetalle = Integer.parseInt(request.getParameter("IdDetalle"));
    int IdPoliza = Integer.parseInt(request.getParameter("IdPoliza"));

    DaoPolizas datos = new DaoPolizas();
    datos.BuscarPoliza(IdPoliza);
    int NumeroLiquidacion, NumeroPedido, RecBod, TotalLineas, LineasGuardadas;
    String NumeroPoliza, NombreProveedor, FechaPoliza, PaisOrigen;
    double TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp,
            TotalOtrosGastosImportacion;
    //RECUPERO LOS TOTALES DE LOS PARAMETROS INGRESADOS
    NumeroPoliza = datos.NumeroPoliza;
    NumeroLiquidacion = datos.NumeroLiquidacion;
    NumeroPedido = datos.NumeroPedido;
    FechaPoliza = datos.FechaPoliza;
    TasaCambio = datos.TasaCambio;
    TotalFOB = datos.TotalFOB;
    TotalSeguro = datos.TotalSeguro;
    TotalFlete = datos.TotalFlete;
    TotalOtrosCIF = datos.TotalOtrosCIF;
    TotalDAI = datos.TotalDAI;
    TotalISC = datos.TotalISC;
    TotalOtrosImp = datos.TotalOtrosImp;
    TotalOtrosGastosImportacion = datos.TotalOtrosGastosImportacion;
    TotalLineas = datos.TotalLineas;
    datos.ObtenerTotalItems();
    LineasGuardadas = datos.TotalLineas;

    //CALCULO PARA FACTOR DE LOS GASTOS CIF
    double FactorSeguro = (TotalSeguro / TotalFOB);
    double FactorFlete = (TotalFlete / TotalFOB);
    double FactorOtrosCIF = (TotalOtrosCIF / TotalFOB);
    //CALCULO PARA FACTOR DE LOS GASTOS DE ADUANA
    double TotalCIFC$ = (TotalFOB + TotalSeguro + TotalFlete + TotalOtrosCIF) * TasaCambio;
    double FactorDAI = (TotalDAI / TotalCIFC$);
    double FactorISC = (TotalISC / TotalFOB);
    double FactorOtrosImpuestos = (TotalOtrosImp / TotalFOB);
    //CALCULO PARA FACTOR DE OTROS GASTOS DE IMPORTACION
    double FactorOtrosGastosImportacion = (TotalOtrosGastosImportacion / TotalFOB);

    DecimalFormat formato = new DecimalFormat("#.0000");

    //RECUPERO EL DETALLE DEL ITEM A EDITAR
    datos.BuscardDetalle(IdDetalle, IdPoliza);
    double Cantidad, Precio;
    String CodProducto, Descripcion;
    CodProducto = datos.CodProducto;
    Descripcion = datos.Descripcion;
    Cantidad = datos.Cantidad;
    Precio = datos.Precio;
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
                <h1 style="color: #FFFFFF; text-align: center;">Detalle de Articulos</h1>
            </center>
        </div>
        <div id="grupoTablas"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <ul  style="background-color: #4682B4;">
                <li><a href="#tab-1">Detallado por Item</a></li>
            </ul>
            <%-- FORMULARIO PARA MANDAR A REGISTRAR UNA POLIZA --%>
            <form id="UpdateDetallePoliza" role='form' action='../ServletPoliza' method='POST'>
                <input id="Accion" name="Accion" type="text" class="form-control col-sm-2" value="UpdateDetallePoliza" hidden="true">
                <input id="form-IdPoliza" name="form-IdPoliza" type="text" class="form-control col-sm-2" value="<%=IdPoliza%>" hidden="true">
                <input id="form-IdDetalle" name="form-IdDetalle" type="text" class="form-control col-sm-2" value="<%=IdDetalle%>" hidden="true">
                <div id="tab-1"><%-- DIV PARA DETALLES DE ITEMS DE LA POLIZA --%>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Numero Poliza</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Numero Liquidacion</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Numero Pedido</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Fecha Poliza</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>T/C</strong></span>
                    </div>
                    <div class="input-group">
                        <input id="form-NumeroPoliza" name="form-NumeroPoliza" type="text" class="form-control col-sm-2" placeholder="# Numero Poliza" style="text-align: center" required="true" readonly="true" value="<%=NumeroPoliza%>">
                        <input id="form-NumeroLiquidacion" name="form-NumeroLiquidacion" type="number" class="form-control col-sm-2" placeholder="# Numero Liquidacion"  style="text-align: center" required="true" readonly="true" value="<%=NumeroLiquidacion%>">
                        <input id="form-NumeroPedido" name="form-NumeroPedido" type="number" class="form-control col-sm-2" placeholder="# Numero Pedido"  style="text-align: center" readonly="true" value="<%=NumeroPedido%>">
                        <input id="FechaPoliza" name="FechaPoliza" type="text" class="form-control col-sm-2"  style="text-align: center" readonly="true" value="<%=FechaPoliza%>">
                        <input id="form-TasaCambio" name="form-TasaCambio" type="number" step="any" class="form-control col-sm-2" placeholder="Tasa Cambio"  style="text-align: center" readonly="true" value="<%=TasaCambio%>">
                    </div>
                    <%-- DIV PARA PRECARGAR LOS PARAMETROS Y PODER LLAMARLOS DESDE LA FUNCION --%>
                    <div class="input-group" hidden="true">
                        <input id="form-FactorSeguro" name="form-FactorSeguro" type="text" class="form-control col-sm-1"value="<%=formato.format(FactorSeguro)%>">
                        <input id="form-FactorFlete" name="form-FactorFlete" type="text" class="form-control col-sm-1"  value="<%=formato.format(FactorFlete)%>">
                        <input id="form-FactorOtrosCIF" name="form-FactorOtrosCIF" type="text" class="form-control col-sm-1"  value="<%=formato.format(FactorOtrosCIF)%>">
                        <input id="form-FactorDAI" name="form-FactorDAI" type="text" class="form-control col-sm-1"  value="<%=formato.format(FactorDAI)%>">
                        <input id="form-FactorISC" name="form-FactorISC" type="text" class="form-control col-sm-1" value="<%=formato.format(FactorISC)%>">
                        <input id="form-FactorOtrosImpuestos" name="form-FactorOtrosImpuestos" type="text"  class="form-control col-sm-1"  value="<%=formato.format(FactorOtrosImpuestos)%>">
                        <input id="form-FactorOtrosGastosImportacion" name="form-FactorOtrosGastosImportacion" type="text"  class="form-control col-sm-1" value="<%=formato.format(FactorOtrosGastosImportacion)%>">
                        <input id="form-TotalLineas" name="form-TotalLineas" type="number" class="form-control col-sm-2" style="text-align: center" value="<%=TotalLineas%>">
                        <input id="form-LineasGuardadas" name="form-LineasGuardadas" type="number" class="form-control col-sm-2" style="text-align: center" value="<%=LineasGuardadas%>">
                    </div>
                    <%-- FINAL DIV PARA PRECARGAR LOS PARAMETROS Y PODER LLAMARLOS DESDE LA FUNCION --%>
                    <hr>
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Codigo Producto</strong></span>
                        <span class="input-group-addon col-sm-4"><strong>Nombre / Descripcion</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Cantidad</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Precio U$</strong></span>
                    </div>
                    <div class="input-group">
                        <input id="form-CodProducto" name="form-CodProducto" type="text" class="form-control col-sm-2" placeholder="Codigo Producto" style="text-align: center" required="true" value="<%=CodProducto%>">
                        <input id="form-NombreProd" name="form-NombreProd" type="text" class="form-control col-sm-4" placeholder="Nombre Producto" style="text-align: center" required="true" value="<%=Descripcion%>">
                        <input id="form-Cantidad" name="form-Cantidad" type="number" class="form-control col-sm-2" min="1" style="text-align: center" required="true" value="<%=Cantidad%>">
                        <input id="form-Precio" name="form-Precio" type="number" step="any" class="form-control col-sm-2" min="0" style="text-align: center" required="true" value="<%=Precio%>">
                        <button type='submit' id="btnAgregarDetalle" name="btnAgregarDetalle" class='btn btn-success'>Guardar Detalle</button>
                    </div>
                </div><%--FIN DIV DE DETALLES DE ITEMS DE LA  POLIZA --%>
        </div><%--FIN DIV GRUPO DE TABS--%>
    </form> <%--FIN FORMULARIO PARA INGRESAR DETALLES DE LA POLIZA--%>
    <br>
    <div class="row form-group">
        <div class="col-sm-3"> </div>
        <a href="AgregarDetallePoliza.jsp?IdPoliza=<%=IdPoliza%>" target="_blank" class="btn btn-primary"> <<< Volver</a>
    </div>
</body>
</html>