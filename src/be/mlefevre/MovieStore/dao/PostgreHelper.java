/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * This class provide method to access and manipulate data in 
 * a postgres db.
 * @author lefevre
 *
 */
public class PostgreHelper implements SQLHelper {
	
	private final String DB_NAME = "lefevre";
	private final String DB_URL = "jdbc:postgresql://localhost:5432/";
	private final String DB_USER = "lefevre";
	private final String DB_PWD = "adminDB";
	
	private Connection connection;
	private Statement statement;
	
	/**
	 * Initiate the connection to the db.
	 * 
	 * @throws ClassNotFoundException If the driver are not found.
	 * @throws SQLException If something wrong occurs when oppening the connection.
	 */
	public PostgreHelper()throws ClassNotFoundException, SQLException{
		//check that driver are available
		Class.forName("org.postgresql.Driver");
		// create connection to db lefevre, with user lefevre and pwd adminDB
		connection = DriverManager
           .getConnection(DB_URL + DB_NAME,
           DB_USER, DB_PWD);
        System.out.println("Opened database successfully");

        statement = connection.createStatement();
	}
	
	/**
	 * close both the statement and the connection.
	 * @throws SQLException
	 */
	public void destroy() throws SQLException{
		statement.close();
		connection.close();
	}

	/**
	 * Create a table with the given name and columns defined.<p>
	 * Check if a table with the same name already exist. If so, no new table is created.
	 * @param tableName
	 * @param columns
	 * @throws SQLException
	 */
	public void createTable(String tableName, ColumnDB... columns) throws SQLException{
		if (isTableExist(tableName)){
			//doNothing
			System.out.println("Table "+tableName+" already exists.");
		}else{
			String sql = "CREATE TABLE " +tableName;
			int index = 1;
			for(ColumnDB column : columns){
				if(index==1){
					sql += " (";
				}
				sql += column.toString();
				if(index < columns.length){
					sql += ", ";
				}else{
					sql += ")";
				}
				index++;
			}
			statement.executeUpdate(sql);
			System.out.println("Table "+tableName+" created.");
		}
	}
	
	public void dropTable(String tableName) throws SQLException{
		String sql = "DROP TABLE " +tableName;
		statement.executeUpdate(sql);
		System.out.println("Table "+tableName+" dropped.");
	}
	
	public void insertRow(String tableName, HashMap<String, String> values) throws SQLException{
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		columnBuilder.append("(");
		valueBuilder.append("(");
		int index = 1;
		for(String key : values.keySet()){
			String hasComma = (index<values.size())?",":"";
			columnBuilder.append(key).append(hasComma);
			valueBuilder.append(values.get(key)).append(hasComma);
			index++;
		}
		columnBuilder.append(")");
		valueBuilder.append(")");
		String sql = "INSERT INTO " +tableName+" "+ columnBuilder.toString()+" "
	            + "VALUES "+valueBuilder.toString()+";";

		statement.executeUpdate(sql);
	}
	
	public ResultSet selectRow(String tableName, String condition)throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		if(condition != null && ! condition.trim().equals("")){
			sql.append(" WHERE ").append(condition);
		}
		sql.append(";");
        return statement.executeQuery( sql.toString());
	}
	
	public void updateRow(String tableName, HashMap<String, String> newValues, String conditions)throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(tableName).append(" set ");
		int index = 1;
		for(String key : newValues.keySet()){
			String hasComma = (index<newValues.size())?",":"";
			sql.append(key).append(" = ").append(newValues.get(key)).append(hasComma);
			index++;
		}
		sql.append(" where ").append(conditions);
		
        statement.executeUpdate(sql.toString());
	}
	
	public void deleteRow(String tableName, String conditions)throws SQLException{
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("DELETE from ").append(tableName).append(" WHERE ").append(conditions);
		
        statement.executeUpdate(sqlBuilder.toString());
	}
	
	private boolean isTableExist(String tableName) throws SQLException{
		tableName = tableName.toLowerCase();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT EXISTS ( ")
			.append("SELECT 1 FROM pg_tables WHERE  tablename = '")
			.append(tableName).append("');");
		ResultSet result = statement.executeQuery( sql.toString());
		result.next();
		return result.getBoolean(1);
	}

}