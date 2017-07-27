/**
 * 
 */
package be.mlefevre.MovieStore.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

/**
 * This class provide method to access and manipulate data in 
 * a postgres db.
 * @author lefevre
 *
 */
public class PostgreHelper {
	
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

	public void createTable(String tableName, ColumnDB... columns) throws SQLException{
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

}
