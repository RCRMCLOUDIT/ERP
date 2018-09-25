<%-- 
    Document   : ReportePoliza
    Created on : 06-26-2018, 12:33:00 PM
    Author     : Ing. Moises Romero Mojica
--%>
<%@page import="beans.ConexionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.*"%> 
<%@page import="java.io.*"%> 
<%@page import="java.sql.*"%> 
<html>
    <body>
        <%
            int IdPoliza = Integer.valueOf(request.getParameter("IdPoliza"));
            /*Parametros para realizar la conexion*/
            ConexionDB conn = new ConexionDB();
            /*Establecemos la ruta del reporte*/
            File reportFile = new File(application.getRealPath("/Importaciones/ReportePoliza.jasper"));
            //System.out.println("Url: " + reportFile.getPath());
            String Url = reportFile.getPath();
            /*Recogemos los parametros que enviaremos al reporte*/
            Map parameters = new HashMap();
            parameters.put("IdPoliza", IdPoliza);
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
