package com.cap.util;

import com.cap.util.Format;
import javax.sql.RowSet;
import com.ibm.db.SelectResult;

/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author:
 */
public class DropBoxEP {

    /* With accessors */
    private String contextRoot = null;
    private int charsToShowColumn1 = 50;
    private int charsToShowColumn2 = 25;
    private int xPosition = 0;
    private com.cap.erp.SPDBBean bean = null; // Case bean using controller
    private RowSet rowSetCursor = null; // Case cursor in RowSet
    private SelectResult selectResult = null; // Case cursor in SelectResult set
    private int cursor = 0;
    private String javaScript = "/erp/ERP_COMMON/js/dropboxlist.js";
    private String imagesPath = "/erp/ERP_COMMON/images/";
    private int boxWidth = 470;
    private int boxHight = 90;

    // To create the output */
    private StringBuffer o = null;

    /* Constants */
    private static int WKLEVEL1_COLUMN = 2;
    private static int ET_COLUMN = 1;
    private static int ECNAME_COLUMN = 2;
    private static int CMCUS_COLUMN = 3;
    private static int PMNAME_COLUMN = 4;
    private static int PMPRO_COLUMN = 5;
    private static int TKNAME_COLUMN = 6;
    private static int TKLEV1_COLUMN = 7;
    private static int TKLEV2_COLUMN = 8;

