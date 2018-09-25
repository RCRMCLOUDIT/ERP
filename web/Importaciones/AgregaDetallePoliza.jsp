<%-- 
    Document   : AgregaDetallePoliza
    Created on : 06-22-2018, 08:16:46 PM
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
    int IdPoliza = Integer.parseInt(request.getParameter("IdPoliza"));
    DaoPolizas datos = new DaoPolizas();
    datos.BuscarPoliza(IdPoliza);
    int NumeroLiquidacion, NumeroPedido, RecBod, TotalLineas, LineasGuardadas;
    String NumeroPoliza, NombreProveedor, FechaPoliza, PaisOrigen;
    double TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp,
            TotalOtrosGastosImportacion;

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
    datos.ObtenerTotalItems(IdPoliza);
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
        <script type="text/javascript">
            // FUNCION PARA ELIMINAR LINEA
            $(document).ready(function () {
                $('#btnEliminarDetalle').click(function () {

                });
            });
        </script>
        <script type="text/javascript">
            function confirmar()
            {
                if (!confirm("¿Deseas eliminar este registro?"))
                {
                    return false; //no se borra 
                } else
                {
                    //si se borra 
                    return true;
                }
            }
        </script>
        <script type="text/javascript">
            //FUNCION PARA VALIDAR EL INGRESO DE PARAMETROS
            function valida() {
                var ok = true;
                var msg = "";
                var TotalLineas = $('#form-TotalLineas').val();
                var LineasGuardadas = $('#form-LineasGuardadas').val();
                if (TotalLineas === LineasGuardadas) {
                    msg = "Para agregar mas lineas debe modifacarlo el dato Parametros de Poliza";
                    ok = false;
                }
                if (ok === false)
                    alert(msg);
                return ok;
            }
        </script>
        <script>
            $(function () {
                $('#selectpicker').selectpicker();
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
            <%-- FORMULARIO PARA MANDAR A REGISTRAR DETALLES DE UNA POLIZA --%>
            <form id="UpdateDetallePoliza" role='form' action='../ServletPoliza' method='POST' onsubmit="return valida(this)">
                <input id="Accion" name="Accion" type="text" class="form-control col-sm-2" value="AddDetallePoliza" hidden="true">
                <input id="form-IdPoliza" name="form-IdPoliza" type="text" class="form-control col-sm-2" value="<%=IdPoliza%>" hidden="true">
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
                        <input id="form-CodProducto" name="form-CodProducto" type="text" class="form-control col-sm-2" placeholder="Codigo Producto" style="text-align: center" required="true">
                        <input id="form-NombreProd" name="form-NombreProd" type="text" class="form-control col-sm-4" placeholder="Nombre Producto" style="text-align: center" required="true">
                        <input id="form-Cantidad" name="form-Cantidad" type="number" class="form-control col-sm-2" value="0" min="1" style="text-align: center" required="true">
                        <input id="form-Precio" name="form-Precio" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center" required="true">
                        <button type='submit' id="btnAgregarDetalle" name="btnAgregarDetalle" class='btn btn-success'>Agregar Item</button>
                    </div>
                </div><%--FIN DIV DE DETALLES DE ITEMS DE LA  POLIZA --%>
            </form> <%--FIN FORMULARIO PARA INGRESAR DETALLES DE LA POLIZA--%>
        </div><%--FIN DIV GRUPO DE TABS--%>
        <br>
        <div class="table-responsive-lg">
            <%--TABLA QUE SERVIRAR PARA LISTAR EL REGISTRO DETALLES AGREGADOS Y PODER INTERACTUAR YA SE PARA MODIFICARLOS O ELIMINAR --%>
            <table class="table table-responsive-lg table-hover" id="tblDetallePoliza">
                <thead style="background-color: #4682B4">
                    <tr>
                        <th style="color: #FFFFFF; text-align: center;">Id</th>
                        <th style="color: #FFFFFF; text-align: center;">Codigo</th>
                        <th style="color: #FFFFFF; text-align: center;">Descripcion</th>
                        <th style="color: #FFFFFF; text-align: center;">Cantidad</th>
                        <th style="color: #FFFFFF; text-align: center;">Precio U$</th>
                        <th style="color: #FFFFFF; text-align: center;">Valor U$</th>
                        <th style="color: #FFFFFF; text-align: center;">Seguro</th>
                        <th style="color: #FFFFFF; text-align: center;">Flete</th>
                        <th style="color: #FFFFFF; text-align: center;">Otros</th>
                        <th style="color: #FFFFFF; text-align: center;">Total CIF $</th>
                        <th style="color: #FFFFFF; text-align: center;">Total CIF C$</th>
                        <th style="color: #FFFFFF; text-align: center;">DAI</th>
                        <th style="color: #FFFFFF; text-align: center;">ISC</th>
                        <th style="color: #FFFFFF; text-align: center;">Otros Impuesto</th>
                        <th style="color: #FFFFFF; text-align: center;">Total Impuestos Aduana</th>
                        <th style="color: #FFFFFF; text-align: center;">Otros Gastos Importacion</th>
                        <th style="color: #FFFFFF; text-align: center;">Costo Total C$</th>
                        <th style="color: #FFFFFF; text-align: center;">Costo Unitario C$</th>
                        <th style="color: #FFFFFF; text-align: center;">Costo Unitario U$</th>
                    </tr>
                </thead>
                <tbody style="background-color: #C7C6C6;" >
                    <% // declarando y creando objetos globales 
                        //Integer cod = DaoLogin.IdUsuario;
                        // construyendo forma dinamica 
                        // mandando el sql a la base de datos 
                        try {
                            ConexionDB conn = new ConexionDB();
                            conn.Conectar();
                            String consulta = "SELECT IdDetalle, IdPoliza, CodigoProducto, Descripcion, Cantidad, Precio, "
                                    + "ValorUS,	Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC, DAI, ISC, OtrosImpuesto,"
                                    + "TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS"
                                    + " FROM `IBDETAILPOLIZA` WHERE IdPoliza='" + IdPoliza + "' ";
                            ResultSet rs = null;
                            PreparedStatement pst = null;
                            pst = conn.conexion.prepareStatement(consulta);
                            rs = pst.executeQuery();
                            int id = 1;
                            double TotalCantidad = 0.00, TotalValor = 0.00, Seguro = 0.00, Flete = 0.00, OtrosCIF = 0.00,
                                    TotalCIFUS = 0.00, TotalCIFC = 0.00, DAI = 0.00, ISC = 0.00, OtrosIMP = 0.00, TotalAduana = 0.00,
                                    OtrosGtsImp = 0.00, CstTotal = 0.00;
                            while (rs.next()) {
                                out.println("<TR style='text-align: center;'>");
                                out.println("<TD style='color: #000000;'>" + id + "</TD>");
                                out.println("<TD style='color: #000000;'>" + rs.getString("CodigoProducto") + "</TD>"); //CODIGO
                                out.println("<TD style='color: #000000;'>" + rs.getString("Descripcion") + "</TD>"); //DESCRIPCION / NOMBRE
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("Cantidad") + "</TD>"); //CANTIDAD
                                TotalCantidad = TotalCantidad + rs.getDouble("Cantidad");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("Precio") + "</TD>"); //PRECIO
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("ValorUS") + "</TD>"); //VALOR
                                TotalValor = TotalValor + rs.getDouble("ValorUS");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("Seguro") + "</TD>"); //SEGURO
                                Seguro = Seguro + rs.getDouble("Seguro");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("Flete") + "</TD>"); //FLETE
                                Flete = Flete + rs.getDouble("Flete");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("OtrosCIF") + "</TD>"); //OTROS CIF
                                OtrosCIF = OtrosCIF + rs.getDouble("OtrosCIF");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("TotalCIFUS") + "</TD>"); //TOTAL CIF U$
                                TotalCIFUS = TotalCIFUS + rs.getDouble("TotalCIFUS");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("TotalCIFC") + "</TD>"); //TOTAL CIF C$
                                TotalCIFC = TotalCIFC + rs.getDouble("TotalCIFC");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("DAI") + "</TD>"); //DAI
                                DAI = DAI + rs.getDouble("DAI");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("ISC") + "</TD>"); //ISC
                                ISC = ISC + rs.getDouble("ISC");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("OtrosImpuesto") + "</TD>"); //OtrosImpuesto
                                OtrosIMP = OtrosIMP + rs.getDouble("OtrosImpuesto");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("TotalImpuestosAduana") + "</TD>"); //TotalImpuestosAduana
                                TotalAduana = TotalAduana + rs.getDouble("TotalImpuestosAduana");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("OtrosGastosImportacion") + "</TD>"); //OtrosGastosImportacion
                                OtrosGtsImp = OtrosGtsImp + rs.getDouble("OtrosGastosImportacion");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("CostoTotal") + "</TD>"); //CostoTotal
                                CstTotal = CstTotal + rs.getDouble("CostoTotal");
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("CostoUnitarioC") + "</TD>"); //CostoUnitarioC
                                out.println("<TD style='color: #000000;'>" + rs.getDouble("CostoUnitarioUS") + "</TD>"); //CostoUnitarioUS
                                out.println("<TD>"
                                        + "<a href='EditarDetallePoliza.jsp?IdDetalle=" + rs.getInt(1) + "&IdPoliza=" + rs.getInt(2) + "' class='btn btn-primary'>Editar</a>"
                                        + "</TD>");
                                out.println("<TD>"
                                        + "<form id='DeleteDetallePoliza' role='form' action='ServletPoliza' method='POST' onsubmit='return confirmar();'>"
                                        + "<input id='Accion' name='Accion' type='text' class='form-control col-sm-2' value='DeleteDetallePoliza' hidden='true'>"
                                        + "<input id='form-IdDetalle' name='form-IdDetalle' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                        + "<input id='form-IdPoliza' name='form-IdPoliza' type='text' value='" + rs.getInt(2) + "' hidden='true'>"
                                        + "<button type='submit' class='btn btn-danger' id='btnEliminarDetalle' name='btnEliminarDetalle'>Eliminar</button>"
                                        + "</form>"
                                        + "</TD>");
                                out.println("</TR>");
                                id = id + 1;
                            }; // fin while 
                            out.println("<TR style='text-align: center;'>");
                            out.println("<TD style='color: #000000;'><strong>TOTALES</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>LINEAS: " + (id - 1) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong></strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + TotalCantidad + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong></strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>U$" + formato.format(TotalValor) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(Seguro) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(Flete) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(OtrosCIF) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>U$" + formato.format(TotalCIFUS) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>C$" + formato.format(TotalCIFC) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(DAI) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(ISC) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(OtrosIMP) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(TotalAduana) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>" + formato.format(OtrosGtsImp) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong>C$" + formato.format(CstTotal) + "</strong></TD>");
                            out.println("<TD style='color: #000000;'><strong></strong></TD>");
                            out.println("<TD style='color: #000000;'><strong></strong></TD>");
                            out.println("</TR>");
                        } //fin try no usar ; al final de dos o mas catchs 
                        catch (SQLException e) {

                        };
                        //}; 
