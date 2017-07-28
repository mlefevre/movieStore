/**
 * 
 */
package be.mlefevre.MovieStore.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * classe de test et mise en pratique des accès db.
 * 
 * @author lefevre
 *
 */
public class PostgreSQLJDBC {

	private static final String COLUMN5 = "Name5";
	private static final String COLUMN4 = "Name4";
	private static final String COLUMN3 = "Name3";
	private static final String COLUMN2 = "Name2";
	private static final String COLUMN1 = "Name1";
	private static final String TABLE_NAME = "TEST_MLE";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	      
	      ColumnDB column1 = new ColumnDB(COLUMN1,"int");
	      column1.addCondition("PRIMARY KEY", "NOT NULL");	  
	      ColumnDB column2 = new ColumnDB(COLUMN2,"numeric");
	      column2.addCondition("NOT NULL");
	      ColumnDB column3 = new ColumnDB(COLUMN3,"char(8)");
	      ColumnDB column4 = new ColumnDB(COLUMN4,"varChar");
	      ColumnDB column5 = new ColumnDB(COLUMN5,"boolean");

	      HashMap<String, String> row1 = new HashMap<String, String>();
	      row1.put(COLUMN1, "1");
	      row1.put(COLUMN2, "1239");
	      row1.put(COLUMN3, "'abcde'");
	      row1.put(COLUMN4, "'Une String'");
	      row1.put(COLUMN5, "false");
	      HashMap<String, String> row2 = new HashMap<String, String>();
	      row2.put(COLUMN1, "2");
	      row2.put(COLUMN2, "0");
	      row2.put(COLUMN3, "'fghij'");
	      row2.put(COLUMN4, "'Une longue string'");
	      row2.put(COLUMN5, "true");
	      
	      try {
	         PostgreHelper psqlHelper = new PostgreHelper();
	         psqlHelper.createTable(TABLE_NAME,column1, column2, column3,column4, column5);

	         psqlHelper.insertRow(TABLE_NAME, row1);
	         psqlHelper.insertRow(TABLE_NAME, row2);
	         
	         ResultSet rs = psqlHelper.selectRow(TABLE_NAME, COLUMN1 +" =1");
	         displayQueryResult(rs);
	         rs.close();

		      HashMap<String, String> newValues = new HashMap<String, String>();
//		      newValues.put(COLUMN1, "2");
		      newValues.put(COLUMN2, "0");
		      newValues.put(COLUMN3, "'fghij'");
//		      newValues.put(COLUMN4, "'Une longue string'");
//		      newValues.put(COLUMN5, "true");
	         
		      psqlHelper.updateRow(TABLE_NAME, newValues, COLUMN1+"=1");
		      psqlHelper.deleteRow(TABLE_NAME, COLUMN2 + "=0");

	         rs = psqlHelper.selectRow(TABLE_NAME, null);
	         displayQueryResult(rs);
	         rs.close();
	         
	         System.out.println("Press enter to end the program and drop the created table.");	         
	         System.in.read();
	         psqlHelper.dropTable(TABLE_NAME);
	         
	         psqlHelper.destroy();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	}

	private static void displayQueryResult(ResultSet rs) throws SQLException {
		while (rs.next()) {
		     int name1Val = rs.getInt(COLUMN1);
		     int name2Val = rs.getInt(COLUMN2);
		     String  name3Val = rs.getString(COLUMN3);
		     String  name4Val = rs.getString(COLUMN4);
		     boolean name5Val  = rs.getBoolean(COLUMN5);
		     System.out.println( "Name1 = " + name1Val );
		     System.out.println( "Name2 = " + name2Val );
		     System.out.println( "Name3 = " + name3Val );
		     System.out.println( "Name4 = " + name4Val );
		     System.out.println( "Name5 = " + name5Val );
		     System.out.println();
		}
	}

}
