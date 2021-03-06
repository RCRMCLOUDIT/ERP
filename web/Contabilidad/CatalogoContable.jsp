<%-- 
    Document   : CatalogoContable
    Created on : 08-08-2018, 01:14:34 PM
    Author     : Ing. Moises Romero Mojica
    Owner:     : Cloud IT Systems, S.A
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="beans.ConexionDB"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String DirActual = request.getContextPath();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <%--ESTILOS DEL FRAMEWORK BOOTSTRAP --%>
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=DirActual%>/css/bootstrap-select.css">
        <link rel="stylesheet" href="<%=DirActual%>/css/jquery-ui-1.12.1.css">
        <%--ESTILOS DEL FRAMEWORK IONICONS --%>
        <link rel="stylesheet" href="<%=DirActual%>/ionicons/css/ionicons.min.css">
        <%--JS DEL FRAMEWORK BOOTSTRAP Y JQUERY--%>
        <script src="<%=DirActual%>/js/jquery.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap.bundle.min.js"></script>
        <script src="<%=DirActual%>/js/bootstrap-select.js"></script>
        <script src="<%=DirActual%>/js/jquery-ui.min.js"></script>
        <script src="<%=DirActual%>/js/popper.min.js"></script>
        <script src="<%=DirActual%>/js/calendario.js"></script>
        <script src="<%=DirActual%>/js/blocker.js"></script>
        <script src="<%=DirActual%>/js/cross-browser.js"></script>        
        <script>
            //ESTA FUNCION SIRVE PARA PAGINAR LOS RESULTADOS
            $(document).ready(function () {
                $("#tblCatalogoContable").jPaginate();
            });
        </script>
        <script>
            //ESTA FUNCION SIRVE PARA FILTRAR LA BUSQUEDA POR NOMBRE
            $(document).ready(function () {
                $("#filtro").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#tblCatalogoContable tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
        <script>
            // ESTA FUNCION SIRVE PARA REDIRIGIR A LA MISMA PAGINA PERO FILTAR LA LISTA DEL CATALOGO POR TIPO DE CUENTA
            $(document).ready(function () {
                $("#form-TipoCuenta").change(function () { // AQUI CAPTURO QUE CAMBIO EL SELECT
                    var IdTipoCuenta = $('#form-TipoCuenta').val(); // CAPTURO EL VALOR QUE SE SELECCIONO
                    //alert(IdTipoCuenta);
                    $('#form-TipoCuenta').val($(this).val());
                    location.href = "CatalogoContable.jsp?IdTipoCuenta=" + IdTipoCuenta; // REGIRIGO EL JSP CON EL ID TIPO CUENTA
                });
            });
        </script>
        <script>
            // FUNCION PARA MOSTRAR O OCULAR LAS LINEAS DE LA TABLA
            $(document).ready(function () {
                $("#tblCatalogoContable tr").click(function () {
                    if ($("#tblCatalogoContable tr").is(":visible")) {
                        //document.getElementById("tblCatalogoContable").style.display = 'none';
                        var FilaId = $(this).closest('tr').attr('id');
                        //alert("Id Fila = " + FilaId);
                    }
                });
            });
        </script>
        <script type="text/javascript">
            function confirmar()
            {
                if (!confirm("¿Deseas Inactivar Esta Cuenta de Catalago?"))
                {
                    return false; //no se borra 
                } else
                {
                    //si se borra 
                    return true;
                }
            }
        </script>
        <title>Catalogo Contable</title>
    </head>
    <%@include file="../Commons/Menu.jsp" %>
    <body>
        <div id="EncabezadoPagina" style="background-color: #4682B4;">
            <center>
                <h1 style="color: #FFFFFF; text-align: center;">Catalogo Contable</h1>                
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
                                    <span class="input-group-addon">Nombre Cta:</span>
                                    <input id="filtro" name="filtro" type="text" value='' class="form-control" placeholder="Ingresa Cta a  Buscar..." required="true">
                                    <%-- <button class="btn btn-primary" id="Buscar" type="submit">Buscar</button> --%>
                                    <a href="AgregaCuentaContable.jsp" class="btn btn-success">Agregar Nueva Cta</a>
                                </div>
                            </form>
                            <div class="input-group">
                                <span class="input-group-addon">Filtar x Tipos  Cta:</span>
                                <select class="form-control" id="form-TipoCuenta" name="form-TipoCuenta" style="text-align: center">
                                    <option value='0'>Mostrar Todos</option>
                                    <% try {
                                            ConexionDB conn = new ConexionDB();
                                            conn.Conectar();
                                            String consulta = "Select GLTPCLSID,GLTPNAME From `IBGLTYPACC`";
                                            ResultSet rs = null;
                                            PreparedStatement pst = null;
                                            pst = conn.conexion.prepareStatement(consulta);
                                            rs = pst.executeQuery();
                                            while (rs.next()) {
                                                if (request.getParameter("IdTipoCuenta") != null) {
                                                    if (rs.getInt(1) == Integer.valueOf(request.getParameter("IdTipoCuenta"))) {
                                                        out.println("<option value='" + rs.getInt(1) + "' selected='true'>" + rs.getString(2) + "</option>");
                                                    } else {
                                                        out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
                                                    }
                                                } else {
                                                    out.println("<option value='" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
                                                }
                                            }; // fin while 
                                            conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                            pst.close(); //CIERRO EL PREPARED STATEMENT.
                                        } //fin try no usar ; al final de dos o mas catchs 
                                        catch (SQLException e) {
                                        };%>
                                </select>
                            </div>
                        </div>
                        <div class="panel-heading" style="background-color: #4682B4;">
                            <h3 class="panel-title" style="color: #FFFFFF; text-align: center;">Listado de Cuentas Contables</h3>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover" id="tblCatalogoContable">
                                <thead style="background-color: #4682B4">
                                    <tr>
                                        <th colspan="7" style="color: #FFFFFF; text-align: center;"><strong>Nombre Cuenta</strong></th>
                                        <th style="color: #FFFFFF; text-align: center;"><strong>Tipo</strong></th>
                                        <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong></strong></th>
                                    </tr>
                                </thead>
                                <tbody style="background-color: #C7C6C6;">
                                    <%try {
                                            ConexionDB conn = new ConexionDB();
                                            conn.Conectar();
                                            ResultSet rs = null;
                                            PreparedStatement pst = null;
                                            //pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.IDCATALOGO, ibglaccnts.AccountNumber, ibglaccnts.AccountName, ibgltypacc.GLTPNAME, ibglaccnts.AccountLevel1, ibglaccnts.AccountLevel2, ibglaccnts.AccountLevel3, ibglaccnts.AccountLevel4, ibglaccnts.AccountLevel5, ibglaccnts.AccountLevel6 FROM ibglaccnts INNER JOIN ibgltypacc ON ibglaccnts.GLTPCLSID=ibgltypacc.GLTPCLSID WHERE ibglaccnts.Active ='S' ORDER BY ibglaccnts.AccountLevel1, ibglaccnts.AccountLevel2, ibglaccnts.AccountLevel3, ibglaccnts.AccountLevel4, ibglaccnts.AccountLevel5, ibglaccnts.AccountLevel6, ibglaccnts.AccountNumber AND ibglaccnts.AccountName");
                                            if (request.getParameter("IdTipoCuenta") != null && Integer.parseInt(request.getParameter("IdTipoCuenta")) > 0) {
                                                pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.IDCATALOGO, IBGLACCNTS.AccountNumber, IBGLACCNTS.AccountName, IBGLTYPACC.GLTPNAME, IBGLACCNTS.AccountLevel1, IBGLACCNTS.AccountLevel2, IBGLACCNTS.AccountLevel3, IBGLACCNTS.AccountLevel4, IBGLACCNTS.AccountLevel5, IBGLACCNTS.AccountLevel6 FROM `IBGLACCNTS` INNER JOIN `IBGLTYPACC` ON IBGLACCNTS.GLTPCLSID=IBGLTYPACC.GLTPCLSID WHERE IBGLACCNTS.Active ='S' AND IBGLACCNTS.GLTPCLSID=" + Integer.parseInt(request.getParameter("IdTipoCuenta")) + " ORDER BY IBGLACCNTS.AccountNumber ASC");
                                            } else {
                                                pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.IDCATALOGO, IBGLACCNTS.AccountNumber, IBGLACCNTS.AccountName, IBGLTYPACC.GLTPNAME, IBGLACCNTS.AccountLevel1, IBGLACCNTS.AccountLevel2, IBGLACCNTS.AccountLevel3, IBGLACCNTS.AccountLevel4, IBGLACCNTS.AccountLevel5, IBGLACCNTS.AccountLevel6 FROM `IBGLACCNTS` INNER JOIN `IBGLTYPACC` ON IBGLACCNTS.GLTPCLSID=IBGLTYPACC.GLTPCLSID WHERE IBGLACCNTS.Active ='S' ORDER BY IBGLACCNTS.AccountNumber ASC");
                                            }
                                            rs = pst.executeQuery();
                                            rs = pst.executeQuery();
                                            while (rs.next()) {
                                                // MANDO A VERIFICAR LOS NIVELES
                                                //INDEX 5 = ACCOUNT LEVEL1; INDEX 6 = ACCOUNT LEVEL2; INDEX 7 = ACCOUNT LEVEL3
                                                //INDEX 8 = ACCOUNT LEVEL4; INDEX 9 = ACCOUNT LEVEL5; INDEX 10 = ACCOUNT LEVEL6
                                                if (Integer.valueOf(rs.getString(5)) > 0 && Integer.valueOf(rs.getString(6)) == 0 && Integer.valueOf(rs.getString(7)) == 0) {
                                                    out.println("<TR id='" + Integer.valueOf(rs.getString(5)) + "-" + Integer.valueOf(rs.getString(6)) + "-" + Integer.valueOf(rs.getString(7)) + "-" + Integer.valueOf(rs.getString(8)) + "-" + Integer.valueOf(rs.getString(9)) + "-" + Integer.valueOf(rs.getString(10)) + "' style='text-align: center;'>");
                                                    //SI ES NIVEL 1 IRA EN LA PRIMERA CELDA
                                                    out.println("<TD colspan='7' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    //FORMULARIO OCULTO PARA MANDAR A INACTIVAR LA CUENTA O ELIMINAR SI NO HA HABIDO NINGUN MOVIMIENTO
                                                    out.println("<TD>"
                                                            + "<form id='DeleteDetallePoliza' role='form' action='#' method='POST' onsubmit='return confirmar();'>"
                                                            + "<input id='Accion' name='Accion' type='text' value='DeleteCtaContable' hidden='true'>"
                                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarCta' name='btnEliminarCta'>Inactivar</button>"
                                                            + "</form>"
                                                            + "</TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(6)) > 0 && Integer.valueOf(rs.getString(7)) == 0 && Integer.valueOf(rs.getString(8)) == 0) {
                                                    //SI ES NIVEL 2 IRA EN LA SEGUNDA CELDA
                                                    out.println("<TR id='" + Integer.valueOf(rs.getString(5)) + "-" + Integer.valueOf(rs.getString(6)) + "-" + Integer.valueOf(rs.getString(7)) + "-" + Integer.valueOf(rs.getString(8)) + "-" + Integer.valueOf(rs.getString(9)) + "-" + Integer.valueOf(rs.getString(10)) + "' style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='6' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    //FORMULARIO OCULTO PARA MANDAR A INACTIVAR LA CUENTA O ELIMINAR SI NO HA HABIDO NINGUN MOVIMIENTO
                                                    out.println("<TD>"
                                                            + "<form id='DeleteDetallePoliza' role='form' action='#' method='POST' onsubmit='return confirmar();'>"
                                                            + "<input id='Accion' name='Accion' type='text' value='DeleteCtaContable' hidden='true'>"
                                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarCta' name='btnEliminarCta'>Inactivar</button>"
                                                            + "</form>"
                                                            + "</TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(7)) > 0 && Integer.valueOf(rs.getString(8)) == 0 && Integer.valueOf(rs.getString(9)) == 0) {
                                                    //SI ES NIVEL 3 IRA EN LA TERCERA CELDA
                                                    //out.println("<TR id='" + Integer.valueOf(rs.getString(5)) + "-" + Integer.valueOf(rs.getString(6)) + "-" + Integer.valueOf(rs.getString(7)) + "-" + Integer.valueOf(rs.getString(8)) + "-" + Integer.valueOf(rs.getString(9)) + "-" + Integer.valueOf(rs.getString(10)) + "' style='text-align: center;' hidden='true'>");
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='5' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    //FORMULARIO OCULTO PARA MANDAR A INACTIVAR LA CUENTA O ELIMINAR SI NO HA HABIDO NINGUN MOVIMIENTO
                                                    out.println("<TD>"
                                                            + "<form id='DeleteDetallePoliza' role='form' action='#' method='POST' onsubmit='return confirmar();'>"
                                                            + "<input id='Accion' name='Accion' type='text' value='DeleteCtaContable' hidden='true'>"
                                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarCta' name='btnEliminarCta'>Inactivar</button>"
                                                            + "</form>"
                                                            + "</TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(8)) > 0 && Integer.valueOf(rs.getString(9)) == 0 && Integer.valueOf(rs.getString(10)) == 0) {
                                                    //SI ES NIVEL 4 IRA EN LA CUARTA CELDA
                                                    //out.println("<TR id='" + Integer.valueOf(rs.getString(5)) + "-" + Integer.valueOf(rs.getString(6)) + "-" + Integer.valueOf(rs.getString(7)) + "-" + Integer.valueOf(rs.getString(8)) + "-" + Integer.valueOf(rs.getString(9)) + "-" + Integer.valueOf(rs.getString(10)) + "' style='text-align: center;' hidden='true'>");
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='4' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    //FORMULARIO OCULTO PARA MANDAR A INACTIVAR LA CUENTA O ELIMINAR SI NO HA HABIDO NINGUN MOVIMIENTO
                                                    out.println("<TD>"
                                                            + "<form id='DeleteDetallePoliza' role='form' action='#' method='POST' onsubmit='return confirmar();'>"
                                                            + "<input id='Accion' name='Accion' type='text' value='DeleteCtaContable' hidden='true'>"
                                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarCta' name='btnEliminarCta'>Inactivar</button>"
                                                            + "</form>"
                                                            + "</TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(9)) > 0 && Integer.valueOf(rs.getString(10)) == 0) {
                                                    //SI ES NIVEL 5 IRA EN LA QUINTA CELDA
                                                    //out.println("<TR id='" + Integer.valueOf(rs.getString(5)) + "-" + Integer.valueOf(rs.getString(6)) + "-" + Integer.valueOf(rs.getString(7)) + "-" + Integer.valueOf(rs.getString(8)) + "-" + Integer.valueOf(rs.getString(9)) + "-" + Integer.valueOf(rs.getString(10)) + "' style='text-align: center;' hidden='true'>");
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='3' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    //FORMULARIO OCULTO PARA MANDAR A INACTIVAR LA CUENTA O ELIMINAR SI NO HA HABIDO NINGUN MOVIMIENTO
                                                    out.println("<TD>"
                                                            + "<form id='DeleteDetallePoliza' role='form' action='#' method='POST' onsubmit='return confirmar();'>"
                                                            + "<input id='Accion' name='Accion' type='text' value='DeleteCtaContable' hidden='true'>"
                                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarCta' name='btnEliminarCta'>Inactivar</button>"
                                                            + "</form>"
                                                            + "</TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(10)) > 0) {
                                                    //SI ES NIVEL 6 IRA EN LA SEXTA CELDA
                                                    //out.println("<TR id='" + Integer.valueOf(rs.getString(5)) + "-" + Integer.valueOf(rs.getString(6)) + "-" + Integer.valueOf(rs.getString(7)) + "-" + Integer.valueOf(rs.getString(8)) + "-" + Integer.valueOf(rs.getString(9)) + "-" + Integer.valueOf(rs.getString(10)) + "' style='text-align: center;' hidden='true'>");
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='2' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    //FORMULARIO OCULTO PARA MANDAR A INACTIVAR LA CUENTA O ELIMINAR SI NO HA HABIDO NINGUN MOVIMIENTO
                                                    out.println("<TD>"
                                                            + "<form id='DeleteDetallePoliza' role='form' action='#' method='POST' onsubmit='return confirmar();'>"
                                                            + "<input id='Accion' name='Accion' type='text' value='DeleteCtaContable' hidden='true'>"
                                                            + "<input id='form-IdCatalogo' name='form-IdCatalogo' type='text' value='" + rs.getInt(1) + "' hidden='true'>"
                                                            + "<button type='submit' class='btn btn-danger' id='btnEliminarCta' name='btnEliminarCta'>Inactivar</button>"
                                                            + "</form>"
                                                            + "</TD>");
                                                    out.println("</TR>");
                                                }
                                            }; // fin while 
                                            conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
                                            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
                                            pst.close(); //CIERRO EL PREPARED STATEMENT.
                                        } //fin try no usar ; al final de dos o mas catchs 
                                        catch (SQLException e) {
                                        };%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <%-- 
            <ul class="pagination pagination-lg justify-content-center" style="margin:20px">
                <li class="page-item"><a class="page-link" href="#">Anterior</a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">Siguiente</a></li>
            </ul>        
            --%>
        </section>
    </body>
</html>