<%-- 
    Document   : CatalogoContableXFiltro
    Created on : 08-23-2018, 02:19:40 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="beans.ConexionDB"%>
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
                    $("#tblCatalogoContable tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
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
                                    <button class="btn btn-primary" id="Buscar" type="submit">Buscar</button>
                                    <a href="AgregaCuentaContable.jsp" class="btn btn-success">Agregar Nueva Cta</a>
                                </div>
                            </form>
                        </div>
                        <div class="panel-heading" style="background-color: #4682B4;">
                            <h3 class="panel-title" style="color: #FFFFFF; text-align: center;">Listado de Cuentas Contables</h3>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover" id="tblCatalogoContable">
                                <thead style="background-color: #4682B4">
                                    <tr>
                                        <th colspan="7" style="color: #FFFFFF; text-align: center;"><strong>Nombre Cuenta</strong></th>
                                        <th colspan="2" style="color: #FFFFFF; text-align: center;"><strong>Tipo</strong></th>
                                    </tr>
                                </thead>
                                <tbody style="background-color: #C7C6C6;">
                                    <%try {
                                            ConexionDB conn = new ConexionDB();
                                            conn.Conectar();
                                            ResultSet rs = null;
                                            PreparedStatement pst = null;
                                            //pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.IDCATALOGO, ibglaccnts.AccountNumber, ibglaccnts.AccountName, ibgltypacc.GLTPNAME, ibglaccnts.AccountLevel1, ibglaccnts.AccountLevel2, ibglaccnts.AccountLevel3, ibglaccnts.AccountLevel4, ibglaccnts.AccountLevel5, ibglaccnts.AccountLevel6 FROM ibglaccnts INNER JOIN ibgltypacc ON ibglaccnts.GLTPCLSID=ibgltypacc.GLTPCLSID WHERE ibglaccnts.Active ='S' ORDER BY ibglaccnts.AccountLevel1, ibglaccnts.AccountLevel2, ibglaccnts.AccountLevel3, ibglaccnts.AccountLevel4, ibglaccnts.AccountLevel5, ibglaccnts.AccountLevel6, ibglaccnts.AccountNumber AND ibglaccnts.AccountName");
                                            pst = conn.conexion.prepareStatement("SELECT IBGLACCNTS.IDCATALOGO, IBGLACCNTS.AccountNumber, IBGLACCNTS.AccountName, IBGLTYPACC.GLTPNAME, IBGLACCNTS.AccountLevel1, IBGLACCNTS.AccountLevel2, IBGLACCNTS.AccountLevel3, IBGLACCNTS.AccountLevel4, IBGLACCNTS.AccountLevel5, IBGLACCNTS.AccountLevel6 FROM `IBGLACCNTS` INNER JOIN `IBGLTYPACC` ON IBGLACCNTS.GLTPCLSID=IBGLTYPACC.GLTPCLSID WHERE IBGLACCNTS.Active ='S' ORDER BY IBGLACCNTS.AccountNumber ASC");
                                            rs = pst.executeQuery();
                                            rs = pst.executeQuery();
                                            while (rs.next()) {
                                                // MANDO A VERIFICAR LOS NIVELES
                                                //INDEX 5 = ACCOUNT LEVEL1; INDEX 6 = ACCOUNT LEVEL2; INDEX 7 = ACCOUNT LEVEL3
                                                //INDEX 8 = ACCOUNT LEVEL4; INDEX 9 = ACCOUNT LEVEL5; INDEX 10 = ACCOUNT LEVEL6
                                                if (Integer.valueOf(rs.getString(5)) > 0 && Integer.valueOf(rs.getString(6)) == 0 && Integer.valueOf(rs.getString(7)) == 0) {
                                                    out.println("<TR style='text-align: center;'>");
                                                    //SI ES NIVEL 1 IRA EN LA PRIMERA CELDA
                                                    out.println("<TD colspan='7' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(6)) > 0 && Integer.valueOf(rs.getString(7)) == 0 && Integer.valueOf(rs.getString(8)) == 0) {
                                                    //SI ES NIVEL 2 IRA EN LA SEGUNDA CELDA
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='6' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(7)) > 0 && Integer.valueOf(rs.getString(8)) == 0 && Integer.valueOf(rs.getString(9)) == 0) {
                                                    //SI ES NIVEL 3 IRA EN LA TERCERA CELDA
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='5' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(8)) > 0 && Integer.valueOf(rs.getString(9)) == 0 && Integer.valueOf(rs.getString(10)) == 0) {
                                                    //SI ES NIVEL 4 IRA EN LA CUARTA CELDA
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='4' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(9)) > 0 && Integer.valueOf(rs.getString(10)) == 0) {
                                                    //SI ES NIVEL 5 IRA EN LA QUINTA CELDA
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='3' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    out.println("</TR>");
                                                }
                                                if (Integer.valueOf(rs.getString(10)) > 0) {
                                                    //SI ES NIVEL 6 IRA EN LA SEXTA CELDA
                                                    out.println("<TR style='text-align: center;'>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD style='color: #000000;'></TD>");
                                                    out.println("<TD colspan='2' style='color: #000000; text-align: left;'>" + rs.getString(2) + " - " + rs.getString(3) + "</TD>");//NUMERO DE CUENTA Y NOMBRE
                                                    out.println("<TD style='color: #000000; text-align: center;'>" + rs.getString(4) + "</TD>");//NOMBRE DEL TIPO
                                                    out.println("<TD> <a class='btn btn-primary' href='EditarCuentaContable.jsp?IDCATALOGO=" + rs.getInt(1) + "'>Editar</a></TD>");
                                                    out.println("</TR>");
                                                }
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
