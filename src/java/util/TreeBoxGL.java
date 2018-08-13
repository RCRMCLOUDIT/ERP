package com.cap.util;

import javax.sql.RowSet;
import com.ibm.db.SelectResult;
import com.cap.util.Format;

/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author: 
 */
public class TreeBoxGL {

    /* With accessors */
    private String contextRoot = null;
    private int xPosition = 0;
    private com.cap.erp.SPDBBean bean = null; // Case bean using controller
    private RowSet rowSetCursor = null; // Case cursor in RowSet
    private SelectResult selectResult = null; // Case cursor in SelectResult set
    private int cursor = 0;
    private String javaScript = "/erp/ERP_COMMON/js/popgltree.js";
    private String imagesPath = "/erp/ERP_COMMON/images/popgltree/";
    private int boxWidth = 200;
    private int boxHight = 100;

    private String canSelectAll = "N";
   // To create the output */
    private StringBuffer o = null;

    /* Constants */
    private static int WKLEVEL1_COLUMN = 2;
    private static int WKLEVEL2_COLUMN = 3;
    private static int WKLEVEL3_COLUMN = 4;
    private static int WKLEVEL4_COLUMN = 5;
    private static int WKLEVEL5_COLUMN = 6;
    //private static int ACTYPE_COLUMN = 7;
    private static int ACINDMOV_COLUMN = 8;
    private static int WKCOMPA_COLUMN = 9;

