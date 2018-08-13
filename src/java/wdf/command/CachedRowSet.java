package wdf.command;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.sql.RowSet;
import javax.sql.RowSetListener;

public class CachedRowSet implements RowSet
{

    private String columnNames[];
    private ArrayList rows;
    private ResultSet rs;
    private int row;

    public CachedRowSet(ResultSet resultset)
        throws SQLException
    {
        rows = null;
        rs = null;
        row = -1;
        rows = new ArrayList();
        rs = resultset;
        readMetaData();
        populate(0);
        rs.close();
        rs = null;
    }

    public CachedRowSet(ResultSet resultset, int i, int j)
        throws SQLException
    {
        rows = null;
        rs = null;
        row = -1;
        rows = new ArrayList(j);
        rs = resultset;
        if(i > 0)
        {
            while(rs.next() && rs.getRow() < i) ;
        }
        readMetaData();
        populate(j);
        rs.close();
        rs = null;
    }

    private void readMetaData()
        throws SQLException
    {
        ResultSetMetaData resultsetmetadata = rs.getMetaData();
        int i = resultsetmetadata.getColumnCount();
        columnNames = new String[i];
        for(int j = 1; j <= i; j++)
        {
            columnNames[j - 1] = resultsetmetadata.getColumnName(j);
        }

    }

    private void populate(int i) throws SQLException
    {
        int j = columnNames.length;
        for(int k = 1; rs.next() && (i == 0 || k <= i); k++)
        {
            Object aobj[] = new Object[j];
            for(int l = 1; l <= j; l++)
            {
                aobj[l - 1] = rs.getObject(l);
            }

            rows.add(((Object) (aobj)));
        }

    }

    public String getUrl()
        throws SQLException
    {
        return null;
    }

    public void setUrl(String s)
        throws SQLException
    {
    }

    public String getDataSourceName()
    {
        return null;
    }

    public void setDataSourceName(String s)
        throws SQLException
    {
    }

    public String getUsername()
    {
        return null;
    }

    public void setUsername(String s)
        throws SQLException
    {
    }

    public String getPassword()
    {
        return null;
    }

    public void setPassword(String s)
        throws SQLException
    {
    }

    public int getTransactionIsolation()
    {
        return 0;
    }

    public void setTransactionIsolation(int i)
        throws SQLException
    {
    }

    public Map getTypeMap()
        throws SQLException
    {
        return null;
    }
/*
    public void setTypeMap(Map map)
        throws SQLException
    {
    }
*/
    public String getCommand()
    {
        return null;
    }

    public void setCommand(String s)
        throws SQLException
    {
    }

    public boolean isReadOnly()
    {
        return true;
    }

    public void setReadOnly(boolean flag)
        throws SQLException
    {
    }

    public int getMaxFieldSize()
        throws SQLException
    {
        return 0;
    }

    public void setMaxFieldSize(int i)
        throws SQLException
    {
    }

    public int getMaxRows()
        throws SQLException
    {
        return 0;
    }

    public void setMaxRows(int i)
        throws SQLException
    {
    }

    public boolean getEscapeProcessing()
        throws SQLException
    {
        return true;
    }

    public void setEscapeProcessing(boolean flag)
        throws SQLException
    {
    }

    public int getQueryTimeout()
        throws SQLException
    {
        return 0;
    }

    public void setQueryTimeout(int i)
        throws SQLException
    {
    }

    public void setType(int i)
        throws SQLException
    {
    }

    public void setConcurrency(int i)
        throws SQLException
    {
    }

    public void setNull(int i, int j)
        throws SQLException
    {
    }

    public void setNull(int i, int j, String s)
        throws SQLException
    {
    }

    public void setBoolean(int i, boolean flag)
        throws SQLException
    {
    }

    public void setByte(int i, byte byte0)
        throws SQLException
    {
    }

    public void setShort(int i, short word0)
        throws SQLException
    {
    }

    public void setInt(int i, int j)
        throws SQLException
    {
    }

    public void setLong(int i, long l)
        throws SQLException
    {
    }

    public void setFloat(int i, float f)
        throws SQLException
    {
    }

    public void setDouble(int i, double d)
        throws SQLException
    {
    }

    public void setBigDecimal(int i, BigDecimal bigdecimal)
        throws SQLException
    {
    }

    public void setString(int i, String s)
        throws SQLException
    {
    }

    public void setBytes(int i, byte abyte0[])
        throws SQLException
    {
    }

    public void setTime(int i, Time time)
        throws SQLException
    {
    }

    public void setObject(int i, Object obj)
        throws SQLException
    {
    }

