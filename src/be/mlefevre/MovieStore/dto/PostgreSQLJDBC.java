/**
 * 
 */
package be.mlefevre.MovieStore.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

/**
 * classe de test et mise en pratique des accès db
 * 
 * @author lefevre
 *
 */
public class PostgreSQLJDBC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	      
	      ColumnDB column1 = new ColumnDB("Name1","int");
	      column1.addCondition("PRIMARY KEY", "NOT NULL");	  
	      ColumnDB column2 = new ColumnDB("Name2","numeric");
	      column2.addCondition("NOT NULL");
	      ColumnDB column3 = new ColumnDB("Name3","char(8)");
	      ColumnDB column4 = new ColumnDB("Name4","varChar");
	      ColumnDB column5 = new ColumnDB("Name5","boolean");

	      HashMap<String, String> row1 = new HashMap<String, String>();
	      row1.put("Name1", "1");
	      row1.put("Name2", "1239");
	      row1.put("Name3", "'abcde'");
	      row1.put("Name4", "'Une String'");
	      row1.put("Name5", "false");
	      HashMap<String, String> row2 = new HashMap<String, String>();
	      row2.put("Name1", "2");
	      row2.put("Name2", "0");
	      row2.put("Name3", "'fghij'");
	      row2.put("Name4", "'Une longue string'");
	      row2.put("Name5", "true");
	      
	      try {
	         PostgreHelper psqlHelper = new PostgreHelper();
	         psqlHelper.createTable("TEST_MLE",column1, column2, column3,column4, column5);

	         psqlHelper.insertRow("TEST_MLE", row1);
	         psqlHelper.insertRow("TEST_MLE", row2);
	         
	         psqlHelper.dropTable("TEST_MLE");
	         
	         psqlHelper.destroy();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	}

}
