/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		if(isTableExist(tableName)){
			String sql = "DROP TABLE " +tableName;
			statement.executeUpdate(sql);
			System.out.println("Table "+tableName+" dropped.");
		}else{
			// Do nothing.
			System.out.println("Table "+tableName+" doesn't exist.");
		}
		
	}
	
	public void insertRow(String tableName, HashMap<String, String> values) throws SQLException{
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		List<String> keySet = new ArrayList<String>();
		keySet.addAll(values.keySet());
		columnBuilder.append("(");
		valueBuilder.append("(");
		for(int i=0 ; i<keySet.size(); i++){
			String hasComma = (i+1<keySet.size())?",":"";
			columnBuilder.append(keySet.get(i)).append(hasComma);
			valueBuilder.append("?").append(hasComma);
		}
		
//		int index = 1;
//		for(String key : keySet){
//			String hasComma = (index<values.size())?",":"";
//			columnBuilder.append(key).append(hasComma);
//			valueBuilder.append(values.get(key)).append(hasComma);
//			index++;
//		}
		columnBuilder.append(")");
		valueBuilder.append(")");
		String sql = "INSERT INTO " +tableName+" "+ columnBuilder.toString()+" "
	            + "VALUES "+valueBuilder.toString()+";";

		PreparedStatement prepSt = connection.prepareStatement(sql);
		for(int i=0 ; i<keySet.size(); i++){
			prepSt.setString(i+1, values.get(keySet.get(i)));
		}
		prepSt.executeUpdate();
//		statement.executeUpdate(sql);
	}
	
	public void updateRow(String tableName, HashMap<String, String> newValues, Condition... conditions)throws SQLException{
		List<String> keySet = new ArrayList<String>();
		keySet.addAll(newValues.keySet());
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(tableName).append(" SET ");
		for(int i=0 ; i<keySet.size(); i++){
			String hasComma = (i+1<keySet.size())?",":"";
			sql.append(keySet.get(i)).append(" = ").append("?").append(hasComma);
		}
		
//		int index = 1;
//		for(String key : newValues.keySet()){
//			String hasComma = (index<newValues.size())?",":"";
//			sql.append(key).append(" = ").append(newValues.get(key)).append(hasComma);
//			index++;
//		}

		if(conditions != null && conditions.length > 0){
			sql.append(" WHERE ");
			for(int i = 0; i<conditions.length; i++){
				if(i>0){
					sql.append(conditions[i].getCombination().getLabel());
				}
				sql.append(conditions[i].getKey())
					.append(conditions[i].getType().getSeparator())
					.append(conditions[i].getType().getStart())
					.append("?")
					.append(conditions[i].getType().getEnd());
			}
		}
			
//		sql.append(" WHERE ").append(conditions);
		
		PreparedStatement prepSt = connection.prepareStatement(sql.toString());
		for(int i=0 ; i<keySet.size(); i++){
			prepSt.setString(i+1, newValues.get(keySet.get(i)));
		}
		if(conditions != null){
			for(int i = 0; i<conditions.length; i++){
				prepSt.setString(i+keySet.size()+1, conditions[i].getValue());
			}
		}
		prepSt.executeUpdate();
//		statement.executeUpdate(sql.toString());
	}
	
	public ResultSet selectRow(String tableName, Condition... conditions)throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(tableName);
		
		if(conditions != null && conditions.length > 0){
			sql.append(" WHERE ");
			for(int i = 0; i<conditions.length; i++){
				if(i>0){
					sql.append(conditions[i].getCombination().getLabel());
				}
				sql.append(conditions[i].getKey())
					.append(conditions[i].getType().getSeparator())
					.append(conditions[i].getType().getStart())
					.append("?")
					.append(conditions[i].getType().getEnd());
			}
		}
		sql.append(";");
		
		PreparedStatement prepSt = connection.prepareStatement(sql.toString()); 
		if(conditions != null){
			for(int i = 0; i<conditions.length; i++){
				prepSt.setString(i+1, conditions[i].getValue());
			}
		}
		
//        return statement.executeQuery( sql.toString());
		return prepSt.executeQuery();
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