    public void setAsciiStream(int i, InputStream inputstream, int j)
        throws SQLException
    {
    }

    public void setBinaryStream(int i, InputStream inputstream, int j)
        throws SQLException
    {
    }

    public void setCharacterStream(int i, Reader reader, int j)
        throws SQLException
    {
    }

    public void setObject(int i, Object obj, int j, int k)
        throws SQLException
    {
    }

    public void setObject(int i, Object obj, int j)
        throws SQLException
    {
    }

    public void setRef(int i, Ref ref)
        throws SQLException
    {
    }

    public void setBlob(int i, Blob blob)
        throws SQLException
    {
    }

    public void setClob(int i, Clob clob)
        throws SQLException
    {
    }

    public void setArray(int i, Array array)
        throws SQLException
    {
    }

    public void setTime(int i, Time time, Calendar calendar)
        throws SQLException
    {
    }

    public void setTimestamp(int i, Timestamp timestamp)
        throws SQLException
    {
    }

    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar)
        throws SQLException
    {
    }

    public void setObject(int i, Object obj, Calendar calendar)
        throws SQLException
    {
    }

    public void clearParameters()
        throws SQLException
    {
    }

    public void execute()
        throws SQLException
    {
    }

    public void addRowSetListener(RowSetListener rowsetlistener)
    {
    }

    public void removeRowSetListener(RowSetListener rowsetlistener)
    {
    }

    public boolean next()
        throws SQLException
    {
        row++;
        return row < rows.size();
    }

    public void close()
        throws SQLException
    {
        rows = null;
        columnNames = null;
    }

    public boolean wasNull() throws SQLException
    {
        return true;
    }

    public String getString(int i) throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return obj.toString().trim();
        } else
        {
            return null;
        }
    }
    public String getStringAsIs(int i) throws SQLException
    {	
    	//Yan, 09/18/2009- Do not trim return string
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return (String) obj;
        } 
        else
            return null;
    }

    public boolean getBoolean(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return ((Boolean)obj).booleanValue();
        } else
        {
            return false;
        }
    }

    public byte getByte(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return ((Byte)obj).byteValue();
        } else
        {
            return 0;
        }
    }

    public short getShort(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return ((Short)obj).shortValue();
        } else
        {
            return 0;
        }
    }

    public int getInt(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return ((Integer)obj).intValue();
        } else
        {
            return 0;
        }
    }

    public long getLong(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return ((Long)obj).longValue();
        } else
        {
            return 0L;
        }
    }

    public float getFloat(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        return ((Float)obj).floatValue();
    }

    public double getDouble(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return ((Double)obj).doubleValue();
        } else
        {
            return 0.0D;
        }
    }

    public BigDecimal getBigDecimal(int i, int j) throws SQLException
    {
        return null;
    }

    public byte[] getBytes(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        if(obj != null)
        {
            return (byte[])obj;
        } else
        {
            return null;
        }
    }

    public Time getTime(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        return (Time)obj;
    }

    public Time getTime(int i, Calendar calendar)
        throws SQLException
    {
        return getTime(i);
    }

    public Timestamp getTimestamp(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        return (Timestamp)obj;
    }

    public Object getObject(int i)
        throws SQLException
    {
        checkOnRow();
        return getColumnValue(i);
    }

    public InputStream getAsciiStream(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        return (InputStream)obj;
    }

    public InputStream getUnicodeStream(int i)
        throws SQLException
    {
        return null;
    }

    public InputStream getBinaryStream(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        return (InputStream)obj;
    }

    public String getString(String s)
        throws SQLException
    {
        return getString(findColumn(s));
    }

    public boolean getBoolean(String s)
        throws SQLException
    {
        return getBoolean(findColumn(s));
    }

    public byte getByte(String s)
        throws SQLException
    {
        return getByte(findColumn(s));
    }

    public short getShort(String s)
        throws SQLException
    {
        return getShort(findColumn(s));
    }

    public int getInt(String s)
        throws SQLException
    {
        return getInt(findColumn(s));
    }

    public long getLong(String s)
        throws SQLException
    {
        return getLong(findColumn(s));
    }

    public float getFloat(String s)
        throws SQLException
    {
        return getFloat(findColumn(s));
    }

    public double getDouble(String s)
        throws SQLException
    {
        return getDouble(findColumn(s));
    }

    public BigDecimal getBigDecimal(String s, int i)
        throws SQLException
    {
        return null;
    }

    public byte[] getBytes(String s)
        throws SQLException
    {
        return getBytes(findColumn(s));
    }

    public Object getObject(String s)
        throws SQLException
    {
        return getObject(findColumn(s));
    }

    public InputStream getAsciiStream(String s)
        throws SQLException
    {
        return getAsciiStream(findColumn(s));
    }

    public InputStream getUnicodeStream(String s)
        throws SQLException
    {
        return null;
    }

    public InputStream getBinaryStream(String s)
        throws SQLException
    {
        return getBinaryStream(findColumn(s));
    }

    public SQLWarning getWarnings()
        throws SQLException
    {
        return null;
    }

    public void clearWarnings()
        throws SQLException
    {
    }

    public String getCursorName()
        throws SQLException
    {
        return null;
    }

    public ResultSetMetaData getMetaData()
        throws SQLException
    {
        return null;
    }

    public int findColumn(String s)
        throws SQLException
    {
        for(int i = 0; i < columnNames.length; i++)
        {
            if(columnNames[i].equalsIgnoreCase(s))
            {
                return i + 1;
            }
        }

        throw new SQLException("Column name '" + s + "' not found in result.");
    }

    public Reader getCharacterStream(int i)
        throws SQLException
    {
        return null;
    }

    public Reader getCharacterStream(String s)
        throws SQLException
    {
        return null;
    }

    public BigDecimal getBigDecimal(int i)
        throws SQLException
    {
        Object obj = getColumnValue(i);
        return (BigDecimal)obj;
    }

    public BigDecimal getBigDecimal(String s)
        throws SQLException
    {
        return getBigDecimal(findColumn(s));
    }

    public boolean isBeforeFirst()
        throws SQLException
    {
        return row == -1;
    }

    public boolean isAfterLast()
        throws SQLException
    {
        return row >= rows.size();
    }

    public boolean isFirst()
        throws SQLException
    {
        return row == 0;
    }

    public boolean isLast()
        throws SQLException
    {
        return row == rows.size() - 1;
    }

    public void beforeFirst()
        throws SQLException
    {
        row = -1;
    }

    public void afterLast()
        throws SQLException
    {
        row = rows.size();
    }

    public boolean first()
        throws SQLException
    {
        row = 0;
        return rows.size() > 0;
    }

    public boolean last()
        throws SQLException
    {
        row = rows.size() - 1;
        return rows.size() > 0;
    }

    public int getRow()
        throws SQLException
    {
        return row + 1;
    }

    public boolean absolute(int i)
        throws SQLException
    {
        row = i;
        return row > rows.size() && row < rows.size();
    }

    public boolean relative(int i)
        throws SQLException
    {
        row += i;
        return row > rows.size() && row < rows.size();
    }

    public boolean previous()
        throws SQLException
    {
        row--;
        return row > rows.size() && row < rows.size();
    }

    public void setFetchDirection(int i)
        throws SQLException
    {
    }

    public int getFetchDirection()
        throws SQLException
    {
        return 0;
    }

    public void setFetchSize(int i)
        throws SQLException
    {
    }

    public int getFetchSize()
        throws SQLException
    {
        return 0;
    }

	public int getRowSetSize()
		throws SQLException
	{
		return rows.size();
	}

    public int getType()
        throws SQLException
    {
        return 0;
    }

    public int getConcurrency()
        throws SQLException
    {
        return 0;
    }

    public boolean rowUpdated()
        throws SQLException
    {
        return false;
    }

    public boolean rowInserted()
        throws SQLException
    {
        return false;
    }

    public boolean rowDeleted()
        throws SQLException
    {
        return false;
    }

    public void updateNull(int i)
        throws SQLException
    {
    }

    public void updateBoolean(int i, boolean flag)
        throws SQLException
    {
    }

    public void updateByte(int i, byte byte0)
        throws SQLException
    {
    }

    public void updateShort(int i, short word0)
        throws SQLException
    {
    }

    public void updateInt(int i, int j)
        throws SQLException
    {
    }

    public void updateLong(int i, long l)
        throws SQLException
    {
    }

    public void updateFloat(int i, float f)
        throws SQLException
    {
    }

    public void updateDouble(int i, double d)
        throws SQLException
    {
    }

    public void updateBigDecimal(int i, BigDecimal bigdecimal)
        throws SQLException
    {
    }

    public void updateString(int i, String s)
        throws SQLException
    {
    }

    public void updateBytes(int i, byte abyte0[])
        throws SQLException
    {
    }

    public void updateTime(int i, Time time)
        throws SQLException
    {
    }

    public void updateTimestamp(int i, Timestamp timestamp)
        throws SQLException
    {
    }

    public void updateObject(int i, Object obj)
        throws SQLException
    {
    }

    public void updateAsciiStream(int i, InputStream inputstream, int j)
        throws SQLException
    {
    }

    public void updateBinaryStream(int i, InputStream inputstream, int j)
        throws SQLException
    {
    }

    public void updateCharacterStream(int i, Reader reader, int j)
        throws SQLException
    {
    }

    public void updateObject(int i, Object obj, int j)
        throws SQLException
    {
    }

    public void updateNull(String s)
        throws SQLException
    {
    }

    public void updateBoolean(String s, boolean flag)
        throws SQLException
    {
    }

    public void updateByte(String s, byte byte0)
        throws SQLException
    {
    }

    public void updateShort(String s, short word0)
        throws SQLException
    {
    }

    public void updateInt(String s, int i)
        throws SQLException
    {
    }

    public void updateLong(String s, long l)
        throws SQLException
    {
    }

    public void updateFloat(String s, float f)
        throws SQLException
    {
    }
    /*
    public void updateDouble(String s, double d)
        throws SQLException
    {
    }
	*/
    public void updateBigDecimal(String s, BigDecimal bigdecimal)
        throws SQLException
    {
    }

    public void updateString(String s, String s1)
        throws SQLException
    {
    }

    public void updateBytes(String s, byte abyte0[])
        throws SQLException
    {
    }

    public void updateTime(String s, Time time)
        throws SQLException
    {
    }

    public void updateTimestamp(String s, Timestamp timestamp)
        throws SQLException
    {
    }

    public void updateObject(String s, Object obj)
        throws SQLException
    {
    }

    public void updateAsciiStream(String s, InputStream inputstream, int i)
        throws SQLException
    {
    }

    public void updateBinaryStream(String s, InputStream inputstream, int i)
        throws SQLException
    {
    }

    public void updateCharacterStream(String s, Reader reader, int i)
        throws SQLException
    {
    }

    public void updateObject(String s, Object obj, int i)
        throws SQLException
    {
    }

    public void insertRow()
        throws SQLException
    {
    }

    public void updateRow()
        throws SQLException
    {
    }

    public void deleteRow()
        throws SQLException
    {
    }

    public void refreshRow()
        throws SQLException
    {
    }

    public void cancelRowUpdates()
        throws SQLException
    {
    }

    public void moveToInsertRow()
        throws SQLException
    {
    }

    public void moveToCurrentRow()
        throws SQLException
    {
    }

    public Statement getStatement()
        throws SQLException
    {
        return null;
    }
