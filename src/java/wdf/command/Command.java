//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\command\\Command.java

package wdf.command;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.sql.RowSet;

import java.util.*;
import java.math.BigDecimal;

import wdf.command.ConnectionFactory;

public class Command 
{
   private String name;
   private String language;
   private java.math.BigDecimal timezone;
   private boolean debug;
   private boolean saveAutoCommit;
   private Connection connection;
   private ArrayList parameters;
   private CallableStatement stmt;
   private String message = "";
   private String flag = "";
   
   /**
   @param name
   @roseuid 3C0D49400015
    */
   public Command(String name) 
   {
	this.name = name;
	this.parameters = new ArrayList();
   }
   
   public Command(String lang, BigDecimal tmzone, String name) 
   {
	this.language = lang;
	this.timezone = tmzone;
	this.name = name;
	this.parameters = new ArrayList();
   }
   
   /**
   @roseuid 3C0D1DCD0082
    */
   public Command() 
   {
	this.parameters = new ArrayList();    
   }
   
   /**
   Access method for the name property.
   
   @return   the current value of the name property
    */
   public String getName() 
   {
      return name;    
   }
   
   /**
   Sets the value of the name property.
   
   @param aName the new value of the name property
    */
   public void setName(String aName) 
   {
      name = aName;    
   }
   
   /**
   Determines if the debug property is true.
   
   @return   <code>true<code> if the debug property is true
    */
   public boolean getDebug() 
   {
      return debug;    
   }
   
   /**
   Sets the value of the debug property.
   
   @param aDebug the new value of the debug property
    */
   public void setDebug(boolean aDebug) 
   {
      debug = aDebug;    
   }
   
   /**
   Access method for the connection property.
   
   @return   the current value of the connection property
     * @throws java.sql.SQLException
    */
   public Connection getConnection() 
   {
	if (this.connection == null) {
		this.connection = ConnectionFactory();
                this.connection = "";
		/* TODO REMOVE DEBUG */
		this.debug("Connection " + this.connection + " got from pool. autoCmt=" + this.connection.getAutoCommit());
	}
      return connection;    
   }
   
   /**
   Sets the value of the connection property.
   
   @param aConnection the new value of the connection property
    */
   public void setConnection(Connection aConnection) 
   {
      connection = aConnection;    
   }
   
   /**
   @param value
   @roseuid 3C0D1F89009E
    */
   public void addParameter(int value) 
   {
	this.parameters.add(new Integer(value));
   }
   
   /**
   @param value
   @roseuid 3C0D1F5100E4
    */
   public void addParameter(Object value) 
   {
	this.parameters.add(value);
   }
   
   /**
   @param value
   @roseuid 3C0D1FAE0097
    */
   public void addParameter(char value) 
   {
	this.parameters.add(new Character(value));
   }

   /**
   @throws com.cap.wdf.command.CommandException

    */
   public void execute() throws CommandException 
   {
	   this.execute(true);
   }
   
