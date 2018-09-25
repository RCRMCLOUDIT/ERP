package controller;

import beans.ConexionDB;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DaoPolizas;

/**
 *
 * @author Ing. Moises Romero Mojica
 */
@WebServlet(name = "ServletPoliza", urlPatterns = {"/ServletPoliza"})
public class ServletPoliza extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL = ""; // ESTA VARIABLE SERA PARA MANEJAR LA URL
        int Msg = 0; // ESTA VARIABLE SERA PARA CONTROLAR LOS MENSAJES DE ERROR Y ENVIARLO A LA VISTA
        String Accion = request.getParameter("Accion");
        DaoPolizas datos = new DaoPolizas();

        //---------------------------------------------------------------------//
        // IF PARA AGREGAR UNA NUEVA POLIZA------------------------------------//
        //---------------------------------------------------------------------//
        if (Accion.equals("ADDPOLIZA")) {
            int CompanyId = 1;
            String NumeroPoliza = request.getParameter("form-NumeroPoliza");
            int NumeroLiquidacion = Integer.valueOf(request.getParameter("form-NumeroLiquidacion"));
            int NumeroPedido = Integer.valueOf(request.getParameter("form-NumeroPedido"));
            int RecBod = Integer.valueOf(request.getParameter("form-RecBod"));
            String NombreProveedor = request.getParameter("form-Proveedor");
            String FechaPoliza = request.getParameter("FechaPoliza");
            String PaisOrigen = request.getParameter("form-PaisOrigen");
            double TasaCambio = Double.valueOf(request.getParameter("form-TasaCambio"));
            double TotalFOB = Double.valueOf(request.getParameter("form-CstTotalFOB"));
            double TotalSeguro = Double.valueOf(request.getParameter("form-CstTotaSeguro"));
            double TotalFlete = Double.valueOf(request.getParameter("form-CstTotaFlete"));
            double TotalOtrosCIF = Double.valueOf(request.getParameter("form-CstTotalOtrosCIF"));
            double TotalDAI = Double.valueOf(request.getParameter("form-CstTotaDAI"));
            double TotalISC = Double.valueOf(request.getParameter("form-CstTotaISC"));
            double TotalOtrosImp = Double.valueOf(request.getParameter("form-CstTotaOtrosImp"));
            double TotalOtrosGastosImportacion = Double.valueOf(request.getParameter("form-TotalOtrosGastosImportacion"));
            int TotalLineas = Integer.valueOf(request.getParameter("form-TotalLineas"));
            try {
                datos.AddPoliza(CompanyId, NumeroPoliza, NumeroLiquidacion, NumeroPedido, RecBod, NombreProveedor, FechaPoliza, PaisOrigen,
                        TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF,
                        TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion, TotalLineas);
                datos.ObtenerIdPoliza();
                int IdPoliza = datos.IdPoliza;
                URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
            } catch (Exception e) {
                e.getMessage();
            }
            response.sendRedirect(URL);
        }// FINAL DEL IF PARA AGREGAR UNA NUEVA POLIZA

        //---------------------------------------------------------------------//
        // IF PARA ACTUALIZAR UNA POLIZA---------------------------------------//
        //---------------------------------------------------------------------//
        if (Accion.equals("UPDATEPOLIZA")) {
            int CompanyId = 1;
            int IdPoliza = Integer.valueOf(request.getParameter("form-IdPoliza"));
            String NumeroPoliza = request.getParameter("form-NumeroPoliza");
            int NumeroLiquidacion = Integer.valueOf(request.getParameter("form-NumeroLiquidacion"));
            int NumeroPedido = Integer.valueOf(request.getParameter("form-NumeroPedido"));
            int RecBod = Integer.valueOf(request.getParameter("form-RecBod"));
            String NombreProveedor = request.getParameter("form-Proveedor");
            String FechaPoliza = request.getParameter("FechaPoliza");
            String PaisOrigen = request.getParameter("form-PaisOrigen");
            double TasaCambio = Double.valueOf(request.getParameter("form-TasaCambio"));
            double TotalFOB = Double.valueOf(request.getParameter("form-CstTotalFOB"));
            double TotalSeguro = Double.valueOf(request.getParameter("form-CstTotaSeguro"));
            double TotalFlete = Double.valueOf(request.getParameter("form-CstTotaFlete"));
            double TotalOtrosCIF = Double.valueOf(request.getParameter("form-CstTotalOtrosCIF"));
            double TotalDAI = Double.valueOf(request.getParameter("form-CstTotaDAI"));
            double TotalISC = Double.valueOf(request.getParameter("form-CstTotaISC"));
            double TotalOtrosImp = Double.valueOf(request.getParameter("form-CstTotaOtrosImp"));
            double TotalOtrosGastosImportacion = Double.valueOf(request.getParameter("form-TotalOtrosGastosImportacion"));
            int TotalLineas = Integer.valueOf(request.getParameter("form-TotalLineas"));
            try {
                datos.UpdatePoliza(CompanyId, IdPoliza, NumeroPoliza, NumeroLiquidacion, NumeroPedido, RecBod, NombreProveedor, FechaPoliza,
                        PaisOrigen, TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp,
                        TotalOtrosGastosImportacion, TotalLineas);
                URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
                //response.sendRedirect("AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza);
            } catch (Exception e) {
                e.getMessage();
            }
            response.sendRedirect(URL);
        }//---------FIN DEL IF PARA ACTUALIZAR LOS PARAMETROS DE UNA POLIZA------//

        //---------------------------------------------------------------------//
        // IF PARA AGREGAR LOS ITEMS A LA POLIZA-------------------------------//
        //---------------------------------------------------------------------//
        if (Accion.equals("AddDetallePoliza")) {
            //FACTOR DE LOS GASTOS CIF
            Double FactorSeguro = Double.valueOf(request.getParameter("form-FactorSeguro"));
            Double FactorFlete = Double.valueOf(request.getParameter("form-FactorFlete"));
            Double FactorOtrosCIF = Double.valueOf(request.getParameter("form-FactorOtrosCIF"));
            //FACTOR DE LOS GASTOS DE ADUANA
            Double FactorDAI = Double.valueOf(request.getParameter("form-FactorDAI"));
            Double FactorISC = Double.valueOf(request.getParameter("form-FactorISC"));
            Double FactorOtrosImpuestos = Double.valueOf(request.getParameter("form-FactorOtrosImpuestos"));
            //FACTOR DE OTROS GASTOS DE IMPORTACION
            Double FactorOtrosGastosImportacion = Double.valueOf(request.getParameter("form-FactorOtrosGastosImportacion"));
            //TASA CAMBIO
            Double TasaCambio = Double.valueOf(request.getParameter("form-TasaCambio"));
            //DATOS DE LA POLIZA Y EL ITEM A AGREGAR
            int CompanyId = 1;
            int IdPoliza = Integer.valueOf(request.getParameter("form-IdPoliza"));
            String CodigoProducto = request.getParameter("form-CodProducto");
            String Descripcion = request.getParameter("form-NombreProd");
            Double Cantidad = Double.valueOf(request.getParameter("form-Cantidad"));
            Double Precio = Double.valueOf(request.getParameter("form-Precio"));
            int TotalLineas = Integer.valueOf(request.getParameter("form-TotalLineas"));
            datos.ObtenerTotalItems(IdPoliza);
            int TotalRegistro = datos.TotalLineas + 1;
            if (TotalLineas == TotalRegistro) {
                //------------CALCULANDO LIQUIDACION CUANDO ES ULTIMA LINEA------------//
                //PRIMERO MANDO A TRAER LOS TOTALES INGRESADOS EN EL ENCABEZADO DE LA POLIZA
                datos.BuscarPoliza(IdPoliza);
                double TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion;
                TotalSeguro = datos.TotalSeguro;
                TotalFlete = datos.TotalFlete;
                TotalOtrosCIF = datos.TotalOtrosCIF;
                TotalDAI = datos.TotalDAI;
                TotalISC = datos.TotalISC;
                TotalOtrosImp = datos.TotalOtrosImp;
                TotalOtrosGastosImportacion = datos.TotalOtrosGastosImportacion;
                //MANDO A BUSCAR LOS TOTALES QUE LLEVO ACUMULADO ANTES DEL LA ULTIMA LINEA.
                datos.BuscarDetallesPoliza(IdPoliza);
                double SeguroAcumulado, FleteAcumulado, OtrosCIFAcumulado, DAIAcumulado, ISCAcumulado, OtrosImpAcumulado,
                        OtrosGastosImportacionAcumulado;
                SeguroAcumulado = datos.SeguroAcumulado;
                FleteAcumulado = datos.FleteAcumulado;
                OtrosCIFAcumulado = datos.OtrosCIFAcumulado;
                DAIAcumulado = datos.DAIAcumulado;
                ISCAcumulado = datos.ISCAcumulado;
                OtrosImpAcumulado = datos.OtrosImpAcumulado;
                OtrosGastosImportacionAcumulado = datos.OtrosGastosImportacionAcumulado;
                //MANDO A REALIZAR LOS CALCULOS Y ASIGNO LAS DIFERENCIAS AL ULTIMO ARTICULO DEL ITEM
                Double Valor = (Cantidad * Precio);
                Double Seguro = (TotalSeguro - SeguroAcumulado);
                Double Flete = (TotalFlete - FleteAcumulado);
                Double OtrosCIF = (TotalOtrosCIF - OtrosCIFAcumulado);
                Double TotalCIFUS = (Valor + Seguro + Flete + OtrosCIF);
                Double TotalCIFC = ((Valor + Seguro + Flete + OtrosCIF) * TasaCambio);
                Double DAI = (TotalDAI - DAIAcumulado);
                Double ISC = (TotalISC - ISCAcumulado);
                Double OtrosImpuestos = (TotalOtrosImp - OtrosImpAcumulado);
                Double TotalImpuestosAduana = (DAI + ISC + OtrosImpuestos);
                Double OtrosGastosImportacion = (TotalOtrosGastosImportacion - OtrosGastosImportacionAcumulado);
                Double CostoTotal = (TotalCIFC + TotalImpuestosAduana + OtrosGastosImportacion);
                Double CostoUnitarioC = (CostoTotal / Cantidad);
                Double CostoUnitarioUS = (CostoUnitarioC / TasaCambio);
                try {
                    datos.AddDetallePoliza(CompanyId, IdPoliza, CodigoProducto, Descripcion, Cantidad, Precio, Valor, Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC,
                            DAI, ISC, OtrosImpuestos, TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS);
                    URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
                    //response.sendRedirect("AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza);
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                //------------CALCULANDO LIQUIDACION CUANDO NO ES ULTIMA LINEA------------// 
                Double Valor = (Cantidad * Precio);
                Double Seguro = (FactorSeguro * Valor);
                Double Flete = (FactorFlete * Valor);
                Double OtrosCIF = (FactorOtrosCIF * Valor);
                Double TotalCIFUS = (Seguro + Flete + OtrosCIF + Valor);
                Double TotalCIFC = ((Valor + Seguro + Flete + OtrosCIF) * TasaCambio);
                Double DAI = (TotalCIFC * FactorDAI);
                Double ISC = (TotalCIFC * FactorISC);
                Double OtrosImpuestos = (Valor * FactorOtrosImpuestos);
                Double TotalImpuestosAduana = (DAI + ISC + OtrosImpuestos);
                Double OtrosGastosImportacion = (FactorOtrosGastosImportacion * Valor);
                Double CostoTotal = (TotalCIFC + TotalImpuestosAduana + OtrosGastosImportacion);
                Double CostoUnitarioC = (CostoTotal / Cantidad);
                Double CostoUnitarioUS = (CostoUnitarioC / TasaCambio);
                try {
                    datos.AddDetallePoliza(CompanyId, IdPoliza, CodigoProducto, Descripcion, Cantidad, Precio, Valor, Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC,
                            DAI, ISC, OtrosImpuestos, TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS);
                    URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
                    //response.sendRedirect("AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            response.sendRedirect(URL);
        }//---------FIN DEL IF PARA AGREGAR LOS ITEMS DE UNA POLIZA------//

        //---------------------------------------------------------------------//
        // IF PARA ACTUALIZAR UN ITEM DE LA POLIZA-----------------------------//
        //---------------------------------------------------------------------//
        if (Accion.equals("UpdateDetallePoliza")) {
            int CompanyId = 1;
            //TASA CAMBIO
            Double TasaCambio = Double.valueOf(request.getParameter("form-TasaCambio"));
            //DATOS DE LA POLIZA Y TASA DE CAMBIO
            int IdPoliza = Integer.valueOf(request.getParameter("form-IdPoliza"));
            int IdDetalle = Integer.valueOf(request.getParameter("form-IdDetalle"));
            String CodigoProducto = request.getParameter("form-CodProducto");
            String Descripcion = request.getParameter("form-NombreProd");
            Double Cantidad = Double.valueOf(request.getParameter("form-Cantidad"));
            Double Precio = Double.valueOf(request.getParameter("form-Precio"));
            int TotalLineas = Integer.valueOf(request.getParameter("form-TotalLineas"));
            datos.ObtenerTotalItems(IdPoliza);
            int TotalRegistro = datos.TotalLineas;
            if (TotalLineas == TotalRegistro) {
                //------------CALCULANDO LIQUIDACION CUANDO ES ULTIMA LINEA------------//
                //PRIMERO MANDO A TRAER LOS TOTALES INGRESADOS EN EL ENCABEZADO DE LA POLIZA
                datos.BuscarPoliza(IdPoliza);
                double TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion;
                TotalSeguro = datos.TotalSeguro;
                TotalFlete = datos.TotalFlete;
                TotalOtrosCIF = datos.TotalOtrosCIF;
                TotalDAI = datos.TotalDAI;
                TotalISC = datos.TotalISC;
                TotalOtrosImp = datos.TotalOtrosImp;
                TotalOtrosGastosImportacion = datos.TotalOtrosGastosImportacion;
                //MANDO A BUSCAR LOS TOTALES QUE LLEVO ACUMULADO ANTES DEL LA ULTIMA LINEA.
                try {
                    datos.BuscarDetallesPoliza(IdPoliza);
                    ConexionDB conn = new ConexionDB();
                    conn.Conectar();
                    String consulta = "SELECT IdDetalle, IdPoliza, CodigoProducto, Descripcion, Cantidad, Precio, "
                            + "ValorUS, Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC, DAI, ISC, OtrosImpuesto,"
                            + "TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS"
                            + " FROM `detallepoliza` WHERE IdPoliza='" + IdPoliza + "' ";
                    ResultSet rs = null;
                    PreparedStatement pst = null;
                    pst = conn.conexion.prepareStatement(consulta);
                    rs = pst.executeQuery();
                    int id = 1;
                    double SeguroAcumulado = 0.00, FleteAcumulado = 0.00, OtrosCIFAcumulado = 0.00,
                            TotalCIFUS = 0.00, TotalCIFC = 0.00, DAIAcumulado = 0.00, ISCAcumulado = 0.00, OtrosImpAcumulado = 0.00,
                            TotalAduana = 0.00, OtrosGastosImportacionAcumulado = 0.00, CstTotal = 0.00;
                    while (rs.next() && id < TotalLineas) {
                        SeguroAcumulado = SeguroAcumulado + rs.getDouble("Seguro");//SEGURO
                        FleteAcumulado = FleteAcumulado + rs.getDouble("Flete");//FLETE
                        OtrosCIFAcumulado = OtrosCIFAcumulado + rs.getDouble("OtrosCIF");//OTROS CIF
                        DAIAcumulado = DAIAcumulado + rs.getDouble("DAI");//DAI
                        ISCAcumulado = ISCAcumulado + rs.getDouble("ISC");//ISC
                        OtrosImpAcumulado = OtrosImpAcumulado + rs.getDouble("OtrosImpuesto");//OtrosImpuesto
                        TotalAduana = TotalAduana + rs.getDouble("TotalImpuestosAduana");//TotalImpuestosAduana
                        OtrosGastosImportacionAcumulado = OtrosGastosImportacionAcumulado + rs.getDouble("OtrosGastosImportacion");//OtrosGastosImportacion
                        CstTotal = CstTotal + rs.getDouble("CostoTotal");//CostoTotal
                        id = id + 1;
                    } // FIN WHILE;
                    //MANDO A REALIZAR LOS CALCULOS Y ASIGNO LAS DIFERENCIAS AL ULTIMO ARTICULO DEL ITEM
                    Double Valor = (Cantidad * Precio);
                    Double Seguro = (TotalSeguro - SeguroAcumulado);
                    Double Flete = (TotalFlete - FleteAcumulado);
                    Double OtrosCIF = (TotalOtrosCIF - OtrosCIFAcumulado);
                    TotalCIFUS = (Valor + Seguro + Flete + OtrosCIF);
                    TotalCIFC = ((Valor + Seguro + Flete + OtrosCIF) * TasaCambio);
                    Double DAI = (TotalDAI - DAIAcumulado);
                    Double ISC = (TotalISC - ISCAcumulado);
                    Double OtrosImpuestos = (TotalOtrosImp - OtrosImpAcumulado);
                    Double TotalImpuestosAduana = (DAI + ISC + OtrosImpuestos);
                    Double OtrosGastosImportacion = (TotalOtrosGastosImportacion - OtrosGastosImportacionAcumulado);
                    Double CostoTotal = (TotalCIFC + TotalImpuestosAduana + OtrosGastosImportacion);
                    Double CostoUnitarioC = (CostoTotal / Cantidad);
                    Double CostoUnitarioUS = (CostoUnitarioC / TasaCambio);
                    datos.UpdateDetallePoliza(CompanyId, IdDetalle, IdPoliza, CodigoProducto, Descripcion, Cantidad, Precio, Valor, Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC,
                            DAI, ISC, OtrosImpuestos, TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS);
                    URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
                    //response.sendRedirect("AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza);
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                //FACTOR DE LOS GASTOS CIF
                Double FactorSeguro = Double.valueOf(request.getParameter("form-FactorSeguro"));
                Double FactorFlete = Double.valueOf(request.getParameter("form-FactorFlete"));
                Double FactorOtrosCIF = Double.valueOf(request.getParameter("form-FactorOtrosCIF"));
                //FACTOR DE LOS GASTOS DE ADUANA
                Double FactorDAI = Double.valueOf(request.getParameter("form-FactorDAI"));
                Double FactorISC = Double.valueOf(request.getParameter("form-FactorISC"));
                Double FactorOtrosImpuestos = Double.valueOf(request.getParameter("form-FactorOtrosImpuestos"));
                //FACTOR DE OTROS GASTOS DE IMPORTACION
                Double FactorOtrosGastosImportacion = Double.valueOf(request.getParameter("form-FactorOtrosGastosImportacion"));
                //------------CALCULANDO LIQUIDACION CUANDO NO ES ULTIMA LINEA------------// 
                Double Valor = (Cantidad * Precio);
                Double Seguro = (FactorSeguro * Valor);
                Double Flete = (FactorFlete * Valor);
                Double OtrosCIF = (FactorOtrosCIF * Valor);
                Double TotalCIFUS = (Seguro + Flete + OtrosCIF + Valor);
                Double TotalCIFC = ((Valor + Seguro + Flete + OtrosCIF) * TasaCambio);
                Double DAI = (TotalCIFC * FactorDAI);
                Double ISC = (TotalCIFC * FactorISC);
                Double OtrosImpuestos = (Valor * FactorOtrosImpuestos);
                Double TotalImpuestosAduana = (DAI + ISC + OtrosImpuestos);
                Double OtrosGastosImportacion = (FactorOtrosGastosImportacion * Valor);
                Double CostoTotal = (TotalCIFC + TotalImpuestosAduana + OtrosGastosImportacion);
                Double CostoUnitarioC = (CostoTotal / Cantidad);
                Double CostoUnitarioUS = (CostoUnitarioC / TasaCambio);
                try {
                    datos.UpdateDetallePoliza(CompanyId, IdDetalle, IdPoliza, CodigoProducto, Descripcion, Cantidad, Precio, Valor, Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC, DAI, ISC, OtrosImpuestos, TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS);
                    URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
                    //response.sendRedirect("AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            response.sendRedirect(URL);
        }//-------------------FINAL DE IF PARA ACTUALIZAR UN ITEM DE LA POLIZA-----------------------------//

        if (Accion.equals("DeleteDetallePoliza")) {
            int CompanyId = 1;
            int IdDetalle = Integer.valueOf(request.getParameter("form-IdDetalle"));
            int IdPoliza = Integer.valueOf(request.getParameter("form-IdPoliza"));
            datos.EliminarDetalle(IdDetalle, IdPoliza, CompanyId);
            URL = "Importaciones/AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza;
            //response.sendRedirect("AgregaDetallePoliza.jsp?IdPoliza=" + IdPoliza);
            response.sendRedirect(URL);
        }

        if (Accion.equals("UpdateEstadoPoliza")) {
            int CompanyId = 1;
            int IdPoliza = Integer.valueOf(request.getParameter("form-IdPoliza"));
            try {
                datos.UpdateEstadoPoliza(CompanyId, IdPoliza);
                URL = "Importaciones/BuscarPolizas.jsp";
                //response.sendRedirect("BuscarPolizas.jsp");
            } catch (Exception e) {
                e.getMessage();
            }
            response.sendRedirect(URL);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
