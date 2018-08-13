//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\command\\ConnectionFactory.java

package wdf.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionFactory 
{
   public String userSQL = "root";
   public String pass = "root";
   public String sUrl = "jdbc:mysql://localhost:3306/cloud.pos";
   public Connection conn = null;

    //Conexion a MYSQL
    public static Connection getConnection() throws CommandException
    {

            try 
            {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(sUrl, userSQL, pass);
            } 
            catch (ClassNotFoundException | SQLException e) 
            {
                return null;
            }        
        return conn;
    }

    public Connection Cerrar() 
    {
        conn = null;
        return conn;
    }
   }

