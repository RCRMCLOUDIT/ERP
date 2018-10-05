package model;

import beans.ConexionDB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Moises Romero
 */
public class DaoContabilidad extends ConexionDB {

    private Connection conn = null;
    private PreparedStatement pst = null;
    private Statement st = null;
    private ResultSet rs = null;
    public static boolean existe = false;

    //VARIABLES PARA EL TIPO DE CUENTA
    public static String GetGLTPNAME, GetGLTPACCID, GetCreatedBy, GetCreatedOn, GetCreatedFromIP, GetModifiedBy, GetModifiedOn, GetModifiedFromIP;
    public static int GetGLTPCLSID, GetCompanyId, GetRelacion;

    //VARIABLES PARA CATALOGO CONTABLE
    public String GetAccountLevel1, GetAccountLevel2, GetAccountLevel3, GetAccountLevel4, GetAccountLevel5, GetAccountLevel6,
            GetAccountNumber, GetAccountName, GetIsacctaccessible, GetActive, GetInactivityDate, GetComments, GetFullNameCta;
    public static int GetIdCatalogo;
    public static Double CurrentBalance;

    //VARIABLES PARA LA PLANTILLA CONTABLE
    public static int GetCurrentGLTMID, GetCurrentGLTMLINEID, GetGLTMCURRENCY, GetGLTMCCTYPID;
    public static String GetGLTMREF, GetGLTMMEMO, GetGLTMMEMODET, GetMOVEMENTTYPE;
    public Double GetGLTMAMOUNT, GetGLTMNOMAMT, GetGLTMBASAMT, GetGLTMEXCHANGE, GetGLTMCCID1, GetGLTMCCID2, GetGLTMCCID3, GetGLTMCCID4, GetGLTMCCID5;

    //VARIABLES PARA EL COMPROBANTE CONTABLE
    public static int GetCurrentIdComprobante, GetCurrentLineIdComp, GetGLBACHCCTYPID;
    public static String GetGLBACHMEMODET, GetGLBACHMOVEMENTTYPE;
    public static Double GetGLBACHAMOUNT;

    public DaoContabilidad() {
        super();
    }