    /**
     * DropBoxGL constructor comment.
     */
    public TreeBoxGL() {
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

        String glnm = null;
        String gl1 = null;
        String gl2 = null;
        String gl3 = null;
        String gl4 = null;
        String gl5 = null;
        String canLink = null;
        String glnmO = null;
        int g1 = 0;
        int g2 = 0;
        int g3 = 0;
        int g4 = 0;
        int g5 = 0;
        int g = 0;

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

        o.append("ICONPATH = '" + contextRoot + imagesPath + "';\n");
        o.append("TREEOFFSET =" + xPosition + ";\n");
        o.append("TREEWIDTH =" + boxWidth + ";\n");
        o.append("TREEHEIGHT =" + boxHight + ";\n");
        o.append("foldersTree = gFld(\"\", \"\");\n");
        for (int l = 0;;) {
            try {
                if (bean != null) { //Controller bean case
                    glnm = (String) bean.valueAtColumnRowResult(WKCOMPA_COLUMN, l, cursor);
                    glnm = glnm.trim();
                    gl1 = (String) bean.valueAtColumnRowResult(WKLEVEL1_COLUMN, l, cursor);
                    gl2 = (String) bean.valueAtColumnRowResult(WKLEVEL2_COLUMN, l, cursor);
                    gl3 = (String) bean.valueAtColumnRowResult(WKLEVEL3_COLUMN, l, cursor);
                    gl4 = (String) bean.valueAtColumnRowResult(WKLEVEL4_COLUMN, l, cursor);
                    gl5 = (String) bean.valueAtColumnRowResult(WKLEVEL5_COLUMN, l, cursor);
                    canLink = (String) bean.valueAtColumnRowResult(ACINDMOV_COLUMN, l, cursor);
                } else if (rowSetCursor != null) { // RowSet case
                    try {
                        if (rowSetCursor.next()) {
                            glnm = rowSetCursor.getString(WKCOMPA_COLUMN);
                            glnm = glnm.trim();
                            gl1 = rowSetCursor.getString(WKLEVEL1_COLUMN);
                            gl2 = rowSetCursor.getString(WKLEVEL2_COLUMN);
                            gl3 = rowSetCursor.getString(WKLEVEL3_COLUMN);
                            gl4 = rowSetCursor.getString(WKLEVEL4_COLUMN);
                            gl5 = rowSetCursor.getString(WKLEVEL5_COLUMN);
                            canLink = rowSetCursor.getString(ACINDMOV_COLUMN);
                        } else
                            break;
                    } catch (java.sql.SQLException e_1) {
                    	System.out.println("glnm = " + glnm);
                    	System.out.println("Exited on sqlexception");
                        break;
                    }
                } else if (selectResult != null) { // SelectResult case (Banks module)
                    try {
                        selectResult.setCurrentRow(l + 1);
                        if (selectResult.isEnd())
                            break;
                        glnm = (String) selectResult.getColumnValue(WKCOMPA_COLUMN);
                        glnm = glnm.trim();
                        gl1 = (String) selectResult.getColumnValue(WKLEVEL1_COLUMN);
                        gl2 = (String) selectResult.getColumnValue(WKLEVEL2_COLUMN);
                        gl3 = (String) selectResult.getColumnValue(WKLEVEL3_COLUMN);
                        gl4 = (String) selectResult.getColumnValue(WKLEVEL4_COLUMN);
                        gl5 = (String) selectResult.getColumnValue(WKLEVEL5_COLUMN);
                        canLink = (String) selectResult.getColumnValue(ACINDMOV_COLUMN);
                    } catch (Exception e) {
                        break;
                    }
                } else
                    break;

                g1 = Integer.parseInt(gl1);
                g2 = Integer.parseInt(gl2);
                g3 = Integer.parseInt(gl3);
                g4 = Integer.parseInt(gl4);
                g5 = Integer.parseInt(gl5);
                g = g2 + g3 + g4;
                glnmO = glnm;
                glnm = Format.escapeOnce(glnm);

                
                if ((g == 0)
                    && canLink.equals("N")) // If true level 1 node and can not be selected
                    {
                    o.append(
                        "aux" + gl1 + " = insFld(foldersTree, gFld(\"" + glnm + "\", \"\"));\n");
                } else if (
                    (g == 0) && canLink.equals("Y")) // If true level 1 node and can be selected
                    {
                    o.append(
                        "insDoc(foldersTree, gLnk(\"Sj\",\""
                            + glnm
                            + "\", \"chooseGl(\\'"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + gl5
                            + "\\',\\'"
                            + Format.escapeDouble(glnmO)
                            + "\\');\"));\n");
                } else if (
                    ((g3 + g4) == 0)
                        && canLink.equals("N")) // If true level 2 node and can not be selected
                    {
                    o.append(
                        "aux"
                            + gl1
                            + gl2
                            + " = insFld(aux"
                            + gl1
                            + ", gFld(\""
                            + glnm
                            + "\", \"\"));\n");
                } else if (
                    ((g3 + g4) == 0)
                        && canLink.equals("Y")) // If true level 2 node and can be selected
                    {
                    o.append(
                        "insDoc(aux"
                            + gl1
                            + ", gLnk(\"Sj\",\""
                            + glnm 
                            + "\", \"chooseGl(\\'"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + gl5
                            + "\\',\\'"
                            + Format.escapeDouble(glnmO)
                            + "\\');\"));\n");
                } else if (
                    (g4 == 0)
                        && canLink.equals("N")) // If true level 3 node and can not be selected
                    {
                    o.append(
                        "aux"
                            + gl1
                            + gl2
                            + gl3
                            + " = insFld(aux"
                            + gl1
                            + gl2
                            + ", gFld(\""
                            + glnm
                            + "\", \"\"));\n");
                } else if (
                    (g4 == 0) && canLink.equals("Y")) // If true level 3 node and can be selected
                    {
                    o.append(
                        "insDoc(aux"
                            + gl1
                            + gl2
                            + ", gLnk(\"Sj\",\""
                            + glnm
                            + "\", \"chooseGl(\\'"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + gl5
                            + "\\',\\'"
                            + Format.escapeDouble(glnmO)
                            + "\\');\"));\n");
                } else if (
                    (g5 == 0)
                        && canLink.equals("N")) // If true level 4 node and can not be selected
                    {
                    o.append(
                        "aux"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + " = insFld(aux"
                            + gl1
                            + gl2
                            + gl3
                            + ", gFld(\""
                            + glnm
                            + "\", \"\"));\n");
                } else if (
                    (g5 == 0) && canLink.equals("Y")) // If true level 4 node and can be selected
                    {
                    o.append(
                        "insDoc(aux"
                            + gl1
                            + gl2
                            + gl3
                            + ", gLnk(\"Sj\",\""
                            + glnm
                            + "\", \"chooseGl(\\'"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + gl5
                            + "\\',\\'"
                            + Format.escapeDouble(glnmO)
                            + "\\');\"));\n");
                } else if (
                    (g5 != 0)
                        && canLink.equals("N")) // If true level 5 node and can not be selected
                    {
                    o.append(
                        "aux"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + gl5
                            + " = insFld(aux"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + ", gFld(\""
                            + glnm
                            + "\", \"\"));\n");
                } else if (
                    (g5 != 0) && canLink.equals("Y")) // If true level 5 node and can be selected
                    {
                    o.append(
                        "insDoc(aux"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + ", gLnk(\"Sj\",\""
                            + glnm
                            + "\", \"chooseGl(\\'"
                            + gl1
                            + gl2
                            + gl3
                            + gl4
                            + gl5
                            + "\\',\\'"
                            + Format.escapeDouble(glnmO)
                            + "\\');\"));\n");
                }

                l++;
            } catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
				System.out.println("glnm = " + glnm);
				System.out.println("Exited on outofboundsexception");
                break;
            }
        }
        o.append("foldersTree.treeID = \"t1966\";\n");
        o.append("gllist('" + contextRoot + "');\n");
        o.append("</script>\n");

        return o.toString();
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
    public void setBean(com.cap.erp.SPDBBean newGlBean) {
        bean = newGlBean;
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
 * Creation date: (5/11/2004 7:03:48 PM)
 * @return javax.sql.RowSet
 */
public javax.sql.RowSet getRowSetCursor() {
	return rowSetCursor;
}

/**
 * Insert the method's description here.
 * Creation date: (5/11/2004 7:03:48 PM)
 * @return com.ibm.db.SelectResult
 */
public com.ibm.db.SelectResult getSelectResult() {
	return selectResult;
}

/**
 * Insert the method's description here.
 * Creation date: (5/11/2004 7:03:48 PM)
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
 * Creation date: (5/11/2004 7:03:48 PM)
 * @param newSelectResult com.ibm.db.SelectResult
 */
public void setSelectResult(com.ibm.db.SelectResult newSelectResult) {
	selectResult = newSelectResult;
}

/**
 * Insert the method's description here.
 * Creation date: (6/9/2004 3:37:51 PM)
 * @return java.lang.String
 */
public java.lang.String getCanSelectAll() {
	return canSelectAll;
}

/**
 * Insert the method's description here.
 * Creation date: (6/9/2004 3:37:51 PM)
 * @param newCanSelectAll java.lang.String
 */
public void setCanSelectAll(java.lang.String newCanSelectAll) {
	canSelectAll = newCanSelectAll;
}
}