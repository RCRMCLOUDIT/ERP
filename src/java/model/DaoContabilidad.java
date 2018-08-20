package model;

import beans.ConexionDB;
import java.sql.CallableStatement;
import java.sql.Connection;
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
            GetAccountNumber, GetAccountName, GetIsacctaccessible, GetActive, GetInactivityDate, GetComments;
    public static int GetIdCatalogo;
    public static Double CurrentBalance;

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
            cs = conn.Conectar().prepareCall("{ CALL GLUPDTYPACC (?,?,?,?,?) }");
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
            this.Cerrar();
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
            this.Cerrar();
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
            this.Cerrar();
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
            this.Cerrar();
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
            this.Cerrar();
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
            this.Cerrar();
        } catch (Exception e) {

        }
        return existe;
    }

    //FUNCION PARA BUSCAR LOS DATOS DE LA CUENTA CONTABLE
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
            this.Cerrar();
        } catch (Exception e) {

        }
        return existe;
    }
}