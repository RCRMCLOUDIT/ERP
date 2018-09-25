package model;

import beans.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ing. Moises Romero Mojica
 */
public class DaoPolizas {

    private PreparedStatement pst = null;
    private Statement st = null;
    private ResultSet rs = null;
    public static boolean existe = false;
    public static int IdPoliza, NumeroLiquidacion, NumeroPedido, RecBod, TotalLineas;
    public static String NumeroPoliza, NombreProveedor, FechaPoliza, PaisOrigen, CodProducto, Descripcion;
    public static double TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion,
            Cantidad, Precio;
    //ESTAS VARIABLES SON PARA LA FUNCION DEL CALCULO DE TOTALES ACUMULADOS, EN EL INGRESO DE DETALLE DE ITEMS DE POLIZA
    public static double SeguroAcumulado, FleteAcumulado, OtrosCIFAcumulado, CIFUSAcumulado, CIFCAcumulado, DAIAcumulado, ISCAcumulado, OtrosImpAcumulado,
            ImpuestosAduanaAcumulado, OtrosGastosImportacionAcumulado, CostoTotalAcumulado, CostoUnitarioCAcumulado, CostoUnitarioUSAcumulado;

    public DaoPolizas() {
        super();
    }

    //FUNCION PARA AGREGAR LOS PARAMETROS DE LA LIQUIDACION..
    public void AddPoliza(int CompanyId, String NumeroPoliza, int NumeroLiquidacion, int NumeroPedido, int RecBod, String NombreProveedor, String FechaPoliza,
            String PaisOrigen, Double TasaCambio, Double TotalFOB, Double TotalSeguro, Double TotalFlete, Double TotalOtrosCIF,
            Double TotalDAI, Double TotalISC, Double TotalOtrosImp, Double TotalOtrosGastosImportacion, int TotalLineas) throws SQLException {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.conexion.prepareStatement("INSERT INTO IBPOLIZA (CompanyId, NumeroPoliza, NumeroLiquidacion, NumeroPedido, RecBod,  NombreProveedor, FechaPoliza, PaisOrigen, TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion, Estado, TotalLineas, CreatedBy, CreatedFromIP) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, CompanyId);
            pst.setString(2, NumeroPoliza);
            pst.setInt(3, NumeroLiquidacion);
            pst.setInt(4, NumeroPedido);
            pst.setInt(5, RecBod);
            pst.setString(6, NombreProveedor);
            pst.setString(7, FechaPoliza);
            pst.setString(8, PaisOrigen);
            pst.setDouble(9, TasaCambio);
            pst.setDouble(10, TotalFOB);
            pst.setDouble(11, TotalSeguro);
            pst.setDouble(12, TotalFlete);
            pst.setDouble(13, TotalOtrosCIF);
            pst.setDouble(14, TotalDAI);
            pst.setDouble(15, TotalISC);
            pst.setDouble(16, TotalOtrosImp);
            pst.setDouble(17, TotalOtrosGastosImportacion);
            pst.setString(18, "Abierto");
            pst.setInt(19, TotalLineas);
            pst.setString(20, "Moises Romero");
            pst.setString(21, "192.168.1.0");
            pst.executeUpdate();
            conn.Cerrar();
            existe = true;
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    //FUNCION PARA ACTUALIZAR LOS PARAMETROS DE LA LIQUIDACION..
    public void UpdatePoliza(int CompanyId, int IdPoliza, String NumeroPoliza, int NumeroLiquidacion, int NumeroPedido, int RecBod, String NombreProveedor,
            String FechaPoliza, String PaisOrigen, Double TasaCambio, Double TotalFOB, Double TotalSeguro, Double TotalFlete,
            Double TotalOtrosCIF, Double TotalDAI, Double TotalISC, Double TotalOtrosImp, Double TotalOtrosGastosImportacion, int TotalLineas) throws SQLException {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.conexion.prepareStatement("UPDATE `IBPOLIZA` SET NumeroPoliza='" + NumeroPoliza + "', NumeroLiquidacion='" + NumeroLiquidacion + "', NumeroPedido='" + NumeroPedido + "', RecBod='" + RecBod + "',  NombreProveedor='" + NombreProveedor + "', FechaPoliza='" + FechaPoliza + "', PaisOrigen='" + PaisOrigen + "', TasaCambio='" + TasaCambio + "', TotalFOB='" + TotalFOB + "', TotalSeguro='" + TotalSeguro + "', TotalFlete='" + TotalFlete + "', TotalOtrosCIF='" + TotalOtrosCIF + "', TotalDAI='" + TotalDAI + "', TotalISC='" + TotalISC + "', TotalOtrosImp='" + TotalOtrosImp + "', TotalOtrosGastosImportacion='" + TotalOtrosGastosImportacion + "', TotalLineas='" + TotalLineas + "', ModifiedBy='Moises Romero', ModifiedOn=NOW(), ModifiedFromIP='192.168.1.1' WHERE IdPoliza='" + IdPoliza + "' AND CompanyId='" + CompanyId + "' ");
            pst.executeUpdate();
            conn.Cerrar();
            existe = true;
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    //FUNCION PARA OBTENER EL TOTAL DE REGISTRO DE ITEMS DE UNA POLIZA
    public boolean ObtenerTotalItems(int IdPoliza) {
        existe = false;
        try {
            TotalLineas = 0;
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            rs = st.executeQuery("SELECT Count(IdDetalle) AS TotalItems FROM `IBDETAILPOLIZA` WHERE IdPoliza = '" + IdPoliza + "' ");
            if (rs.next()) {
                existe = true;
                TotalLineas = rs.getInt("TotalItems");
            }
            conn.Cerrar();
        } catch (Exception e) {
            e.getMessage();
        }
        return existe;
    }

    //FUNCION PARA AGREGAR EL DETALLE DE LA POLIZA..
    public void AddDetallePoliza(int CompanyId, int IdPoliza, String CodigoProducto, String Descripcion, Double Cantidad, Double Precio, Double Valor,
            Double Seguro, Double Flete, Double OtrosCIF, Double TotalCIFUS, Double TotalCIFC,
            Double DAI, Double ISC, Double OtrosImpuesto, Double TotalImpuestosAduana, Double OtrosGastosImportacion,
            Double CostoTotal, Double CostoUnitarioC, Double CostoUnitarioUS) throws SQLException {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.conexion.prepareStatement("INSERT INTO `IBDETAILPOLIZA` (CompanyId, IdPoliza, CodigoProducto, Descripcion, Cantidad,  Precio, ValorUS, Seguro, Flete, OtrosCIF, TotalCIFUS, TotalCIFC, DAI, ISC, OtrosImpuesto, TotalImpuestosAduana, OtrosGastosImportacion, CostoTotal, CostoUnitarioC, CostoUnitarioUS, CreatedBy, CreatedFromIP) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, CompanyId);
            pst.setInt(2, IdPoliza);
            pst.setString(3, CodigoProducto);
            pst.setString(4, Descripcion);
            pst.setDouble(5, Cantidad);
            pst.setDouble(6, Precio);
            pst.setDouble(7, Valor);
            pst.setDouble(8, Seguro);
            pst.setDouble(9, Flete);
            pst.setDouble(10, OtrosCIF);
            pst.setDouble(11, TotalCIFUS);
            pst.setDouble(12, TotalCIFC);
            pst.setDouble(13, DAI);
            pst.setDouble(14, ISC);
            pst.setDouble(15, OtrosImpuesto);
            pst.setDouble(16, TotalImpuestosAduana);
            pst.setDouble(17, OtrosGastosImportacion);
            pst.setDouble(18, CostoTotal);
            pst.setDouble(19, CostoUnitarioC);
            pst.setDouble(20, CostoUnitarioUS);
            pst.setString(21, "Moises Romero");
            pst.setString(22, "192.168.1.0");
            pst.executeUpdate();
            conn.Cerrar();
            existe = true;
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    //FUNCION PARA ACTUALIZAR EL DETALLE DE UN ITEM DE LA POLIZA..
    public void UpdateDetallePoliza(int CompanyId, int IdDetalle, int IdPoliza, String CodigoProducto, String Descripcion, Double Cantidad, Double Precio, Double Valor,
            Double Seguro, Double Flete, Double OtrosCIF, Double TotalCIFUS, Double TotalCIFC,
            Double DAI, Double ISC, Double OtrosImpuesto, Double TotalImpuestosAduana, Double OtrosGastosImportacion,
            Double CostoTotal, Double CostoUnitarioC, Double CostoUnitarioUS) throws SQLException {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.conexion.prepareStatement("UPDATE IBDETAILPOLIZA SET CodigoProducto='" + CodigoProducto + "', Descripcion='" + Descripcion + "', Cantidad='" + Cantidad + "',  Precio='" + Precio + "', ValorUS='" + Valor + "', Seguro='" + Seguro + "', Flete='" + Flete + "', OtrosCIF='" + OtrosCIF + "', TotalCIFUS='" + TotalCIFUS + "', TotalCIFC='" + TotalCIFC + "', DAI='" + DAI + "', ISC='" + ISC + "', OtrosImpuesto='" + OtrosImpuesto + "', TotalImpuestosAduana='" + TotalImpuestosAduana + "', OtrosGastosImportacion='" + OtrosGastosImportacion + "', CostoTotal='" + CostoTotal + "', CostoUnitarioC='" + CostoUnitarioC + "', CostoUnitarioUS='" + CostoUnitarioUS + "'  WHERE IdDetalle='" + IdDetalle + "' AND IdPoliza='" + IdPoliza + "' AND CompanyId='" + CompanyId + "'");
            pst.executeUpdate();
            conn.Cerrar();
            existe = true;
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    //FUNCION PARA OBTENER LOS DATOS DEL DETALLE DE UN ITEM DE LA POLIZA..
    public boolean BuscardDetalle(int IdDetalle, int IdPoliza) {
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            rs = st.executeQuery("SELECT CodigoProducto, Descripcion, Cantidad, Precio FROM `detallepoliza`  WHERE IdDetalle=" + IdDetalle + " AND IdPoliza=" + IdPoliza + " ");
            if (rs.next()) {
                existe = true;
                CodProducto = rs.getString("CodigoProducto");
                Descripcion = rs.getString("Descripcion");
                Cantidad = rs.getDouble("Cantidad");
                Precio = rs.getDouble("Precio");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return existe;
    }

    //FUNCION PARA ELIMINAR EL DETALLE DE UN ITEM DE LA POLIZA..
    public boolean EliminarDetalle(int IdDetalle, int IdPoliza, int CompanyId) {
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("DELETE FROM IBDETAILPOLIZA  WHERE IdDetalle=" + IdDetalle + " AND IdPoliza=" + IdPoliza + " AND CompanyId=" + CompanyId + " ");
            pst.executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }
        return existe;
    }

    //FUNCION PARA OBTENER EL ID DE LA LIQUIDACION..
    public boolean ObtenerIdPoliza() {
        existe = false;
        IdPoliza = 0;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            rs = st.executeQuery("Select Count(IdPoliza) AS IdPoliza FROM IBPOLIZA");
            if (rs.next()) {
                existe = true;
                IdPoliza = rs.getInt("IdPoliza");
            }
            conn.Cerrar();
        } catch (Exception e) {
            e.getMessage();
        }
        return existe;
    }

    //FUNCION PARA OBTENER LOS DATOS DE LA POLIZA..
    public boolean BuscarPoliza(int IdPoliza) {
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            rs = st.executeQuery("SELECT NumeroPoliza, NumeroLiquidacion, NumeroPedido, RecBod, NombreProveedor, FechaPoliza, PaisOrigen, TasaCambio, TotalFOB, TotalSeguro, TotalFlete, TotalOtrosCIF, TotalDAI, TotalISC, TotalOtrosImp, TotalOtrosGastosImportacion, TotalLineas FROM `IBPOLIZA`  WHERE IdPoliza=" + IdPoliza + " ");
            if (rs.next()) {
                existe = true;
                NumeroPoliza = rs.getString("NumeroPoliza");
                NumeroLiquidacion = rs.getInt("NumeroLiquidacion");
                NumeroPedido = rs.getInt("NumeroPedido");
                RecBod = rs.getInt("RecBod");
                NombreProveedor = rs.getString("NombreProveedor");
                FechaPoliza = rs.getString("FechaPoliza");
                PaisOrigen = rs.getString("PaisOrigen");
                TasaCambio = rs.getDouble("TasaCambio");
                TotalFOB = rs.getDouble("TotalFOB");
                TotalSeguro = rs.getDouble("TotalSeguro");
                TotalFlete = rs.getDouble("TotalFlete");
                TotalOtrosCIF = rs.getDouble("TotalOtrosCIF");
                TotalDAI = rs.getDouble("TotalDAI");
                TotalISC = rs.getDouble("TotalISC");
                TotalOtrosImp = rs.getDouble("TotalOtrosImp");
                TotalOtrosGastosImportacion = rs.getDouble("TotalOtrosGastosImportacion");
                TotalLineas = rs.getInt("TotalLineas");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return existe;
    }

    //FUNCION PARA OBTENER LOS TOTALES ACUMULADOS, PARA INSERCION DE LA ULTIMA LINEA EN EL DETALLE DE LA POLIZA..
    public boolean BuscarDetallesPoliza(int IdPoliza) {
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            rs = st.executeQuery("SELECT SUM(Seguro) AS Seguro, SUM(Flete) AS Flete, SUM(OtrosCIF) AS OtrosCIF, "
                    + "SUM(TotalCIFUS) AS TotalCIFUS, SUM(TotalCIFC) AS TotalCIFC, SUM(DAI) AS DAI, SUM(ISC) AS ISC, "
                    + "SUM(OtrosImpuesto) AS OtrosImpuesto, SUM(TotalImpuestosAduana) AS TotalImpuestosAduana, "
                    + "SUM(OtrosGastosImportacion) AS OtrosGastosImportacion, SUM(CostoTotal) AS CostoTotal, "
                    + "SUM(CostoUnitarioC) AS CostoUnitarioC, "
                    + "SUM(CostoUnitarioUS) AS CostoUnitarioUS FROM `IBDETAILPOLIZA` WHERE IdPoliza='" + IdPoliza + "'");
            if (rs.next()) {
                existe = true;
                SeguroAcumulado = rs.getDouble("Seguro");
                FleteAcumulado = rs.getDouble("Flete");
                OtrosCIFAcumulado = rs.getDouble("OtrosCIF");
                CIFUSAcumulado = rs.getDouble("TotalCIFUS");
                CIFCAcumulado = rs.getDouble("TotalCIFC");
                DAIAcumulado = rs.getDouble("DAI");
                ISCAcumulado = rs.getDouble("ISC");
                OtrosImpAcumulado = rs.getDouble("OtrosImpuesto");
                ImpuestosAduanaAcumulado = rs.getDouble("TotalImpuestosAduana");
                OtrosGastosImportacionAcumulado = rs.getDouble("TotalOtrosGastosImportacion");
                CostoTotalAcumulado = rs.getDouble("CostoTotal");
                CostoUnitarioCAcumulado = rs.getDouble("CostoUnitarioC");
                CostoUnitarioUSAcumulado = rs.getDouble("CostoUnitarioUS");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return existe;
    }

    //FUNCION PARA CAMBIAR EL ESTADO DE LA POLIZA A CERRADO....
    public void UpdateEstadoPoliza(int CompanyId, int IdPoliza) throws SQLException {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            st = conn.conexion.createStatement();
            pst = conn.conexion.prepareStatement("UPDATE IBPOLIZA SET Estado='Cerrado' WHERE IdPoliza='" + IdPoliza + "' AND CompanyId='" + CompanyId + "' ");
            pst.executeUpdate();
            conn.Cerrar();
            existe = true;
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
