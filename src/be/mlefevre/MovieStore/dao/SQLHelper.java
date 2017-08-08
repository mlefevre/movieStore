/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author lefevre
 *
 */
public interface SQLHelper {
	/**
	 * Close the connection and the statement.
	 * @throws SQLException
	 */
	public void destroy() throws SQLException;
	
	public void createTable(String tableName, ColumnDB... columns) throws SQLException;
	public void dropTable(String tableName) throws SQLException;
	
	public void insertRow(String tableName, HashMap<String, String> values) throws SQLException;
	public ResultSet selectRow(String tableName, String condition)throws SQLException;
	public void updateRow(String tableName, HashMap<String, String> newValues, String conditions)throws SQLException;
	public void deleteRow(String tableName, String conditions)throws SQLException;
}