    //FUNCION PARA MANDAR  A GUARDAR EN LA BASE DE DATOS UN NUEVO REGISTRO EN LA TABLA TIPO DE CUENTA. NOMBRE DE LA TABLA: IBGLTYPACC
    public void GLADDTYPACC(int CompanyId, int GLTPCLSID, String GLTPNAME, String GLTPACCID, String CreatedBy, String CreatedFromIP) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit,
            // asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement
            * que usaremos para invocar el SP
            * La cantidad de "?" determina la cantidad
            * parametros que recibe el procedimiento
             */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLADDTYPACC(?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, CompanyId);
            cs.setInt(2, GLTPCLSID);
            cs.setString(3, GLTPNAME);
            cs.setString(4, GLTPACCID);
            cs.setString(5, CreatedBy);
            cs.setString(6, CreatedFromIP);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }

    }

    //FUNCION PARA MANDAR A ACTUALIZAR EN LA BASE DE DATOS EL REGISTRO EN LA TABLA TIPO DE CUENTA. NOMBRE DE LA TABLA: IBGLTYPACC
    public void GLUPDTYPACC(int GLTPCLSID, String GLTPNAME, String GLTPACCID, String ModifiedBy, String ModifiedFromIP) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit,
            // asi controlamos la transaccion de manera manual
            conn.conexion.setAutoCommit(false);
            /* Instanciamos el objeto callable statement que usaremos para invocar el SP
            * La cantidad de "?" determina la cantidad parametros que recibe el procedimiento
             */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLUPDTYPACC(?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, GLTPCLSID);
            cs.setString(2, GLTPNAME);
            cs.setString(3, GLTPACCID);
            cs.setString(4, ModifiedBy);
            cs.setString(5, ModifiedFromIP);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }
    }

    // FUNCION PARA MANDAR A VERIFICAR SI YA EXISTE EL NOMBRE EN LA TABLA IBGLTYPACC.
    public boolean VerifarNombre(String GLTPNAME) {
        existe = false;
        GetGLTPCLSID = 0; //VARIABLE PARA ALMACENAR EL CODIGO DE TIPO DE CUENTA
        GetGLTPNAME = ""; //VARIABLE PARA ALMACENAR EL NOMBRE TIPO CUENTA
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT `GLTPCLSID`, `GLTPNAME` FROM `IBGLTYPACC` WHERE `GLTPNAME` LIKE'" + GLTPNAME + "'");
            if (rs.next()) {
                existe = true;
                GetGLTPCLSID = rs.getInt("GLTPCLSID");
                GetGLTPNAME = rs.getString("GLTPNAME");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {
        }
        return existe;
    }

    // FUNCION PARA MANDAR A VERIFICAR SI YA EXISTE EL CODIGO EN LA TABLA IBGLTYPACC.
    public boolean VerificarCod(int GLTPCLSID) {
        existe = false;
        GetGLTPCLSID = 0; //VARIABLE PARA ALMACENAR EL CODIGO DE TIPO DE CUENTA
        GetGLTPNAME = "";//VARIABLE PARA ALMACENAR EL NOMBRE TIPO CUENTA
        GetGLTPACCID = ""; //VARIABLE PARA ALMACENAR EL TIPO DE CUENTA A, P, C, I, G.
        //A: ACTIVO, P: PASIVO, C: CAPITAL, I: INGRESO, G: GASTOS. 
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT `GLTPCLSID`, `GLTPNAME`, GLTPACCID FROM `IBGLTYPACC` WHERE `GLTPCLSID` ='" + GLTPCLSID + "'");
            if (rs.next()) {
                existe = true;
                GetGLTPCLSID = rs.getInt("GLTPCLSID");
                GetGLTPNAME = rs.getString("GLTPNAME");
                GetGLTPACCID = rs.getString("GLTPACCID");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {
        }
        return existe;
    }

    // FUNCION PARA MANDAR A VERIFICAR SI YA EXISTE UNA RELACION DE LA TABLA IBGLTYPACC. QUE MANEJA LOS TIPOS DE CUENTA.
    //CON LA TABLA IBGLACCNTS, QUE MANEJA EL CATALOGO CONTABLE
    public boolean VerificarRelacion(int GLTPCLSID) {
        existe = false;
        GetRelacion = 0; //VARIABLE PARA VERIFICAR SI EL TIPO DE CUENTA YA ESTA ASIGNADO A CUENTAS DEL CATALAGO CONTABLE
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT COUNT(IBGLACCNTS.`GLTPCLSID`) ASIGNADO FROM `IBGLACCNTS` INNER JOIN IBGLTYPACC ON IBGLACCNTS.GLTPCLSID=IBGLTYPACC.GLTPCLSID WHERE IBGLTYPACC.GLTPCLSID='" + GLTPCLSID + "'");
            if (rs.next()) {
                existe = true;
                GetRelacion = rs.getInt("ASIGNADO");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {
        }
        return existe;
    }

    //FUNCION PARA MANDAR  A GUARDAR EN LA BASE DE DATOS UN NUEVO REGISTRO EN LA TABLA CATALAGO CONTABLE. NOMBRE DE LA TABLA: IBGLACCNTS
    public void GLADDACNTS(String AccountLevel1, String AccountLevel2, String AccountLevel3, String AccountLevel4, String AccountLevel5,
            String AccountLevel6, String AccountNumber, String AccountName, String Comments, String CreatedBy, String CreatedFromIP, int GLTPCLSID,
            int CompanyId) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP
            * La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLADDACNTS(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setString(1, AccountLevel1);
            cs.setString(2, AccountLevel2);
            cs.setString(3, AccountLevel3);
            cs.setString(4, AccountLevel4);
            cs.setString(5, AccountLevel5);
            cs.setString(6, AccountLevel6);
            cs.setString(7, AccountNumber);
            cs.setString(8, AccountName);
            cs.setString(9, Comments);
            cs.setString(10, CreatedBy);
            cs.setString(11, CreatedFromIP);
            cs.setInt(12, GLTPCLSID);
            cs.setInt(13, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }

    }

    //FUNCION PARA MANDAR  A ACTUALIZAR EN LA BASE DE DATOS UN REGISTRO DE LA TABLA CATALAGO CONTABLE. NOMBRE DE LA TABLA: IBGLACCNTS
    public void GLUPDACNTS(int IdCatalogo, String AccountNumber, String AccountName, String Comments, String ModifiedBy, String ModifiedFromIP,
            int CompanyId) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP
            * La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLUPDACNTS(?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, IdCatalogo);
            cs.setString(2, AccountNumber);
            cs.setString(3, AccountName);
            cs.setString(4, Comments);
            cs.setString(5, ModifiedBy);
            cs.setString(6, ModifiedFromIP);
            cs.setInt(7, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }

    }

    //FUNCION PARA BUSCAR LOS DATOS DE LA CUENTA CONTABLE
    public boolean BuscarIBGLACCNTS(int IDCATALOGO) {
        existe = false;
        GetAccountLevel1 = "";
        GetAccountLevel2 = "";
        GetAccountLevel3 = "";
        GetAccountLevel4 = "";
        GetAccountLevel5 = "";
        GetAccountLevel6 = "";
        GetAccountNumber = "";
        GetAccountName = "";
        GetGLTPCLSID = 0;
        GetComments = "";
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, AccountNumber, "
                    + "AccountName, Isacctaccessible, Active, InactivityDate, Comments, CurrentBalance, CreatedBy, CreatedOn, CreatedFromIP,"
                    + "ModifiedBy, ModifiedOn, ModifiedFromIP, GLTPCLSID, CompanyId FROM IBGLACCNTS WHERE IDCATALOGO=" + IDCATALOGO + " ");
            if (rs.next()) {
                existe = true;
                GetAccountLevel1 = rs.getString("AccountLevel1");
                GetAccountLevel2 = rs.getString("AccountLevel2");
                GetAccountLevel3 = rs.getString("AccountLevel3");
                GetAccountLevel4 = rs.getString("AccountLevel4");
                GetAccountLevel5 = rs.getString("AccountLevel5");
                GetAccountLevel6 = rs.getString("AccountLevel6");
                GetGLTPCLSID = rs.getInt("GLTPCLSID");
                GetAccountNumber = rs.getString("AccountNumber");
                GetAccountName = rs.getString("AccountName");
                GetComments = rs.getString("Comments");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    //FUNCION PARA VERIFICAR QUE EL NOMBRE Y NUMERO DE CUENTA NO EXISTAN EN EL CATALAGO CONTABLE
    public boolean CheckNameIBGLACCNTS(String AccountName) {
        existe = false;
        GetIdCatalogo = 0;
        GetAccountNumber = "";
        GetAccountName = "";
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT IDCATALOGO, AccountNumber, AccountName FROM IBGLACCNTS WHERE AccountName LIKE'" + AccountName + "'");
            if (rs.next()) {
                existe = true;
                GetIdCatalogo = rs.getInt("IDCATALOGO");
                GetAccountNumber = rs.getString("AccountNumber");
                GetAccountName = rs.getString("AccountName");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    //FUNCION PARA VERIFICAR QUE EL NOMBRE Y NUMERO DE CUENTA NO EXISTAN EN EL CATALAGO CONTABLE
    public boolean CheckNumberIBGLACCNTS(String AccountNumber) {
        existe = false;
        GetIdCatalogo = 0;
        GetAccountNumber = "";
        GetAccountName = "";
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT IDCATALOGO, AccountNumber, AccountName FROM IBGLACCNTS WHERE AccountNumber LIKE'" + AccountNumber + "'");
            if (rs.next()) {
                existe = true;
                GetIdCatalogo = rs.getInt("IDCATALOGO");
                GetAccountNumber = rs.getString("AccountNumber");
                GetAccountName = rs.getString("AccountName");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    //FUNCION PARA BUSCAR LA NUMERACION DE LOS NIVELES DE LA CUENTA CONTABLE
    public boolean GetCountLevel() {
        existe = false;
        GetAccountLevel1 = "";
        GetAccountLevel2 = "";
        GetAccountLevel3 = "";
        GetAccountLevel4 = "";
        GetAccountLevel5 = "";
        GetAccountLevel6 = "";
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT COUNT(*) AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6 FROM IBGLACCNTS");
            if (rs.next()) {
                existe = true;
                GetAccountLevel1 = rs.getString("AccountLevel1");
                GetAccountLevel2 = rs.getString("AccountLevel2");
                GetAccountLevel3 = rs.getString("AccountLevel3");
                GetAccountLevel4 = rs.getString("AccountLevel4");
                GetAccountLevel5 = rs.getString("AccountLevel5");
                GetAccountLevel6 = rs.getString("AccountLevel6");
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    //FUNCION PARA MANDAR  A GUARDAR EN LA BASE DE DATOS UN NUEVO REGISTRO EN LA TABLA DE PLANTILLAS CONTABLES. NOMBRE DE LA TABLA: IBGLTPDST
    public void GLADDTPDST(int GLTMID, int IdCatalogo, int GLTMLINEID, String GLTMREF, String GLTMMEMO, String AccountLevel1,
            String AccountLevel2, String AccountLevel3, String AccountLevel4, String AccountLevel5, String AccountLevel6,
            String GLTMMEMODET, Double GLTMAMOUNT, Double GLTMNOMAMT, Double GLTMBASAMT, int GLTMCURRENCY, Double GLTMEXCHANGE,
            String MOVEMENTTYPE, int GLTMCCTYPID, Double GLTMCCID1, Double GLTMCCID2, Double GLTMCCID3, Double GLTMCCID4, Double GLTMCCID5,
            String CreatedBy, String CreatedFromIP, int CompanyId) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLADDTPDST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, GLTMID);
            cs.setInt(2, IdCatalogo);
            cs.setInt(3, GLTMLINEID);
            cs.setString(4, GLTMREF);
            cs.setString(5, GLTMMEMO);
            cs.setString(6, AccountLevel1);
            cs.setString(7, AccountLevel2);
            cs.setString(8, AccountLevel3);
            cs.setString(9, AccountLevel4);
            cs.setString(10, AccountLevel5);
            cs.setString(11, AccountLevel6);
            cs.setString(12, GLTMMEMODET);
            cs.setDouble(13, GLTMAMOUNT);
            cs.setDouble(14, GLTMNOMAMT);
            cs.setDouble(15, GLTMBASAMT);
            cs.setDouble(16, GLTMCURRENCY);
            cs.setDouble(17, GLTMEXCHANGE);
            cs.setString(18, MOVEMENTTYPE);
            cs.setInt(19, GLTMCCTYPID);
            cs.setDouble(20, GLTMCCID1);
            cs.setDouble(21, GLTMCCID2);
            cs.setDouble(22, GLTMCCID3);
            cs.setDouble(23, GLTMCCID4);
            cs.setDouble(24, GLTMCCID5);
            cs.setString(25, CreatedBy);
            cs.setString(26, CreatedFromIP);
            cs.setInt(27, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }
    }

    // FUNCION PARA ELIMINAR LA PLANTILLA CONTABLE
    public boolean DeletePlantilla(int GLTMID) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("DELETE FROM IBGLTPDST WHERE GLTMID=" + GLTMID + " ");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA ACTUALIZAR LINEA DE LA PLANTILLA CONTABLE
    public void UpdateGLTMLINEID(int GLTMID, int IdCatalogo, int GLTMLINEID, String AccountLevel1,
            String AccountLevel2, String AccountLevel3, String AccountLevel4, String AccountLevel5, String AccountLevel6,
            String GLTMMEMODET, Double GLTMAMOUNT, Double GLTMNOMAMT, Double GLTMBASAMT, int GLTMCURRENCY, Double GLTMEXCHANGE,
            String MOVEMENTTYPE, int GLTMCCTYPID, Double GLTMCCID1, Double GLTMCCID2, Double GLTMCCID3, Double GLTMCCID4, Double GLTMCCID5,
            String ModifiedBy, String ModifiedFromIP, int CompanyId) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLUPDTPDST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, GLTMID);
            cs.setInt(2, IdCatalogo);
            cs.setInt(3, GLTMLINEID);
            cs.setString(4, AccountLevel1);
            cs.setString(5, AccountLevel2);
            cs.setString(6, AccountLevel3);
            cs.setString(7, AccountLevel4);
            cs.setString(8, AccountLevel5);
            cs.setString(9, AccountLevel6);
            cs.setString(10, GLTMMEMODET);
            cs.setDouble(11, GLTMAMOUNT);
            cs.setDouble(12, GLTMNOMAMT);
            cs.setDouble(13, GLTMBASAMT);
            cs.setDouble(14, GLTMCURRENCY);
            cs.setDouble(15, GLTMEXCHANGE);
            cs.setString(16, MOVEMENTTYPE);
            cs.setInt(17, GLTMCCTYPID);
            cs.setDouble(18, GLTMCCID1);
            cs.setDouble(19, GLTMCCID2);
            cs.setDouble(20, GLTMCCID3);
            cs.setDouble(21, GLTMCCID4);
            cs.setDouble(22, GLTMCCID5);
            cs.setString(23, ModifiedBy);
            cs.setString(24, ModifiedFromIP);
            cs.setInt(25, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }
    }

    //FUNCION PARA MANDAR  A GUARDAR EN LA BASE DE DATOS UN NUEVO REGISTRO EN LA TABLA DE COMPROBANTE CONTABLE. NOMBRE DE LA TABLA: GLBACHDST
    public void GLADDBACH(int IdComprobante, int GLTMID, int IdCatalogo, int GLBACHLINEID, String GLBACHMREF, String GLBACHMEMO, String GLBACHDATE,
            String AccountLevel1, String AccountLevel2, String AccountLevel3, String AccountLevel4, String AccountLevel5, String AccountLevel6,
            String GLBACHMEMODET, Double GLBACHAMOUNT, Double GLBACHNOMAMT, Double GLBACHBASAMT, int GLBACHCURRENCY, Double GLBACHEXCHANGE,
            String GLBACHMOVEMENTTYPE, int GLBACHCCTYPID, Double GLBACHCCID1, Double GLBACHCCID2, Double GLBACHCCID3, Double GLBACHCCID4,
            Double GLBACHCCID5, String CreatedBy, String CreatedFromIP, int CompanyId) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLADDBACH(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, IdComprobante);
            cs.setInt(2, GLTMID);
            cs.setInt(3, IdCatalogo);
            cs.setInt(4, GLBACHLINEID);
            cs.setString(5, GLBACHMREF);
            cs.setString(6, GLBACHMEMO);
            cs.setString(7, GLBACHDATE);
            cs.setString(8, AccountLevel1);
            cs.setString(9, AccountLevel2);
            cs.setString(10, AccountLevel3);
            cs.setString(11, AccountLevel4);
            cs.setString(12, AccountLevel5);
            cs.setString(13, AccountLevel6);
            cs.setString(14, GLBACHMEMODET);
            cs.setDouble(15, GLBACHAMOUNT);
            cs.setDouble(16, GLBACHNOMAMT);
            cs.setDouble(17, GLBACHBASAMT);
            cs.setDouble(18, GLBACHCURRENCY);
            cs.setDouble(19, GLBACHEXCHANGE);
            cs.setString(20, GLBACHMOVEMENTTYPE);
            cs.setInt(21, GLBACHCCTYPID);
            cs.setDouble(22, GLBACHCCID1);
            cs.setDouble(23, GLBACHCCID2);
            cs.setDouble(24, GLBACHCCID3);
            cs.setDouble(25, GLBACHCCID4);
            cs.setDouble(26, GLBACHCCID5);
            cs.setString(27, CreatedBy);
            cs.setString(28, CreatedFromIP);
            cs.setInt(29, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }
    }

    //FUNCION PARA MANDAR A ACTUALIZAR EN LA BASE DE DATOS UN REGISTRO EN LA TABLA DE COMPROBANTE CONTABLE. NOMBRE DE LA TABLA: GLBACHDST
    public void GLUPDBACH(int IdComprobante, int IdCatalogo, int GLBACHLINEID, String GLBACHMREF, String GLBACHMEMO, String GLBACHDATE,
            String AccountLevel1, String AccountLevel2, String AccountLevel3, String AccountLevel4, String AccountLevel5, String AccountLevel6,
            String GLBACHMEMODET, Double GLBACHAMOUNT, Double GLBACHNOMAMT, Double GLBACHBASAMT, int GLBACHCURRENCY, Double GLBACHEXCHANGE,
            String GLBACHMOVEMENTTYPE, int GLBACHCCTYPID, Double GLBACHCCID1, Double GLBACHCCID2, Double GLBACHCCID3, Double GLBACHCCID4,
            Double GLBACHCCID5, String ModifiedBy, String ModifiedFromIP, int CompanyId) throws SQLException {
        existe = false;
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            //conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLUPDBACH(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, IdComprobante);
            cs.setInt(2, IdCatalogo);
            cs.setInt(3, GLBACHLINEID);
            cs.setString(4, GLBACHMREF);
            cs.setString(5, GLBACHMEMO);
            cs.setString(6, GLBACHDATE);
            cs.setString(7, AccountLevel1);
            cs.setString(8, AccountLevel2);
            cs.setString(9, AccountLevel3);
            cs.setString(10, AccountLevel4);
            cs.setString(11, AccountLevel5);
            cs.setString(12, AccountLevel6);
            cs.setString(13, GLBACHMEMODET);
            cs.setDouble(14, GLBACHAMOUNT);
            cs.setDouble(15, GLBACHNOMAMT);
            cs.setDouble(16, GLBACHBASAMT);
            cs.setInt(17, GLBACHCURRENCY);
            cs.setDouble(18, GLBACHEXCHANGE);
            cs.setString(19, GLBACHMOVEMENTTYPE);
            cs.setInt(20, GLBACHCCTYPID);
            cs.setDouble(21, GLBACHCCID1);
            cs.setDouble(22, GLBACHCCID2);
            cs.setDouble(23, GLBACHCCID3);
            cs.setDouble(24, GLBACHCCID4);
            cs.setDouble(25, GLBACHCCID5);
            cs.setString(26, ModifiedBy);
            cs.setString(27, ModifiedFromIP);
            cs.setInt(28, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            conn.close();
        }
    }

    //FUNCION PARA MANDAR A REGISTRAR TRANSACCIONES CONTABLES EN LA BASE DE DATOS. NOMBRE DE LA TABLA: IBGLDSTACC
    public void GLADDTRANSTACC(int LineNumber, String DateTransaction, String DocumentNumber, String TypeDocument, String Coments, int IdCatalogo,
            String AccountLevel1, String AccountLevel2, String AccountLevel3, String AccountLevel4, String AccountLevel5, String AccountLevel6,
            String ComentsDetail, Double AmountTransCurr, Double AmountCompanyCurr, Double AmountBaseCurr, int CurrencyID, Double ExchangeRateTrans,
            Double ExchangeRateBase, String MOVEMENTTYPE, int GLTMCCTYPID, Double GLTMCCID1, Double GLTMCCID2, Double GLTMCCID3, Double GLTMCCID4,
            Double GLTMCCID5, Double TAGID, String CreatedBy, String CreatedFromIP, int CompanyId) throws SQLException {
        try {
            // creamos la conexion
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            conn.conexion.setAutoCommit(false);
            /* instanciamos el objeto callable statement que usaremos para invocar el SP La cantidad de "?" determina la cantidad parametros que recibe el procedimiento */
            CallableStatement cs = null;
            cs = conn.Conectar().prepareCall("CALL GLADDTRANSTACC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            // cargar parametros al SP
            cs.setInt(1, LineNumber);
            cs.setDate(2, Date.valueOf(DateTransaction));
            cs.setString(3, DocumentNumber);
            cs.setString(4, TypeDocument);
            cs.setString(5, Coments);
            cs.setInt(6, IdCatalogo);
            cs.setString(7, AccountLevel1);
            cs.setString(8, AccountLevel2);
            cs.setString(9, AccountLevel3);
            cs.setString(10, AccountLevel4);
            cs.setString(11, AccountLevel5);
            cs.setString(12, AccountLevel6);
            cs.setString(13, ComentsDetail);
            cs.setDouble(14, AmountTransCurr);
            cs.setDouble(15, AmountCompanyCurr);
            cs.setDouble(16, AmountBaseCurr);
            cs.setInt(17, CurrencyID);
            cs.setDouble(18, ExchangeRateTrans);
            cs.setDouble(19, ExchangeRateBase);
            cs.setString(20, MOVEMENTTYPE);
            cs.setInt(21, GLTMCCTYPID);
            cs.setDouble(22, GLTMCCID1);
            cs.setDouble(23, GLTMCCID2);
            cs.setDouble(24, GLTMCCID3);
            cs.setDouble(25, GLTMCCID4);
            cs.setDouble(26, GLTMCCID5);
            cs.setDouble(27, TAGID);
            cs.setString(28, CreatedBy);
            cs.setString(29, CreatedFromIP);
            cs.setInt(30, CompanyId);
            // ejecutar el SP
            cs.execute();
            // confirmar si se ejecuto sin errores
            conn.conexion.commit();
        } catch (SQLException e) {
            // deshacer la ejecucion en caso de error
            //conn.rollback();
            // informar por consola
            System.out.println("Error Al ejcutar SP: " + e.getMessage());
        } finally {
            // cerrar la conexion
            //conn.close();
        }
    }

    //FUNCION PARA MANDAR A LEER EL COMPROBANTE EN LA TABLA IBGLBATCHDST QUE SE CONTABILIZARA
    public void ContabilizarBATCH(int IdComprobante, int IdCompany, String CreatedBy, String CreatedFromIP) {
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = conn.conexion.prepareStatement("SELECT IdComprobante, GLTMID, IDCATALOGO, GLBACHLINEID, GLBACHMREF, GLBACHMEMO, GLBACHDATE, AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, GLBACHMEMODET, GLBACHAMOUNT, GLBACHNOMAMT, GLBACHBASAMT, GLBACHCURRENCY, GLBACHEXCHANGE, GLBACHMOVEMENTTYPE, GLBACHCCTYPID, GLBACHCCID1, GLBACHCCID2, GLBACHCCID3, GLBACHCCID4, GLBACHCCID5, GLBACHSTATUS FROM IBGLBATCHDST WHERE IdComprobante =" + IdComprobante + "  AND GLBACHSTATUS='Aprobado' AND CompanyId=" + IdCompany + " ");
            rs = pst.executeQuery();
            while (rs.next()) {
                GLADDTRANSTACC(rs.getInt(4), rs.getString(7), String.valueOf(IdComprobante), "Comprobante", rs.getString(6),
                        rs.getInt(3), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getDouble(15), rs.getDouble(16), rs.getDouble(17), rs.getInt(18),
                        rs.getDouble(19), rs.getDouble(19), rs.getString(20), rs.getInt(21), rs.getDouble(22), rs.getDouble(23), rs.getDouble(24),
                        rs.getDouble(25), rs.getDouble(26), 0.00, CreatedBy, CreatedFromIP, IdCompany);
            }
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT.
            conn.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //FUNCION PARA MANDAR A APROBAR EL COMPROBANTE CONTABLE. NOMBRE DE LA TABLA: GLBACHDST
    public boolean AplicarBATCH(int IdComprobante, int IdCompany, String AppliedBy, String AppliedFromIP) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("UPDATE IBGLBATCHDST SET GLBACHSTATUS='Aplicado', AppliedBy='" + AppliedBy + "', AppliedOn=NOW(), AppliedFromIP='" + AppliedFromIP + "' WHERE IdComprobante=" + IdComprobante + " AND CompanyId=" + IdCompany + " ");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    //FUNCION PARA MANDAR A APROBAR EL COMPROBANTE CONTABLE. NOMBRE DE LA TABLA: GLBACHDST
    public boolean AprobarBATCH(int IdComprobante, int IdCompany, String ApprovedBy, String ApprovedFromIP) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("UPDATE IBGLBATCHDST SET GLBACHSTATUS='Aprobado', ApprovedBy='" + ApprovedBy + "', ApprovedOn=NOW(), ApprovedFromIP='" + ApprovedFromIP + "' WHERE IdComprobante=" + IdComprobante + " AND CompanyId=" + IdCompany + " ");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA ELIMINAR LA PLANTILLA CONTABLE
    public boolean DeleteBATCH(int IdComprobante) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("DELETE FROM IBGLBATCHDST WHERE IdComprobante=" + IdComprobante + " ");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA ACTUALIZAR EL NOMBRE DE LA PLANTILLA
    public boolean UpdateHeaderNameTMP(int GLTMID, String GLTMMEMO) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("UPDATE IBGLTDPST SET GLTMMEMO='" + GLTMMEMO + "' WHERE GLTMID=" + GLTMID + " ");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA ACTUALIZAR EL NOMBRE DEL COMPROBANTE
    public boolean UpdateHeaderNameBATCH(int IdComprobante, String GLBACHMEMO) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("UPDATE IBGLBATCHDST SET GLBACHMEMO='" + GLBACHMEMO + "' WHERE IdComprobante=" + IdComprobante + " ");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA ELIMINAR LINEA DE LA PLANTILLA CONTABLE
    public boolean DeleteGLTMLINEID(int GLTMID, int GLTMLINEID, int IDCATALOGO) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("DELETE FROM IBGLTDPST WHERE GLTMID=" + GLTMID + " AND GLTMLINEID=" + GLTMLINEID + " AND IDCATALOGO=" + IDCATALOGO + "");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA LOS DATOS DE LA LINEA QUE SE ESTA EDITANDO DE LA PLANTILLA.
    public boolean GetDetailLine(int GLTMID, int GLTMLINEID) {
        existe = false;
        GetIdCatalogo = 0;
        GetGLTMMEMODET = "";
        GetGLTMAMOUNT = 0.00;
        GetMOVEMENTTYPE = "";
        GetGLTMCCTYPID = 0;
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT GLTMID, IDCATALOGO, GLTMLINEID, GLTMREF, GLTMMEMO, AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, GLTMMEMODET, GLTMAMOUNT, GLTMNOMAMT, GLTMBASAMT, GLTMCURRENCY, GLTMEXCHANGE, MOVEMENTTYPE, GLTMCCTYPID, GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5, CreatedBy, CreatedOn, CreatedFromIP, ModifiedBy, ModifiedOn, ModifiedFromIP, CompanyId FROM IBGLTDPST WHERE GLTMID=" + GLTMID + " AND GLTMLINEID=" + GLTMLINEID + " ");
            if (rs.next()) {
                existe = true;
                GetIdCatalogo = rs.getInt(2);
                GetGLTMMEMODET = rs.getString(12);
                GetGLTMAMOUNT = rs.getDouble(13);
                GetMOVEMENTTYPE = rs.getString(18);
                GetGLTMCCTYPID = rs.getInt(19);
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA OBTENER LOS DATOS DE LA LINEA DEL COMPROBANTE QUE SE ESTA EDITANDO.
    public boolean GetDetailLineBATCH(int IdComprobante, int GLBACHLINEID) {
        existe = false;
        GetIdCatalogo = 0;
        GetGLBACHMEMODET = "";
        GetGLBACHAMOUNT = 0.00;
        GetGLBACHMOVEMENTTYPE = "";
        GetGLBACHCCTYPID = 0;
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT IdComprobante, GLTMID, IDCATALOGO, GLBACHLINEID, GLBACHMREF, GLBACHMEMO, GLBACHDATE, AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, GLBACHMEMODET, GLBACHAMOUNT, GLBACHNOMAMT, GLBACHBASAMT, GLBACHCURRENCY, GLBACHEXCHANGE, GLBACHMOVEMENTTYPE, GLBACHCCTYPID, GLBACHCCID1, GLBACHCCID2, GLBACHCCID3, GLBACHCCID4, GLBACHCCID5, GLBACHSTATUS FROM IBGLBATCHDST WHERE IdComprobante =" + IdComprobante + " AND GLBACHLINEID=" + GLBACHLINEID + " ");
            if (rs.next()) {
                existe = true;
                GetIdCatalogo = rs.getInt(3);
                GetGLBACHMEMODET = rs.getString(14);
                GetGLBACHAMOUNT = rs.getDouble(15);
                GetGLBACHMOVEMENTTYPE = rs.getString(20);
                GetGLBACHCCTYPID = rs.getInt(21);
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA ELIMINAR LINEA DEL COMPROBANTE CONTABLE
    public boolean DeleteLineBATCH(int IdComprobante, int GLBACHLINEID, int IDCATALOGO) {
        existe = false;
        try {
            ConexionDB conn = new ConexionDB();
            conn.Conectar();
            st = conn.conexion.createStatement();
            pst = conn.Conectar().prepareStatement("DELETE FROM IBGLBATCHDST WHERE IdComprobante=" + IdComprobante + " AND GLBACHLINEID=" + GLBACHLINEID + " AND IDCATALOGO=" + IDCATALOGO + "");
            pst.executeUpdate();
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA OBTENER EL CONTEO DE NUMERO DE COMPROBANTES
    public boolean GetCountIdComprobante() {
        existe = false;
        GetCurrentIdComprobante = 0;
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            //rs = st.executeQuery("SELECT COUNT(*)+1 IdComprobante FROM IBGLBATCHDST");
            rs = st.executeQuery("SELECT DISTINCT IdComprobante FROM IBGLBATCHDST ORDER BY IdComprobante DESC LIMIT 1");
            if (rs.next()) {
                existe = true;
                GetCurrentIdComprobante = rs.getInt("IdComprobante") + 1;
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA OBTENER EL CONTEO DE LINEAS DE UN COMPROBANTE
    public boolean GetCountLineBach(int IdComprobante) {
        existe = false;
        GetCurrentLineIdComp = 0;
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT DISTINCT GLBACHLINEID FROM IBGLBATCHDST WHERE IdComprobante=" + IdComprobante + " ORDER BY GLBACHLINEID DESC LIMIT 1");
            if (rs.next()) {
                existe = true;
                GetCurrentLineIdComp = rs.getInt("GLBACHLINEID") + 1;
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA OBTENER EL CONTEO DE NUMERO DE PLANTILLAS
    public boolean GetCounGLTMID() {
        existe = false;
        GetCurrentGLTMID = 0;
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            //rs = st.executeQuery("SELECT COUNT(*)+1 GLTMID FROM IBGLTDPST");
            rs = st.executeQuery("SELECT DISTINCT GLTMID FROM IBGLTDPST ORDER BY GLTMID DESC LIMIT 1");
            if (rs.next()) {
                existe = true;
                GetCurrentGLTMID = rs.getInt("GLTMID") + 1;
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

    // FUNCION PARA OBTENER EL CONTEO DE LINEAS DE LA PLANTILLA
    public boolean GetCountLineID(int GLTMID) {
        existe = false;
        GetCurrentGLTMLINEID = 0;
        try {
            conn = this.Conectar();
            st = conn.createStatement();
            //rs = st.executeQuery("SELECT COUNT(*)+1 GLTMLINEID FROM IBGLTDPST WHERE GLTMID=" + GLTMID + " ");
            rs = st.executeQuery("SELECT DISTINCT GLTMLINEID FROM IBGLTDPST WHERE GLTMID=" + GLTMID + " ORDER BY GLTMLINEID DESC LIMIT 1");
            if (rs.next()) {
                existe = true;
                GetCurrentGLTMLINEID = rs.getInt("GLTMLINEID") + 1;
            }
            this.Cerrar(); // CIERRO LA CONEXION A LA BASE DE DATOS
            rs.close(); //CIERRO LA CONEXION DEL RESULSET.
            pst.close(); //CIERRO EL PREPARED STATEMENT
        } catch (Exception e) {

        }
        return existe;
    }

}