    /**
     * DropBoxGL constructor comment.
     */
    public DropBoxEP() {
        super();
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 9:22:21 PM)
     * @return int
     */
    public int getBoxHight() {
        return boxHight;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 9:22:21 PM)
     * @return int
     */
    public int getBoxWidth() {
        return boxWidth;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return java.lang.String
     */
    public java.lang.String getBuilt() {

        String beforeCustomer = null;
        String beforeProject = null;
        String beforeTask = null;
        StringBuffer empCustId = null;
        String et = null;
        String ecname = null;
        String cmcus = null;
        String pmname = null;
        String pmproCocon = null;
        String tkname = null;
        java.math.BigDecimal tklev1 = null;
        java.math.BigDecimal tklev2 = null;
        boolean goOut = false;

        o =
            new StringBuffer(
                "<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n"
                    + "<!--\nif (!(window.DROPBOXWIDTH)) {\n"
                    + "document.write(\"<SCRIPT LANGUAGE='JavaScript1.2' SRC='"
                    + contextRoot
                    + javaScript
                    + "' TYPE='text/javascript'></SCRIPT>\");\n"
                    + "}\n//-->\n</SCRIPT>\n");
        o.append("<script language=\"JavaScript\">\n");
        o.append(
            "DROPBOXICONPATH = '"
                + contextRoot
                + imagesPath
                + "';"
                + "CHARSTOSHOWTEXT1 = "
                + charsToShowColumn1
                + ";\n CHARSTOSHOWTEXT2 = "
                + charsToShowColumn2
                + ";\n");
        o.append("DROPBOXWIDTH=" + boxWidth + ";\nDROPBOXHEIGHT=" + boxHight + ";\n");
        o.append(
            "listBoxValues['etList'] = new Array();\nlistBoxText1['etList'] = new Array();\n"
                + "listBoxText2['etList'] = new Array();\nlistBoxOffSetX['etList'] = "
                + xPosition
                + ";\n");

        for (int l = 0;;) {
            try {
                if (bean != null) { //Controller bean case
                    et = (String) bean.valueAtColumnRowResult(ET_COLUMN, l, cursor);
                    ecname = (String) bean.valueAtColumnRowResult(ECNAME_COLUMN, l, cursor);
                    cmcus = (String) bean.valueAtColumnRowResult(CMCUS_COLUMN, l, cursor);
                    beforeCustomer =
                        l == 0 ? "" : (String) bean.valueAtColumnRowResult(CMCUS_COLUMN, l - 1, cursor);
                    pmname = (String) bean.valueAtColumnRowResult(PMNAME_COLUMN, l, cursor);
                    pmproCocon = (String) bean.valueAtColumnRowResult(PMPRO_COLUMN, l, cursor);
                    beforeProject =
                        l == 0 ? "" : (String) bean.valueAtColumnRowResult(PMPRO_COLUMN, l - 1, cursor);
                    tkname = (String) bean.valueAtColumnRowResult(TKNAME_COLUMN, l, cursor);
                    beforeTask =
                        l == 0
                            ? ""
                            : (String) bean.valueAtColumnRowResult(TKNAME_COLUMN, l - 1, cursor);
                    tklev1 =
                        (java.math.BigDecimal) bean.valueAtColumnRowResult(TKLEV1_COLUMN, l, cursor);
                    tklev2 =
                        (java.math.BigDecimal) bean.valueAtColumnRowResult(TKLEV2_COLUMN, l, cursor);
                } else if (rowSetCursor != null) { // RowSet case
                    try {
                        if (rowSetCursor.next()) {
                            et = rowSetCursor.getString(ET_COLUMN);
                            ecname = rowSetCursor.getString(ECNAME_COLUMN);
                            cmcus = rowSetCursor.getString(CMCUS_COLUMN);
                            pmname = rowSetCursor.getString(PMNAME_COLUMN);
                            pmproCocon = rowSetCursor.getString(PMPRO_COLUMN);
                            tkname = rowSetCursor.getString(TKNAME_COLUMN);
                            tklev1 = rowSetCursor.getBigDecimal(TKLEV1_COLUMN);
                            tklev2 = rowSetCursor.getBigDecimal(TKLEV2_COLUMN);
                            if (l > 0) {
                                rowSetCursor.absolute(l);
                                beforeCustomer = rowSetCursor.getString(CMCUS_COLUMN);
                                beforeProject = rowSetCursor.getString(PMPRO_COLUMN);
                                beforeTask = rowSetCursor.getString(TKNAME_COLUMN);
                            } else {
                                beforeCustomer = "";
                                beforeProject = "";
                                beforeTask = "";
                            }
                        } else {
                            break;
                        }
                    } catch (java.sql.SQLException e_1) {
                        break;
                    }
                } else if (selectResult != null) { // SelectResult case (Banks module)
                    try {	                  
                        selectResult.setCurrentRow(l+1);
                        if (selectResult.isEnd()) goOut = true; 
                        et = (String) selectResult.getColumnValue(ET_COLUMN);
                        ecname = (String) selectResult.getColumnValue(ECNAME_COLUMN);
                        cmcus = (String) selectResult.getColumnValue(CMCUS_COLUMN);
                        pmname = (String) selectResult.getColumnValue(PMNAME_COLUMN);
                        pmproCocon = (String) selectResult.getColumnValue(PMPRO_COLUMN);
                        tkname = (String) selectResult.getColumnValue(TKNAME_COLUMN);
                        tklev1 = (java.math.BigDecimal) selectResult.getColumnValue(TKLEV1_COLUMN);
                        tklev2 = (java.math.BigDecimal) selectResult.getColumnValue(TKLEV2_COLUMN);                        
                        if (l > 0) {
                            selectResult.setCurrentRow(l);
                            beforeProject = (String) selectResult.getColumnValue(PMPRO_COLUMN);
                            beforeTask = (String) selectResult.getColumnValue(TKNAME_COLUMN);
                            beforeCustomer = (String) selectResult.getColumnValue(CMCUS_COLUMN);
                        } else {
                            beforeCustomer = "";
                            beforeProject = "";
                            beforeTask = "";
                        }
                        if (goOut) break;
                    } catch (Exception e) {
                        break;
                    }

                } else {
                    break;
                }

                empCustId = new StringBuffer(et);
                if (et.equals("E")) {
                    empCustId =
                        empCustId.append(ConstantValue.DELIMITER).append(pmproCocon).append("©©© ©©©0©©©0©©©");
                    o.append(
                        "addData('"
                            + empCustId
                            + "','"
                            + Format.indent(1, ecname, charsToShowColumn1)
                            + "','Employee','etList');\n");
                } else if (et.equals("T")) {
                    empCustId =
                        empCustId.append(ConstantValue.DELIMITER).append(cmcus).append(ConstantValue.DELIMITER).append(pmproCocon);
                    empCustId =
                        empCustId.append(ConstantValue.DELIMITER).append(tklev1).append(ConstantValue.DELIMITER).append(tklev2).append(
                            ConstantValue.DELIMITER);
                    if (!(beforeCustomer.equals(cmcus))) {
                        o.append(
                            "addData('0','"
                                + Format.indent(1, ecname, charsToShowColumn1)
                                + "','Customer','etList');\n");
                        o.append(
                            "addData('0','"
                                + Format.indent(2, pmname, charsToShowColumn1)
                                + "','Project','etList');\n");
                        o.append(
                            "addData('"
                                + empCustId
                                + "','"
                                + Format.indent(3, tkname, charsToShowColumn1)
                                + "','Task','etList');\n");
                    } else if (!(beforeProject.equals(pmproCocon))) {
                        o.append(
                            "addData('0','"
                                + Format.indent(2, pmname, charsToShowColumn1)
                                + "','Project','etList');\n");
                        o.append(
                            "addData('"
                                + empCustId
                                + "','"
                                + Format.indent(3, tkname, charsToShowColumn1)
                                + "','Task','etList');\n");
                    } else if (!(beforeTask.equals(tkname))) {
                        o.append(
                            "addData('"
                                + empCustId
                                + "','"
                                + Format.indent(3, tkname, charsToShowColumn1)
                                + "','Task','etList');\n");
                    }
                }
                l++;
            } catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
                break;
            }
        }

        o.append("writeTable('etList');\n");
        o.append("</script>\n");

        return o.toString();
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return int
     */
    public int getCharsToShowColumn1() {
        return charsToShowColumn1;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return int
     */
    public int getCharsToShowColumn2() {
        return charsToShowColumn2;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return java.lang.String
     */
    public java.lang.String getContextRoot() {
        return contextRoot;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 8:16:47 PM)
     * @return int
     */
    public int getCursor() {
        return cursor;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return com.cap.erp.SPDBBean
     */
    public com.cap.erp.SPDBBean getBean() {
        return bean;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 8:16:47 PM)
     * @return java.lang.String
     */
    public java.lang.String getImagesPath() {
        return imagesPath;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return java.lang.String
     */
    public java.lang.String getJavaScript() {
        return javaScript;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 9:22:21 PM)
     * @param newBoxHight int
     */
    public void setBoxHight(int newBoxHight) {
        boxHight = newBoxHight;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 9:22:21 PM)
     * @param newBoxWidth int
     */
    public void setBoxWidth(int newBoxWidth) {
        boxWidth = newBoxWidth;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @param newCharsToShowColumn1 int
     */
    public void setCharsToShowColumn1(int newCharsToShowColumn1) {
        charsToShowColumn1 = newCharsToShowColumn1;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @param newCharsToShowColumn2 int
     */
    public void setCharsToShowColumn2(int newCharsToShowColumn2) {
        charsToShowColumn2 = newCharsToShowColumn2;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @param newContextRoot java.lang.String
     */
    public void setContextRoot(java.lang.String newContextRoot) {
        contextRoot = newContextRoot;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 8:16:47 PM)
     * @param newCursor int
     */
    public void setCursor(int newCursor) {
        cursor = newCursor;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @param newGlBean com.cap.erp.SPDBBean
     */
    public void setBean(com.cap.erp.SPDBBean newBean) {
        bean = newBean;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 8:16:47 PM)
     * @param newImagesPath java.lang.String
     */
    public void setImagesPath(java.lang.String newImagesPath) {
        imagesPath = newImagesPath;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @param newJavaScript java.lang.String
     */
    public void setJavaScript(java.lang.String newJavaScript) {
        javaScript = newJavaScript;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 8:56:35 PM)
     * @param newXPosition int
     */
    public void setXPosition(int newXPosition) {
        xPosition = newXPosition;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @return javax.sql.RowSet
     */
    public javax.sql.RowSet getRowSetCursor() {
        return rowSetCursor;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @return com.ibm.db.SelectResult
     */
    public com.ibm.db.SelectResult getSelectResult() {
        return selectResult;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @param newRowSetCursor javax.sql.RowSet
     */
    public void setRowSetCursor(javax.sql.RowSet newRowSetCursor) {
        rowSetCursor = newRowSetCursor;
        try {
			rowSetCursor.beforeFirst();
        } catch (Exception e) {
	        rowSetCursor = null;
        }        
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @param newSelectResult com.ibm.db.SelectResult
     */
    public void setSelectResult(com.ibm.db.SelectResult newSelectResult) {
        selectResult = newSelectResult;
    }
}