<%-- 
    Document   : ReporteComprobante
    Created on : 09-19-2018, 12:35:32 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="beans.ConexionDB"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.*"%> 
<%@page import="java.io.*"%> 
<%@page import="java.sql.*"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Comprobante</title>
    </head>
    <body>
        <%
            int IdComprobante = Integer.valueOf(request.getParameter("IdComprobante"));
            String NombreEmpresa = "MUNKEL MEDICAL";
            /*Parametros para realizar la conexion*/
            ConexionDB conn = new ConexionDB();
            /*Establecemos la ruta del reporte*/
            File reportFile = new File(application.getRealPath("/Contabilidad/ReporteComprobante.jasper"));
            //System.out.println("Url: " + reportFile.getPath());
            String Url = reportFile.getPath();
            /*Recogemos los parametros que enviaremos al reporte*/
            Map parameters = new HashMap();
            parameters.put("IdComprobante", IdComprobante);
            parameters.put("NombreEmpresa", NombreEmpresa);
            /*Enviamos la ruta del reporte, los parámetros y la conexion(objeto Connection)*/
            //System.out.println(Url);
            byte[] bytes = JasperRunManager.runReportToPdf(Url, parameters, conn.Conectar());
            /*Indicamos que la respuesta va a ser en formato PDF*/
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            /*Limpiamos y cerramos flujos de salida*/
            ouputStream.flush();
            ouputStream.close();
        %>
    </body>
</html>