/*
    public Object getObject(int i, Map map)
        throws SQLException
    {
        return null;
    }
*/
    public Ref getRef(int i)
        throws SQLException
    {
        return null;
    }

    public Blob getBlob(int i)
        throws SQLException
    {
        return null;
    }

    public Clob getClob(int i)
        throws SQLException
    {
        return null;
    }

    public Array getArray(int i)
        throws SQLException
    {
        return null;
    }
/*
    public Object getObject(String s, Map map)
        throws SQLException
    {
        return null;
    }
*/
    public Ref getRef(String s)
        throws SQLException
    {
        return null;
    }

    public Blob getBlob(String s)
        throws SQLException
    {
        return null;
    }

    public Clob getClob(String s)
        throws SQLException
    {
        return null;
    }

    public Array getArray(String s)
        throws SQLException
    {
        return null;
    }

    public Time getTime(String s)
        throws SQLException
    {
        return getTime(findColumn(s));
    }

    public Time getTime(String s, Calendar calendar)
        throws SQLException
    {
        return getTime(findColumn(s), calendar);
    }

    public Object getObject(int i, Calendar calendar)
        throws SQLException
    {
        return getObject(i);
    }

    public Object getObject(String s, Calendar calendar)
        throws SQLException
    {
        return getObject(findColumn(s));
    }

    public Timestamp getTimestamp(int i, Calendar calendar)
        throws SQLException
    {
        return getTimestamp(i);
    }

    public Timestamp getTimestamp(String s, Calendar calendar)
        throws SQLException
    {
        return getTimestamp(findColumn(s), calendar);
    }

    public Timestamp getTimestamp(String s)
        throws SQLException
    {
        return getTimestamp(findColumn(s));
    }

    private void checkOnRow()
        throws SQLException
    {
        if(rows.size() == 0)
        {
            throw new SQLException("The result set is empty");
        }
        if(isBeforeFirst())
        {
            throw new SQLException("The position is before the first row, use next()");
        }
        if(isAfterLast())
        {
            throw new SQLException("The position is after last row");
        } else
        {
            return;
        }
    }

    private Object getColumnValue(int i) throws SQLException
    {
        Object aobj[] = (Object[])rows.get(row);
        if(i <= 0 || i > aobj.length)
        {
            throw new SQLException("Column index " + i + " is out of bounds");
        }
        if(aobj[i - 1] != null)
        {
            return aobj[i - 1];
        } else
        {
            return null;
        }
    }

    public java.sql.Date getDate(int i)
        throws SQLException
    {
        checkOnRow();
        Object obj = getColumnValue(i);
        return (java.sql.Date)obj;
    }

    public java.sql.Date getDate(int i, Calendar calendar)
        throws SQLException
    {
        Object obj = getColumnValue(i);
        return (java.sql.Date)obj;
    }

    public java.sql.Date getDate(String s)
        throws SQLException
    {
        return getDate(findColumn(s));
    }

    public java.sql.Date getDate(String s, Calendar calendar)
        throws SQLException
    {
        return getDate(findColumn(s), calendar);
    }

    public void setDate(int i, java.sql.Date date)
        throws SQLException
    {
    }

    public void setDate(int i, java.sql.Date date, Calendar calendar)
        throws SQLException
    {
    }

    public void updateDate(int i, java.sql.Date date)
        throws SQLException
    {
    }

    public void updateDate(String s, java.sql.Date date)
        throws SQLException
    {
    }
	public void updateArray(int columnIndex, Array x) throws SQLException {}
	public void updateArray(String str, Array x) throws SQLException {}

	public void updateRef(int columnIndex, Ref x) throws SQLException {}
	public void updateRef(String str, Ref x) throws SQLException {}
	
	public void updateBlob(int columnIndex, Blob x) throws SQLException	{}
	public void updateBlob(String str, Blob x) throws SQLException	{}

	public void updateClob(int columnIndex, Clob x) throws SQLException	{}
	public void updateClob(String str, Clob x) throws SQLException	{}

	public java.net.URL getURL(String str) throws SQLException	{return null;}
	public java.net.URL getURL(int i) throws SQLException	{return null;}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String columnName, Map<String, Class<?>> map)
			throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODOx Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODOx Auto-generated method stub
		return false;
	}

	@Override
	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODOx Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODOx Auto-generated method stub
		return false;
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream theInputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream theInputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String parameterName,
			InputStream theInputStream, int length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBigDecimal(String parameterName, BigDecimal theBigDecimal)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream theInputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream theInputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String parameterName,
			InputStream theInputStream, int length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream theInputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream theInputStream,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBlob(String parameterName, InputStream theInputStream)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBlob(String parameterName, InputStream theInputStream,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBlob(String parameterName, Blob theBlob) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBoolean(String parameterName, boolean theBoolean)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setByte(String parameterName, byte theByte) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setBytes(String parametername, byte[] theByteArray)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String parameterName, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String parameterName, Reader theReader,
			int length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader theReader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setClob(String parameterName, Clob x) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setClob(String parameterName, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setClob(String parameterName, Reader theReader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setDate(String parameterName, Date theDate) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setDate(String parameterName, Date theDate, Calendar theCalendar)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setDouble(String parameterName, double theDouble)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setFloat(String parameterName, float theFloat)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setInt(String parameterName, int theInteger)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setLong(String parameterName, long theLong) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader theReader,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader theReader,
			long length) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, NClob theNClob)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader theReader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNClob(String parameterName, NClob value) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNClob(String parameterName, Reader theReader)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNClob(String parameterName, Reader theReader, long length)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNString(int parameterIndex, String theNString)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNString(String parameterName, String theNString)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNull(String parameterName, int sqlType) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setNull(String parameterName, int sqlType, String typeName)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setObject(String parameterName, Object theObject)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setObject(String parameterName, Object theObject,
			int targetSqlType) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setObject(String parameterName, Object theObject,
			int targetSqlType, int scale) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setShort(String parameterName, short theShort)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setString(String parameterName, String theString)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setRowId(int parameterIndex, RowId theRowId)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setRowId(String parameterName, RowId theRowId)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML theSQLXML)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(String parameterName, SQLXML theSQLXML)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setTime(String parameterName, Time theTime) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setTime(String parameterName, Time theTime, Calendar theCalendar)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp theTimestamp)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp theTimestamp,
			Calendar theCalendar) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> theTypeMap)
			throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void setURL(int parameterIndex, URL theURL) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

	@Override
	public void updateDouble(String columnName, double x) throws SQLException {
		// TODOx Auto-generated method stub
		
	}

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
		
}	