%>
                </tbody>
            </table><%--FIN DE TABLA DE DETALLES DE ITEMS --%>
        </div>
        <div class="col-auto"><%-- DIV PARA AGRUPAR LOS DATOS POR TABS --%>
            <%-- FORMULARIO PARA MANDAR A REGISTRAR EL DETALLE UNA POLIZA --%>
            <form id="UpdateDetallePoliza" role='form' action='../ServletPoliza' method='POST' onsubmit="return valida(this)">
                <input id="Accion" name="Accion" type="text" class="form-control col-sm-2" value="AddDetallePoliza" hidden="true" >
                <input id="form-IdPoliza" name="form-IdPoliza" type="text" class="form-control col-sm-2" value="<%=IdPoliza%>" hidden="true">
                <div id="tab-1"><%-- DIV PARA DETALLES DE ITEMS DE LA POLIZA --%>
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
                    <div class="input-group">
                        <span class="input-group-addon col-sm-2"><strong>Codigo Producto</strong></span>
                        <span class="input-group-addon col-sm-4"><strong>Nombre / Descripcion</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Cantidad</strong></span>
                        <span class="input-group-addon col-sm-2"><strong>Precio U$</strong></span>
                    </div>
                    <div class="input-group">
                        <input id="form-CodProducto" name="form-CodProducto" type="text" class="form-control col-sm-2" placeholder="Codigo Producto" style="text-align: center" required="true">
                        <input id="form-NombreProd" name="form-NombreProd" type="text" class="form-control col-sm-4" placeholder="Nombre Producto" style="text-align: center" required="true">
                        <input id="form-Cantidad" name="form-Cantidad" type="number" class="form-control col-sm-2" value="0" min="1" style="text-align: center" required="true">
                        <input id="form-Precio" name="form-Precio" type="number" step="any" class="form-control col-sm-2" value="0.00" min="0" style="text-align: center" required="true">
                        <button type='submit' id="btnAgregarDetalle" name="btnAgregarDetalle" class='btn btn-success'>Agregar Item</button>
                    </div>
                    <hr>
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
                </div><%--FIN DIV DE DETALLES DE ITEMS DE LA  POLIZA --%>
            </form> <%--FIN FORMULARIO PARA INGRESAR DETALLES DE LA POLIZA--%>
        </div><%--FIN DIV GRUPO DE TABS--%>
        <br>
        <div class="row form-group">
            <div class="col-2"></div>
            <div class="col-2"> 
                <button type='button' onclick='location.href = "BuscarPolizas.jsp"' class='btn btn-primary'><<< Volver Busqueda</button>
            </div>
            <div class="col-2">
                <a href="EditarPoliza.jsp?IdPoliza=<%=IdPoliza%>" class="btn btn-primary">Volver a Poliza</a>
            </div>
            <div class="col-2"> 
                <a href="ReportePoliza.jsp?IdPoliza=<%=IdPoliza%>" target="_blank" class="btn btn-warning text-white">Imprimir Poliza</a>        
            </div>
            <div class="col-2">
                <form id="UpdateEstadoPoliza" role='form' action='../ServletPoliza' method='POST'>
                    <input id="Accion" name="Accion" type="text" class="form-control col-sm-2" value="UpdateEstadoPoliza" hidden="true">
                    <input id="form-IdPoliza" name="form-IdPoliza" type="text" class="form-control col-sm-2" value="<%=IdPoliza%>" hidden="true">
                    <button type='submit' id="btnCerrarPoliza" name="btnCerrarPoliza" class='btn btn-danger'>Cerrar Poliza</button>
                </form>
            </div>
        </div>
    </body>
</html>