   /**
   @throws com.cap.wdf.command.CommandException
   @roseuid 3C0D45FF02EF
    */
   public void execute(boolean autoCommit) throws CommandException 
   {
	   
	StringBuffer sql = new StringBuffer();
	sql.append("{ call ");
	sql.append(this.name);
	sql.append("(");

	for (int i=0; i<this.parameters.size(); i++) {
		if (i>0)
			sql.append(",");
		sql.append("?");
	}
	if (this.parameters.size() > 0)
		sql.append(",");
		
	sql.append("?,?,?,?) } ");	// language, timezone, errorFlag & errorMsg

	/* TODO REMOVE DEBUG */
	this.debug("Prepared statement '" + sql.toString() + "'");

	try {
		//System.out.println("in command 1");
		Connection conn = this.getConnection();
		//System.out.println("in command 2");
		this.stmt =  conn.prepareCall(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//System.out.println("in command 3");

	} 
	catch (SQLException sqle) 
	{ 
		//System.out.println("caught sqlExp");
		//throw new CommandErrorException(sqle.toString());

		//does not work, can not catch it?
		throw new CommandFatalException(sqle, "Error creating preparedStmt [" + sql.toString() + "]"); 
	}
	catch (Exception exp)
	{
		System.out.println("in command catch exp:" + exp.getStackTrace());
		
	}

	
	//System.out.println("in command 4");

	/* TODO REMOVE DEBUG */
	this.debug("Parameter values: " + this.parameters.size());
	//System.out.println("in command 4");

	Object parameterValue = null;
	int i = 0;
	try {
		for (i=1; i<=this.parameters.size(); i++) {

			parameterValue = this.parameters.get(i-1);

			/* TODO REMOVE DEBUG */
			this.debug("Command " + this.name + " parameter " + i + ": " + parameterValue  + " (" +  (parameterValue != null ? parameterValue.getClass().getName() : "null") + ")");

			if (parameterValue == null)
				this.stmt.setNull(i, java.sql.Types.CHAR);

			else if (parameterValue instanceof String)
				this.stmt.setString(i, (String) parameterValue);

			else if (parameterValue instanceof BigDecimal)
				this.stmt.setBigDecimal(i, (BigDecimal) parameterValue);

			else if (parameterValue instanceof java.sql.Timestamp)
				this.stmt.setTimestamp(i, (java.sql.Timestamp) parameterValue );

			else if (parameterValue instanceof java.util.Date)
				this.stmt.setTimestamp(i, new java.sql.Timestamp( ((java.util.Date)parameterValue).getTime() ) );

			else if (parameterValue instanceof Integer)
				this.stmt.setInt(i, ((Integer) parameterValue).intValue());

			else if (parameterValue instanceof Collection)
				this.stmt.setString(i, parseBigParameter((Collection)parameterValue));
			else
				this.stmt.setObject(i, parameterValue);
		}
		//System.out.println("in command 5");

		/* TODO REMOVE DEBUG */
		this.debug("Command " + this.name + " parameter " + i + ": " + language  + " (java.lang.String)");
		this.debug("Command " + this.name + " parameter " + (i+1) + ": " + timezone  + " (java.lang.Decimal)");
		//System.out.println("in command 5");

		this.stmt.setString(i, language);
		this.stmt.setBigDecimal(++i, timezone);
		this.stmt.registerOutParameter(++i, java.sql.Types.CHAR);	//errorFlag
		this.stmt.registerOutParameter(++i, java.sql.Types.CHAR);	//errorMsg
		//System.out.println("in command 5");

	} catch (SQLException sqle) { throw new CommandFatalException(sqle, "Error setting parameter at " + i + " index"); }

	try {
		//this.setAutoCommit(autoCommit);

		this.stmt.execute();

		//this.restoreAutoCommit();

		String errorFlag = this.stmt.getString(i-1);
		String errorMsg = this.stmt.getString(i);
		/* TODO REMOVE DEBUG */
		this.debug("Command " + this.name + " executed with errorFlag='" + errorFlag + "', errorMsg='" + errorMsg + "'");

		/*
		if (!errorFlag.equalsIgnoreCase("S") && !errorFlag.equalsIgnoreCase(" ")) {
			throw new CommandErrorException(errorMsg);
		}
		*/
		if (errorFlag.equalsIgnoreCase("F")) {
			throw new CommandErrorException(errorMsg);
		}
		else
		{
			flag = errorFlag;
			message = errorMsg;
		}
		

	} catch (SQLException sqle) { 
			//this.restoreAutoCommit();
			//System.out.println("comes to here");
			throw new CommandFatalException(sqle); 
	}
   }
   
   /**
   @return java.util.Collection
   @roseuid 3C0D173D0217
    */
   public Collection getRowSets() throws CommandException
   {
	Collection results = new ArrayList();

	try {
		ResultSet rs = stmt.getResultSet();

		while (rs != null) {
			results.add( createRowSet(rs) );
			stmt.getMoreResults();
			rs = stmt.getResultSet();
		}
	} catch (SQLException e) { new CommandFatalException(e); }

	return results;
   }
   
   public RowSet getNextRowSet() throws CommandException 
   {
	RowSet rowset = null;
	try {
		ResultSet rs = this.stmt.getResultSet();
		if (rs == null)
			throw new CommandFatalException("No next result set available for command '" + this.name + "'");

		rowset = createRowSet(rs);
		this.stmt.getMoreResults();

	} catch (SQLException e) { new CommandFatalException(e); }

	return rowset;
   }
   
   public RowSet getNextRowSet(int page, int pageSize) throws CommandException 
   {
	RowSet rowset = null;
	try {
		ResultSet rs = this.stmt.getResultSet();
		if (rs == null)
			throw new CommandFatalException("No next result set available for command '" + this.name + "'");

		rowset = createRowSet(rs, page, pageSize);
		this.stmt.getMoreResults();
	} catch (SQLException e) { new CommandFatalException(e); }

	return rowset;
   }
   
   /**
   @roseuid 3C0D499202BC
    */
   public void close() 
   {
	this.parameters = null;

	if (this.stmt != null)
		try { 
			this.stmt.close();
		} catch (SQLException e) { debug(e); }

   	if (this.connection != null) {
		try { 
			if (!this.connection.isClosed())
				this.connection.close();

		} catch (SQLException e) { debug(e); }
		this.connection = null;
	}
   }
   
   /**
   @param resultSet
   @param page
   @param size
   @return javax.sql.RowSet
   @roseuid 3C0D8D7A02B7
    */
   protected RowSet createRowSet(ResultSet resultSet, int page, int size) throws SQLException
   {
	int offset = (page == 1 ? 0 : (page-1) * size);
	CachedRowSet rowset = new CachedRowSet(resultSet, offset, size+1);
	return rowset;
   }
   
   /**
   @param resultSet
   @return javax.sql.RowSet
   @roseuid 3C0D8D38008C
    */
   protected RowSet createRowSet(ResultSet resultSet) throws SQLException
   {
	CachedRowSet rowset = new CachedRowSet(resultSet) {};
	return rowset;
   }

   protected String parseBigParameter(Collection bigParameter) throws SQLException
   {
	StringBuffer parsed = new StringBuffer();
	Iterator values = bigParameter.iterator();
	String sep = com.cap.util.ConstantValue.DELIMITER;

	while (values.hasNext()) {
		parsed.append(values.next().toString());
		parsed.append(sep);
	}
	return parsed.toString();
   }


   protected void debug(String text)
   {
	if (this.debug)
		System.out.println(text);
   }

   protected void debug(Throwable e)
   {
	if (this.debug)
		e.printStackTrace(System.out);
   }

   protected void finalize() throws Throwable
   {
	this.close();
   }

   	private void setAutoCommit(boolean autoCommit) throws CommandException
   	{
		try 
		{
			this.saveAutoCommit = this.getConnection().getAutoCommit();
			this.getConnection().setAutoCommit(autoCommit);
		} 
		catch (SQLException e) 
		{ 
			new CommandFatalException(e); 
		}

   	}

   	private void restoreAutoCommit()
   	{
		try 
		{
			this.getConnection().setAutoCommit(this.saveAutoCommit);
		} 
		catch (Exception e) 
		{ 
		}
   	}

	public String getMessage() 
	{
		return message;
	}
	
	public String getFlag() 
	{
		return flag;
	}
/**
 * @return
 */
public CallableStatement getCommandStatement() {
	return stmt;
}

/**
 * @return
 */
public String getLanguage() {
	return language;
}

/**
 * @param string
 */
public void setLanguage(String lang) {
	language = lang;
}

public java.math.BigDecimal getTimezone() {
	return timezone;
}

public void setTimezone(java.math.BigDecimal timezone) {
	this.timezone = timezone;
}

}