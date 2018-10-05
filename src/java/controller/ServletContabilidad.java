package controller;

import beans.ConexionDB;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DaoContabilidad;

/**
 *
 * @author Ing. Moises Romero Mojica
 */
public class ServletContabilidad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL = ""; // ESTA VARIABLE SERA PARA MANEJAR LA URL
        String Accion = request.getParameter("form-Accion");
        int Msg = 0; // ESTA VARIABLE SERA PARA CONTROLAR LOS MENSAJES DE ERROR Y ENVIARLO A LA VISTA

        //---------------------------------------------------------------------//
        // IF PARA AGREGAR UN NUEVO TIPO DE CUENTA CONTABLE--------------------//
        //---------------------------------------------------------------------//
        if (Accion.equals("AddTipoCta")) {
            URL = "";
            int CompanyId = 1;
            int GLTPCLSID = Integer.valueOf(request.getParameter("form-NumeroCuenta"));
            String GLTPNAME = request.getParameter("form-NombreCuenta");
            String GLTPACCID = request.getParameter("form-TipoCuenta");
            DaoContabilidad datos = new DaoContabilidad();
            datos.VerifarNombre(GLTPNAME);
            String GetGLTPNAME = datos.GetGLTPNAME;

            datos.VerificarCod(GLTPCLSID);
            int GetGLTPCLSID = datos.GetGLTPCLSID;
            if (GLTPNAME.equals(GetGLTPNAME)) {
                Msg = 1;
                //MENSAJE = 1 EL NOMBRE DE CUENTA YA EXISTE
                URL = "Contabilidad/AgregarTipoCuenta.jsp?NumeroCuenta=" + GLTPCLSID + "&NombreCuenta=" + GLTPNAME + "&Tipo=" + GLTPACCID + "&Msg=" + Msg;

            } else if (GLTPCLSID == GetGLTPCLSID) {
                Msg = 2;
                //MENSAJE = 2 EL CODIGO DE CUENTA YA EXISTE
                URL = "Contabilidad/AgregarTipoCuenta.jsp?NumeroCuenta=" + GLTPCLSID + "&NombreCuenta=" + GLTPNAME + "&Tipo=" + GLTPACCID + "&Msg=" + Msg;
            } else {
                try {
                    datos.GLADDTYPACC(CompanyId, GLTPCLSID, GLTPNAME, GLTPACCID, "Moises Romero", "");
                    URL = "Contabilidad/TiposCuenta.jsp";
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            response.sendRedirect(URL);
        }//FIN DEL IF PARA AGREGAR UN NUEVO TIPO DE CUENTA CONTABLE

        //---------------------------------------------------------------------//
        // IF PARA ACTUALIZAR UN TIPO DE CUENTA CONTABLE
        //---------------------------------------------------------------------//
        if (Accion.equals("UpdateTipoCta")) {
            int CompanyId = 1;
            int GLTPCLSID = Integer.valueOf(request.getParameter("form-NumeroCuenta"));
            String GLTPNAME = request.getParameter("form-NombreCuenta");
            String GLTPACCID = request.getParameter("form-TipoCuenta");
            if (GLTPACCID.equals("Activo")) {
                GLTPACCID = "A";
            }
            if (GLTPACCID.equals("Pasivo")) {
                GLTPACCID = "P";
            }
            if (GLTPACCID.equals("Capital")) {
                GLTPACCID = "C";
            }
            if (GLTPACCID.equals("Ingresos")) {
                GLTPACCID = "I";
            }
            if (GLTPACCID.equals("Gastos")) {
                GLTPACCID = "G";
            }
            DaoContabilidad datos = new DaoContabilidad();
            datos.VerifarNombre(GLTPNAME);
            //TRABAJAR MEJORAR ESTA PARTE PARA HACER BIEN LA ACTUALIZACION DE DATOS. DE TIPO DE CUENTA
            int GetGLTPCLSID = datos.GetGLTPCLSID;
            if (GLTPCLSID == GetGLTPCLSID) {
                try {
                    datos.GLUPDTYPACC(GLTPCLSID, GLTPNAME, GLTPACCID, "Moises Romero", "192.168.0.1");
                    URL = "Contabilidad/TiposCuenta.jsp";
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                Msg = 1;
                //MENSAJE = 1 EL NOMBRE DE CUENTA YA EXISTE
                URL = "Contabilidad/ModificarTipoCuenta.jsp?GLTPCLSID=" + GLTPCLSID + "&Msg=" + Msg;
            }
            response.sendRedirect(URL);
        }//FIN DEL IF PARA ACTUALIZAR UN TIPO DE CUENTA CONTABLE

        //---------------------------------------------------------------------//
        // IF PARA REGISTRAR UNA CUENTA EN EL CATALOGO CONTABLE
        //---------------------------------------------------------------------//
        if (Accion.equals("AddCtaContable")) {
            //RECUPERO LOS PARAMETROS DEL JSP
            int IdTipoCta = Integer.valueOf(request.getParameter("form-TipoCuenta"));
            String NombreCta = request.getParameter("form-NombreCuenta");
            String NumeroCta = request.getParameter("form-NumeroCuenta");
            String Descripcion = request.getParameter("form-Descripcion");
            int SubCta = 0;
            if (request.getParameter("form-CuentaPadre") != null) {
                SubCta = Integer.valueOf(request.getParameter("form-CuentaPadre"));
            }
            int GetLevel1, GetLevel2, GetLevel3, GetLevel4, GetLevel5, GetLevel6;

            DaoContabilidad datos = new DaoContabilidad();
            //MANDO A VERIFICAR SI EL NOMBRE DE CUENTA CONTABLE YA EXISTE
            datos.CheckNameIBGLACCNTS(NombreCta);
            String GetName = datos.GetAccountName;
            //MANDO A VERIFICAR SI EL NUMERO DE CUENTA CONTABLE YA EXISTE
            datos.CheckNumberIBGLACCNTS(NumeroCta);
            String GetNumber = datos.GetAccountNumber;
            if (NombreCta.equals(GetName)) {
                //MENSAJE = 1 EL NOMBRE DE CUENTA YA EXISTE
                Msg = 1;
                URL = "Contabilidad/AgregaCuentaContable.jsp?Msg=" + Msg + "&IdTipoCuenta=" + IdTipoCta + "&Name=" + NombreCta + "&Num=" + NumeroCta + "&Desc=" + Descripcion + "&SubC=" + SubCta;
                //response.sendRedirect("Contabilidad/AgregaCuentaContable.jsp?Msg=" + Msg + "&IdTipoCuenta=" + IdTipoCta + "&Name=" + NombreCta + "&Num=" + NumeroCta + "&Desc=" + Descripcion);
            } else if (NumeroCta.equals(GetNumber)) {
                //MENSAJE = 2 EL NUMERO DE CUENTA YA EXISTE
                Msg = 2;
                URL = "Contabilidad/AgregaCuentaContable.jsp?Msg=" + Msg + "&IdTipoCuenta=" + IdTipoCta + "&Name=" + NombreCta + "&Num=" + NumeroCta + "&Desc=" + Descripcion + "&SubC=" + SubCta;
                //response.sendRedirect("Contabilidad/AgregaCuentaContable.jsp?Msg=" + Msg + "&IdTipoCuenta=" + IdTipoCta + "&Name=" + NombreCta + "&Num=" + NumeroCta + "&Desc=" + Descripcion);
            } else if (IdTipoCta == 0) {
                Msg = 3;
                URL = "Contabilidad/AgregaCuentaContable.jsp?Msg=" + Msg + "&IdTipoCuenta=" + IdTipoCta + "&Name=" + NombreCta + "&Num=" + NumeroCta + "&Desc=" + Descripcion + "&SubC=" + SubCta;
            } else {
                //SI SUB-CTA = 0; QUIERE DECIR QUE SERA UNA CUENTA DE NIVEL 1.
                if (SubCta == 0) {
                    //MANDO A BUSCAR EL NUMERO ACTUAL DE NUMERACION DEL NIVEL
                    datos.GetCountLevel();
                    //RECUPERO EL CONTEO QUE LLEVA EL NIVEL 1
                    GetLevel1 = Integer.valueOf(datos.GetAccountLevel1) + 1;
                    try {
                        //MANDO A GUARDAR EL REGISTRO PARA GUARDAR LA CUENTA CONTABLE EL LA TABLA 'IBGLACCNTS'
                        datos.GLADDACNTS(String.valueOf(GetLevel1), "000", "000", "000", "000", "000",
                                NumeroCta, NombreCta, Descripcion, "Moises Romero", "", IdTipoCta, 1);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    //REDIRECCIONO A LA PAGINA DEL CATALOGO CONTABLE
                    //response.sendRedirect("Contabilidad/CatalogoContable.jsp");
                    URL = "Contabilidad/CatalogoContable.jsp";
                }
                //SI SUB-CTA > 0; QUIERE DECIR QUE SERA UNA CUENTA DE NIVEL 2,3,4,5,6
                if (SubCta > 0) {
                    //PRIMERO MANDO A RECUPERAR LA NUMERACION DE NIVEL QUE TIENE LA CUENTA PADRE
                    datos.BuscarIBGLACCNTS(SubCta);
                    GetLevel1 = Integer.valueOf(datos.GetAccountLevel1);
                    GetLevel2 = Integer.valueOf(datos.GetAccountLevel2);
                    GetLevel3 = Integer.valueOf(datos.GetAccountLevel3);
                    GetLevel4 = Integer.valueOf(datos.GetAccountLevel4);
                    GetLevel5 = Integer.valueOf(datos.GetAccountLevel5);
                    GetLevel6 = Integer.valueOf(datos.GetAccountLevel6);
                    int NewNivel = 0;
                    //AHORA MANDO VERIFICAR QUE NIVEL TIENE LA QUE SERA LA CUENTA PADRE DE LA NUEVA CUENTA CONTABLE
                    if (GetLevel1 > 0 && GetLevel2 == 0 && GetLevel3 == 0) {
                        NewNivel = 2; //LA CUENTA SERA DE NIVEL 2
                    }
                    if (GetLevel2 > 0 && GetLevel3 == 0 && GetLevel4 == 0) {
                        NewNivel = 3; //LA CUENTA SERA DE NIVEL 3
                    }
                    if (GetLevel3 > 0 && GetLevel4 == 0 && GetLevel5 == 0) {
                        NewNivel = 4; //LA CUENTA SERA DE NIVEL 4
                    }
                    if (GetLevel4 > 0 && GetLevel5 == 0 && GetLevel6 == 0) {
                        NewNivel = 5; //LA CUENTA SERA DE NIVEL 5
                    }
                    if (GetLevel5 > 0 && GetLevel6 == 0) {
                        NewNivel = 6; //LA CUENTA SERA DE NIVEL 6
                    }
                    switch (NewNivel) {
                        // EN CASO DE QUE SERA DE NIVEL 2 MANDO A RECUPERAR EL CONTEO DE CUENTA DE ESE NIVEL Y LE SUMO +1 Y LO GUARDO EN LA BASE DE DATOS.
                        case 2:
                            GetLevel2 = 0;
                            datos.GetCountLevel();
                            GetLevel2 = Integer.valueOf(datos.GetAccountLevel2) + 1;
                            try {
                                datos.GLADDACNTS(String.valueOf(GetLevel1), String.valueOf(GetLevel2), "000", "000", "000", "000",
                                        NumeroCta, NombreCta, Descripcion, "Moises Romero", "", IdTipoCta, 1);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            break;
                        // EN CASO DE QUE SERA DE NIVEL 3 MANDO A RECUPERAR EL CONTEO DE CUENTA DE ESE NIVEL Y LE SUMO +1 Y LO GUARDO EN LA BASE DE DATOS.
                        case 3:
                            GetLevel3 = 0;
                            datos.GetCountLevel();
                            GetLevel3 = Integer.valueOf(datos.GetAccountLevel3) + 1;
                            try {
                                datos.GLADDACNTS(String.valueOf(GetLevel1), String.valueOf(GetLevel2), String.valueOf(GetLevel3), "000", "000", "000",
                                        NumeroCta, NombreCta, Descripcion, "Moises Romero", "", IdTipoCta, 1);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            break;
                        // EN CASO DE QUE SERA DE NIVEL 4 MANDO A RECUPERAR EL CONTEO DE CUENTA DE ESE NIVEL Y LE SUMO +1 Y LO GUARDO EN LA BASE DE DATOS.
                        case 4:
                            GetLevel4 = 0;
                            datos.GetCountLevel();
                            GetLevel4 = Integer.valueOf(datos.GetAccountLevel4) + 1;
                            try {
                                datos.GLADDACNTS(String.valueOf(GetLevel1), String.valueOf(GetLevel2), String.valueOf(GetLevel3), String.valueOf(GetLevel4),
                                        "000", "000", NumeroCta, NombreCta, Descripcion, "Moises Romero", "", IdTipoCta, 1);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            break;
                        // EN CASO DE QUE SERA DE NIVEL 5 MANDO A RECUPERAR EL CONTEO DE CUENTA DE ESE NIVEL Y LE SUMO +1 Y LO GUARDO EN LA BASE DE DATOS.
                        case 5:
                            GetLevel5 = 0;
                            datos.GetCountLevel();
                            GetLevel5 = Integer.valueOf(datos.GetAccountLevel5) + 1;
                            try {
                                datos.GLADDACNTS(String.valueOf(GetLevel1), String.valueOf(GetLevel2), String.valueOf(GetLevel3), String.valueOf(GetLevel4),
                                        String.valueOf(GetLevel5), "000", NumeroCta, NombreCta, Descripcion, "Moises Romero", "", IdTipoCta, 1);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            break;
                        // EN CASO DE QUE SERA DE NIVEL 6 MANDO A RECUPERAR EL CONTEO DE CUENTA DE ESE NIVEL Y LE SUMO +1 Y LO GUARDO EN LA BASE DE DATOS.
                        case 6:
                            GetLevel6 = 0;
                            datos.GetCountLevel();
                            GetLevel6 = Integer.valueOf(datos.GetAccountLevel6) + 1;
                            try {
                                datos.GLADDACNTS(String.valueOf(GetLevel1), String.valueOf(GetLevel2), String.valueOf(GetLevel3), String.valueOf(GetLevel4),
                                        String.valueOf(GetLevel5), String.valueOf(GetLevel6), NumeroCta, NombreCta, Descripcion, "Moises Romero", "", IdTipoCta, 1);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            break;
                    }
                    //REDIRECCIONO A LA PAGINA DEL CATALOGO CONTABLE
                    //response.sendRedirect("Contabilidad/CatalogoContable.jsp");
                    URL = "Contabilidad/CatalogoContable.jsp";
                }
            }
            response.sendRedirect(URL);
        } //FIN DEL IF PARA REGISTRAR UNA CUENTA EN EL CATALOGO CONTABLE

        //---------------------------------------------------------------------//
        // IF PARA ACTUALIZAR UNA CUENTA EN EL CATALOGO CONTABLE
        //---------------------------------------------------------------------//
        if (Accion.equals("UpdateCtaContable")) {
            int CompanyId = 1;
            //RECUPERO LOS PARAMETROS DEL JSP
            int IdCatalogo = Integer.valueOf(request.getParameter("form-IdCatalogo"));
            String NombreCta = request.getParameter("form-NombreCuenta");
            String NumeroCta = request.getParameter("form-NumeroCuenta");
            String Descripcion = request.getParameter("form-Descripcion");
            DaoContabilidad datos = new DaoContabilidad();
            int GetIdCatalogo = 0;// ESTA VARIABLE ME SERVIRA PARA RECUPERAR EL ID DE LA CUENTA QUE TIENE EL MISMO NOMBRE Ó NUMERO
            datos.CheckNameIBGLACCNTS(NombreCta); // MANDO A VERIFICAR SI HAY COINCIDENCIA CON EL NOMBRE DE LA CUENTA
            GetIdCatalogo = datos.GetIdCatalogo;
            if (IdCatalogo != GetIdCatalogo && GetIdCatalogo > 0) {
                //MENSAJE = 1 EL NOMBRE DE CUENTA YA EXISTE
                Msg = 1;
                //response.sendRedirect("Contabilidad/EditarCuentaContable.jsp?IDCATALOGO=" + IdCatalogo + "&Msg=" + Msg);
                URL = "Contabilidad/EditarCuentaContable.jsp?IDCATALOGO=" + IdCatalogo + "&Msg=" + Msg;
            } else {
                datos.CheckNumberIBGLACCNTS(NumeroCta); // MANDO A VERIFICAR SI HAY COINCIDENCIA CON EL NUMERO DE LA CUENTA
                GetIdCatalogo = datos.GetIdCatalogo;
                if (IdCatalogo != GetIdCatalogo && GetIdCatalogo > 0) {
                    //MENSAJE = 1 EL NUMERO DE CUENTA YA EXISTE
                    Msg = 2;
                    //response.sendRedirect("Contabilidad/EditarCuentaContable.jsp?IDCATALOGO=" + IdCatalogo + "&Msg=" + Msg);
                    URL = "Contabilidad/EditarCuentaContable.jsp?IDCATALOGO=" + IdCatalogo + "&Msg=" + Msg;
                } else {
                    try {
                        datos.GLUPDACNTS(IdCatalogo, NumeroCta, NombreCta, Descripcion, "Moises Romero", "", CompanyId);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    //response.sendRedirect("Contabilidad/CatalogoContable.jsp");
                    URL = "Contabilidad/CatalogoContable.jsp";
                }
            }
            response.sendRedirect(URL);
        }//FIN DEL IF PARA ACTUAIZAR UNA CUENTA EN EL CATALOGO CONTABLE

        //---------------------------------------------------------------------//
        //--------- IF PARA AGREGAR DATOS A  PLANTILLA CONTABLE ---------------//
        //---------------------------------------------------------------------//
        if (Accion.equals("AddPlantillaComprobante")) {
            int IdPlantilla = Integer.valueOf(request.getParameter("form-IdPlantilla"));
            String Descripcion = request.getParameter("form-DescripcionPlantilla");
            int IdCatalago = Integer.valueOf(request.getParameter("form-CtaContable"));
            DaoContabilidad datos = new DaoContabilidad();
            if (IdCatalago > 0) {
                if (IdPlantilla == 0) {
                    // SI EL ID PLANTILLA ES IGUAL A 0 QUIERE DECIR QUE ES UNA NUEVA PLANTILLA
                    String Referencia = "";
                    String AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6;
                    datos.BuscarIBGLACCNTS(IdCatalago);
                    AccountLevel1 = datos.GetAccountLevel1;
                    AccountLevel2 = datos.GetAccountLevel2;
                    AccountLevel3 = datos.GetAccountLevel3;
                    AccountLevel4 = datos.GetAccountLevel4;
                    AccountLevel5 = datos.GetAccountLevel5;
                    AccountLevel6 = datos.GetAccountLevel6;
                    String ComentLinea = request.getParameter("form-DescLinea");
                    Double MontoTranCurr = Double.valueOf(request.getParameter("form-Monto"));
                    Double MontoCompCurre = Double.valueOf(request.getParameter("form-Monto"));
                    Double MontoBaseCurre = Double.valueOf(request.getParameter("form-Monto"));
                    int TypeCurrency = 0;
                    Double ExchangeRate = 0.00;
                    String TypeMov = request.getParameter("form-TipoMov");
                    int IdTypeCC = Integer.valueOf(request.getParameter("form-TipoCC"));
                    int IdCtaCC = Integer.valueOf(request.getParameter("form-CtaCentroCost"));
                    Double GLTMCCID1 = 0.00, GLTMCCID2 = 0.00, GLTMCCID3 = 0.00, GLTMCCID4 = 0.00, GLTMCCID5 = 0.00;
                    String CreatedBy = "Moises Romero", CreatedFromIP = "192.168.1.10";
                    int CompanyId = 1;
                    datos.GetCounGLTMID();
                    IdPlantilla = datos.GetCurrentGLTMID;
                    int LineId = 1;
                    try {
                        datos.GLADDTPDST(IdPlantilla, IdCatalago, LineId, Referencia, Descripcion, AccountLevel1, AccountLevel2, AccountLevel3,
                                AccountLevel4, AccountLevel5, AccountLevel6, ComentLinea, MontoTranCurr, MontoCompCurre, MontoBaseCurre,
                                TypeCurrency, ExchangeRate, TypeMov, IdTypeCC, GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5,
                                CreatedBy, CreatedFromIP, CompanyId);
                    } catch (Exception e) {
                    }
                    URL = "Contabilidad/AddPlantillaContable.jsp?IdPlantilla=" + IdPlantilla + "&DescPlant=" + Descripcion;
                } else {
                    // SI EL ID PLANTILLA NO ES IGUAL A 0 QUIERE DECIR QUE ES UNA PLANTILLA QUE YA EXISTE
                    String Referencia = "";
                    String AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6;
                    datos.BuscarIBGLACCNTS(IdCatalago);
                    AccountLevel1 = datos.GetAccountLevel1;
                    AccountLevel2 = datos.GetAccountLevel2;
                    AccountLevel3 = datos.GetAccountLevel3;
                    AccountLevel4 = datos.GetAccountLevel4;
                    AccountLevel5 = datos.GetAccountLevel5;
                    AccountLevel6 = datos.GetAccountLevel6;
                    String ComentLinea = request.getParameter("form-DescLinea");
                    Double MontoTranCurr = Double.valueOf(request.getParameter("form-Monto"));
                    Double MontoCompCurre = Double.valueOf(request.getParameter("form-Monto"));
                    Double MontoBaseCurre = Double.valueOf(request.getParameter("form-Monto"));
                    int TypeCurrency = 0;
                    Double ExchangeRate = 0.00;
                    String TypeMov = request.getParameter("form-TipoMov");
                    int IdTypeCC = Integer.valueOf(request.getParameter("form-TipoCC"));
                    int IdCtaCC = Integer.valueOf(request.getParameter("form-CtaCentroCost"));
                    Double GLTMCCID1 = 0.00, GLTMCCID2 = 0.00, GLTMCCID3 = 0.00, GLTMCCID4 = 0.00, GLTMCCID5 = 0.00;
                    String CreatedBy = "Moises Romero", CreatedFromIP = "192.168.1.10";
                    int CompanyId = 1;
                    datos.GetCountLineID(IdPlantilla);
                    int LineId = datos.GetCurrentGLTMLINEID;
                    try {
                        datos.GLADDTPDST(IdPlantilla, IdCatalago, LineId, Referencia, Descripcion, AccountLevel1, AccountLevel2, AccountLevel3,
                                AccountLevel4, AccountLevel5, AccountLevel6, ComentLinea, MontoTranCurr, MontoCompCurre, MontoBaseCurre,
                                TypeCurrency, ExchangeRate, TypeMov, IdTypeCC, GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5,
                                CreatedBy, CreatedFromIP, CompanyId);
                        datos.UpdateHeaderNameTMP(IdPlantilla, Descripcion);
                    } catch (Exception e) {
                    }
                    URL = "Contabilidad/AddPlantillaContable.jsp?IdPlantilla=" + IdPlantilla + "&DescPlant=" + Descripcion;
                }
            } else {
                datos.UpdateHeaderNameTMP(IdPlantilla, Descripcion);
                URL = "Contabilidad/AddPlantillaContable.jsp?IdPlantilla=" + IdPlantilla + "&DescPlant=" + Descripcion;
            }

            response.sendRedirect(URL);
        }// FIN DEL IF PARA GUARDAR UNA PLANTILLA Y SUS LINEAS

        //----------------------------------------------------------------------------------//
        //--------- IF PARA ACTUALIZAR UNA LINEA DE LA PLANTILLA CONTABLE -------------------//
        //----------------------------------------------------------------------------------//        
        if (Accion.equals("UpdateLineTemplate")) {
            DaoContabilidad datos = new DaoContabilidad();
            int IdPlantilla = Integer.valueOf(request.getParameter("form-IdPlantilla"));
            int LineId = Integer.valueOf(request.getParameter("form-IdLinea"));
            String Descripcion = request.getParameter("form-DescripcionPlantilla");
            int IdCatalago = Integer.valueOf(request.getParameter("form-CtaContable"));
            String AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6;
            datos.BuscarIBGLACCNTS(IdCatalago);
            AccountLevel1 = datos.GetAccountLevel1;
            AccountLevel2 = datos.GetAccountLevel2;
            AccountLevel3 = datos.GetAccountLevel3;
            AccountLevel4 = datos.GetAccountLevel4;
            AccountLevel5 = datos.GetAccountLevel5;
            AccountLevel6 = datos.GetAccountLevel6;
            String ComentLinea = request.getParameter("form-DescLinea");
            Double MontoTranCurr = Double.valueOf(request.getParameter("form-Monto"));
            Double MontoCompCurre = Double.valueOf(request.getParameter("form-Monto"));
            Double MontoBaseCurre = Double.valueOf(request.getParameter("form-Monto"));
            int TypeCurrency = 0;
            Double ExchangeRate = 0.00;
            String TypeMov = request.getParameter("form-TipoMov");
            int IdTypeCC = Integer.valueOf(request.getParameter("form-TipoCC"));
            int IdCtaCC = Integer.valueOf(request.getParameter("form-CtaCentroCost"));
            Double GLTMCCID1 = 0.00, GLTMCCID2 = 0.00, GLTMCCID3 = 0.00, GLTMCCID4 = 0.00, GLTMCCID5 = 0.00;
            String ModifiedBy = "Moises Romero", ModifiedFromIP = "192.168.1.10";
            int CompanyId = 1;
            try {
                datos.UpdateGLTMLINEID(IdPlantilla, IdCatalago, LineId, AccountLevel1, AccountLevel2, AccountLevel3,
                        AccountLevel4, AccountLevel5, AccountLevel6, ComentLinea, MontoTranCurr, MontoCompCurre, MontoBaseCurre,
                        TypeCurrency, ExchangeRate, TypeMov, IdTypeCC, GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5,
                        ModifiedBy, ModifiedFromIP, CompanyId);
            } catch (Exception e) {
            }
            URL = "Contabilidad/AddPlantillaContable.jsp?IdPlantilla=" + IdPlantilla + "&DescPlant=" + Descripcion;
            response.sendRedirect(URL);
        }/////----------FINAL IF PARA ACTUALIZAR UNA LINEA DE LA PLANTILLAS CONTABLE----------/////////

        //----------------------------------------------------------------------------------//
        //--------- IF PARA ELIMINAR UNA LINEA DE LA PLANTILLA CONTABLE -------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("DeleteLineTemplate")) {
            int IdPlantilla = Integer.valueOf(request.getParameter("form-IdPlantilla"));
            int LineId = Integer.valueOf(request.getParameter("form-IdLinea"));
            int IdCatalogo = Integer.valueOf(request.getParameter("form-IdCatalogo"));
            String Descripcion = request.getParameter("form-DescripcionPlantilla");
            try {
                DaoContabilidad datos = new DaoContabilidad();
                datos.DeleteGLTMLINEID(IdPlantilla, LineId, IdCatalogo);
            } catch (Exception e) {
            }
            URL = "Contabilidad/AddPlantillaContable.jsp?IdPlantilla=" + IdPlantilla + "&DescPlant=" + Descripcion;
            response.sendRedirect(URL);
        }//FIN DEL IF PARA ELIMINAR UNA LINEA DE LA PLANTILLA CONTABLE

        //----------------------------------------------------------------------------------//
        //---------------- IF PARA ELIMINAR LA PLANTILLA CONTABLE --------------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("DeletePlantilla")) {
            int IdPlantilla = Integer.valueOf(request.getParameter("form-IdPlantilla"));
            try {
                DaoContabilidad datos = new DaoContabilidad();
                datos.DeletePlantilla(IdPlantilla);

            } catch (Exception e) {
            }
            URL = "Contabilidad/PlantillaComprobante.jsp";
            response.sendRedirect(URL);
        }//FIN DEL IF PARA ELIMINAR UNA LINEA DE LA PLANTILLA CONTABLE

        //----------------------------------------------------------------------------------//
        //---------------- IF PARA AGREGAR COMPROBANTE CONTABLE ----------------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("AddComprobanteContable")) {
            int IdComprobante = Integer.valueOf(request.getParameter("form-IdComprobante"));
            int IdPlantilla = Integer.valueOf(request.getParameter("form-IdPlantilla"));
            String Descripcion = request.getParameter("form-DescripcionComprobante");
            String NumeroReferencia = request.getParameter("form-NumeroReferencia");
            String Fecha = request.getParameter("Fecha");
            DaoContabilidad datos = new DaoContabilidad();
            //datos.UpdateHeaderNameTMP(IdPlantilla, Descripcion);
            if (IdComprobante == 0) {
                // SI EL ID COMPROBANTE ES IGUAL A 0 QUIERE DECIR QUE ES UN NUEVO COMPROBANTE
                int IdCatalago = Integer.valueOf(request.getParameter("form-CtaContable"));
                String AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6;
                datos.BuscarIBGLACCNTS(IdCatalago);
                AccountLevel1 = datos.GetAccountLevel1;
                AccountLevel2 = datos.GetAccountLevel2;
                AccountLevel3 = datos.GetAccountLevel3;
                AccountLevel4 = datos.GetAccountLevel4;
                AccountLevel5 = datos.GetAccountLevel5;
                AccountLevel6 = datos.GetAccountLevel6;
                String ComentLinea = request.getParameter("form-DescLinea");
                Double MontoTranCurr = Double.valueOf(request.getParameter("form-Monto"));
                Double MontoCompCurre = Double.valueOf(request.getParameter("form-Monto"));
                Double MontoBaseCurre = Double.valueOf(request.getParameter("form-Monto"));
                int TypeCurrency = 0;
                Double ExchangeRate = 0.00;
                String TypeMov = request.getParameter("form-TipoMov");
                int IdTypeCC = Integer.valueOf(request.getParameter("form-TipoCC"));
                int IdCtaCC = Integer.valueOf(request.getParameter("form-CtaCentroCost"));
                Double GLTMCCID1 = 0.00, GLTMCCID2 = 0.00, GLTMCCID3 = 0.00, GLTMCCID4 = 0.00, GLTMCCID5 = 0.00;
                String CreatedBy = "Moises Romero", CreatedFromIP = "192.168.1.10";
                int CompanyId = 1;
                datos.GetCountIdComprobante();
                IdComprobante = datos.GetCurrentIdComprobante;
                int LineId = 1;
                URL = "Contabilidad/AgregarComprobante.jsp?IdComprobante=" + IdComprobante + "&DescComp=" + Descripcion + "&RefNum=" + NumeroReferencia + "&Fecha=" + Fecha;
                try {
                    datos.GLADDBACH(IdComprobante, IdPlantilla, IdCatalago, LineId, NumeroReferencia, Descripcion, Fecha,
                            AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, ComentLinea,
                            MontoTranCurr, MontoCompCurre, MontoBaseCurre, TypeCurrency, ExchangeRate, TypeMov, IdTypeCC,
                            GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5, CreatedBy, CreatedFromIP, CompanyId);
                } catch (Exception e) {
                }

            } else {
                // SI EL ID COMPROBANTE NO ES IGUAL A 0 QUIERE DECIR QUE ES UN COMPROBANTE QUE YA EXISTE
                int IdCatalago = Integer.valueOf(request.getParameter("form-CtaContable"));
                String AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6;
                datos.BuscarIBGLACCNTS(IdCatalago);
                AccountLevel1 = datos.GetAccountLevel1;
                AccountLevel2 = datos.GetAccountLevel2;
                AccountLevel3 = datos.GetAccountLevel3;
                AccountLevel4 = datos.GetAccountLevel4;
                AccountLevel5 = datos.GetAccountLevel5;
                AccountLevel6 = datos.GetAccountLevel6;
                String ComentLinea = request.getParameter("form-DescLinea");
                Double MontoTranCurr = Double.valueOf(request.getParameter("form-Monto"));
                Double MontoCompCurre = Double.valueOf(request.getParameter("form-Monto"));
                Double MontoBaseCurre = Double.valueOf(request.getParameter("form-Monto"));
                int TypeCurrency = 0;
                Double ExchangeRate = 0.00;
                String TypeMov = request.getParameter("form-TipoMov");
                int IdTypeCC = Integer.valueOf(request.getParameter("form-TipoCC"));
                int IdCtaCC = Integer.valueOf(request.getParameter("form-CtaCentroCost"));
                Double GLTMCCID1 = 0.00, GLTMCCID2 = 0.00, GLTMCCID3 = 0.00, GLTMCCID4 = 0.00, GLTMCCID5 = 0.00;
                String CreatedBy = "Moises Romero", CreatedFromIP = "192.168.1.10";
                int CompanyId = 1;
                datos.GetCountLineBach(IdComprobante);
                int LineId = datos.GetCurrentLineIdComp;
                URL = "Contabilidad/AgregarComprobante.jsp?IdComprobante=" + IdComprobante + "&DescComp=" + Descripcion + "&RefNum=" + NumeroReferencia + "&Fecha=" + Fecha;
                try {
                    datos.GLADDBACH(IdComprobante, IdPlantilla, IdCatalago, LineId, NumeroReferencia, Descripcion, Fecha,
                            AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6, ComentLinea,
                            MontoTranCurr, MontoCompCurre, MontoBaseCurre, TypeCurrency, ExchangeRate, TypeMov, IdTypeCC,
                            GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5, CreatedBy, CreatedFromIP, CompanyId);
                } catch (Exception e) {
                }

            }
            response.sendRedirect(URL);
        }// FINAL IF PARA AGREGAR DATOS DEL COMPROBANTE

        //----------------------------------------------------------------------------------//
        //--------- IF PARA ACTUALIZAR UNA LINEA DEL COMPROBANTE CONTABLE ------------------//
        //----------------------------------------------------------------------------------//        
        if (Accion.equals("UpdateLineBATCH")) {
            DaoContabilidad datos = new DaoContabilidad();
            int IdComprobante = Integer.valueOf(request.getParameter("form-IdComprobante"));
            int LineId = Integer.valueOf(request.getParameter("form-IdLinea"));
            String Descripcion = request.getParameter("form-DescripcionComprobante");
            int IdCatalago = Integer.valueOf(request.getParameter("form-CtaContable"));
            String NumeroReferencia = request.getParameter("form-NumeroReferencia");
            String Fecha = request.getParameter("Fecha");
            String AccountLevel1, AccountLevel2, AccountLevel3, AccountLevel4, AccountLevel5, AccountLevel6;
            datos.BuscarIBGLACCNTS(IdCatalago);
            AccountLevel1 = datos.GetAccountLevel1;
            AccountLevel2 = datos.GetAccountLevel2;
            AccountLevel3 = datos.GetAccountLevel3;
            AccountLevel4 = datos.GetAccountLevel4;
            AccountLevel5 = datos.GetAccountLevel5;
            AccountLevel6 = datos.GetAccountLevel6;
            String ComentLinea = request.getParameter("form-DescLinea");
            Double MontoTranCurr = Double.valueOf(request.getParameter("form-Monto"));
            Double MontoCompCurre = Double.valueOf(request.getParameter("form-Monto"));
            Double MontoBaseCurre = Double.valueOf(request.getParameter("form-Monto"));
            int TypeCurrency = 0;
            Double ExchangeRate = 0.00;
            String TypeMov = request.getParameter("form-TipoMov");
            int IdTypeCC = Integer.valueOf(request.getParameter("form-TipoCC"));
            int IdCtaCC = Integer.valueOf(request.getParameter("form-CtaCentroCost"));
            Double GLTMCCID1 = 0.00, GLTMCCID2 = 0.00, GLTMCCID3 = 0.00, GLTMCCID4 = 0.00, GLTMCCID5 = 0.00;
            String ModifiedBy = "Moises Romero", ModifiedFromIP = "192.168.1.10";
            int CompanyId = 1;
            URL = "Contabilidad/AgregarComprobante.jsp?IdComprobante=" + IdComprobante + "&DescComp=" + Descripcion + "&RefNum=" + NumeroReferencia + "&Fecha=" + Fecha;
            try {
                datos.GLUPDBACH(IdComprobante, IdCatalago, LineId, NumeroReferencia, Descripcion, Fecha, AccountLevel1, AccountLevel2, AccountLevel3,
                        AccountLevel4, AccountLevel5, AccountLevel6, ComentLinea, MontoTranCurr, MontoCompCurre, MontoBaseCurre,
                        TypeCurrency, ExchangeRate, TypeMov, IdTypeCC, GLTMCCID1, GLTMCCID2, GLTMCCID3, GLTMCCID4, GLTMCCID5,
                        ModifiedBy, ModifiedFromIP, CompanyId);
            } catch (Exception e) {
            }
            response.sendRedirect(URL);
        }/////----------FINAL IF PARA ACTUALIZAR UNA LINEA DEL COMPROBANTE CONTABLE----------/////////

        //----------------------------------------------------------------------------------//
        //--------- IF PARA ELIMINAR UNA LINEA DEL COMPROBANTE CONTABLE -------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("DeleteLineBATCH")) {
            int IdComprobante = Integer.valueOf(request.getParameter("form-IdComprobante"));
            int LineId = Integer.valueOf(request.getParameter("form-IdLinea"));
            int IdCatalogo = Integer.valueOf(request.getParameter("form-IdCatalogo"));
            String Descripcion = request.getParameter("form-DescripcionComprobante");
            String NumeroReferencia = request.getParameter("form-NumeroReferencia");
            String Fecha = request.getParameter("Fecha");
            try {
                DaoContabilidad datos = new DaoContabilidad();
                datos.DeleteLineBATCH(IdComprobante, LineId, IdCatalogo);
            } catch (Exception e) {
            }
            URL = "Contabilidad/AgregarComprobante.jsp?IdComprobante=" + IdComprobante + "&DescComp=" + Descripcion + "&RefNum=" + NumeroReferencia + "&Fecha=" + Fecha;
            response.sendRedirect(URL);
        }//FIN DEL IF PARA ELIMINAR UNA LINEA DEL COMPROBANTE CONTABLE

        //----------------------------------------------------------------------------------//
        //---------------- IF PARA ELIMINAR EL COMPROBANTE CONTABLE-------------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("DeleteBATCH")) {
            int IdComprobante = Integer.valueOf(request.getParameter("form-IdComprobante"));
            try {
                DaoContabilidad datos = new DaoContabilidad();
                datos.DeleteBATCH(IdComprobante);

            } catch (Exception e) {
            }
            URL = "Contabilidad/ListarComprobante.jsp";
            response.sendRedirect(URL);
        }//FIN DEL IF PARA ELIMINAR COMPROBANTE CONTABLE

        //----------------------------------------------------------------------------------//
        //---------------- IF PARA APROBAR EL COMPROBANTE CONTABLE-------------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("AprobarBATCH")) {
            int IdComprobante = Integer.valueOf(request.getParameter("form-IdComprobante"));
            int CompanyId = 1;
            String ApprovedBy = "Moises Romero", ApprovedFromIP = "192.168.1.10";
            try {
                DaoContabilidad datos = new DaoContabilidad();
                datos.AprobarBATCH(IdComprobante, CompanyId, ApprovedBy, ApprovedFromIP);

            } catch (Exception e) {
            }
            URL = "Contabilidad/ListarComprobante.jsp";
            response.sendRedirect(URL);
        }//FIN DEL IF PARA APROBAR COMPROBANTE CONTABLE

        //----------------------------------------------------------------------------------//
        //---------------- IF PARA APLICAR EL COMPROBANTE CONTABLE-------------------------//
        //----------------------------------------------------------------------------------//
        if (Accion.equals("AplicarBATCH")) {
            int IdComprobante = Integer.valueOf(request.getParameter("form-IdComprobante"));
            int CompanyId = 1;
            String AppliedBy = "Moises Romero", AppliedFromIP = "192.168.1.10";
            try {
                DaoContabilidad datos = new DaoContabilidad();
                datos.ContabilizarBATCH(IdComprobante, CompanyId, AppliedBy, AppliedFromIP);
                datos.AplicarBATCH(IdComprobante, CompanyId, AppliedBy, AppliedFromIP);
            } catch (Exception e) {
            }
            URL = "Contabilidad/ListarComprobante.jsp";
            response.sendRedirect(URL);
        }//FIN DEL IF PARA APLICAR COMPROBANTE CONTABLE

    }//FIN DEL VOID

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
