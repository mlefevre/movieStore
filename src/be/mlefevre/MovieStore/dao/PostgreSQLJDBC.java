/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import be.mlefevre.MovieStore.dao.Condition.Combination;
import be.mlefevre.MovieStore.dao.Condition.Type;
import be.mlefevre.MovieStore.dao.impl.PersonDAO;
import be.mlefevre.MovieStore.dao.impl.TitleDAO;
import be.mlefevre.MovieStore.model.Country;
import be.mlefevre.MovieStore.model.Person;
import be.mlefevre.MovieStore.model.Title;

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
//		testPersonDao();
		testTitleDao();
	}

	private static void testTitleDao(){
		Title rando = new Title();
		rando.setOriginal("Les randonneurs");
		
		Title mc = new Title();
		mc.setOriginal("Mountain Crush");
		
		Title dik = new Title();
		dik.setOriginal("Dikkenek");
		
		Title ap = new Title();
		ap.setOriginal("Le fabuleux destin d'Amélie Poulain");
		ap.setEnglishVersion("Amélie");
		
		Title witch = new Title();
		witch.setOriginal("Las brujas de Zugarramurdi");
		witch.setFrenchVersion("Les sorcières de Zugarramurdi");
		witch.setEnglishVersion("Witching & Bitching");
		try{
			PostgreHelper psqlHelper = new PostgreHelper();
			
			TitleDAO dao = new TitleDAO(psqlHelper);
			dao.create();
			dao.save(rando);
			dao.save(mc);
			dao.save(dik);
			dao.save(ap);
			dao.save(witch);
			
			
			pause();
			
			dao.destroy();
			psqlHelper.destroy();
		}catch(Exception e){
		    e.printStackTrace();
		}
	}
	
	private static void testPersonDao(){
		 Person sk = new Person();
	      sk.setName("Kubrick");
	      sk.setFirstName("Stanliii");
	      Calendar calendar= new GregorianCalendar(1928,Calendar.JULY,26);
	      sk.setDateOfBirth(calendar.getTime());
	      sk.setOrigin(Country.USA);

	      Person kl = new Person();
	      kl.setName("Loach");
	      kl.setFirstName("Ken");
	      calendar.set(1936, Calendar.JUNE, 17);
	      kl.setDateOfBirth(calendar.getTime());
	      kl.setOrigin(Country.UK);
	      
	      Person rs = new Person();
	      rs.setName("Scott");
	      rs.setFirstName("Ridley");
	      calendar.set(1937, Calendar.NOVEMBER, 30);
	      rs.setDateOfBirth(calendar.getTime());
	      rs.setOrigin(Country.UK);

	      Person bp = new Person();
	      bp.setName("Poelvoorde");
	      bp.setFirstName("Benoit");
	      calendar.set(1964, Calendar.SEPTEMBER, 22);
	      bp.setDateOfBirth(calendar.getTime());
	      bp.setOrigin(Country.BE);
	      
	      Person laurence = new Person();
	      laurence.setName("d'Arabie");
	      laurence.setFirstName("Laurence");
	      calendar.set(1970, Calendar.JANUARY,1);
	      laurence.setDateOfBirth(calendar.getTime());
	      laurence.setOrigin(Country.UK);
	      
	      try {
	         PostgreHelper psqlHelper = new PostgreHelper();
	         
	         PersonDAO dao = new PersonDAO(psqlHelper);
	         dao.create();
	         dao.save(sk);
	         dao.save(bp);
	         dao.save(rs);
	         dao.save(kl);
	         dao.save(laurence);
	         
	         for(Person p : dao.getAllPersons()){
	        	 System.out.println(p);
	         }
	         
	         sk.setFirstName("Stanley");
	         bp.setFirstName("Benoît l'olibrius");
	         dao.save(sk);
	         dao.save(bp);
	         
	         System.out.println("---------------------------");
	         for(Person p : dao.getAllPersons()){
	        	 System.out.println(p);
	         }
	         pause();
	         dao.destroy();
	    
	         psqlHelper.destroy();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	}

	private static void primaryTest(){
      ColumnDB column1 = new ColumnDB(COLUMN1,"int");
      column1.addCondition("PRIMARY KEY", "NOT NULL");	  
      ColumnDB column2 = new ColumnDB(COLUMN2,"numeric");
      column2.addCondition("NOT NULL");
      ColumnDB column3 = new ColumnDB(COLUMN3,"char(8)");
      ColumnDB column4 = new ColumnDB(COLUMN4,"varChar");
      ColumnDB column5 = new ColumnDB(COLUMN5,"boolean");

      HashMap<String, String> row1 = new HashMap<String, String>();
      row1.put(COLUMN1, "'1'");
      row1.put(COLUMN2, "'1239'");
      row1.put(COLUMN3, "'abcde'");
      row1.put(COLUMN4, "'Une String'");
      row1.put(COLUMN5, "false");
      HashMap<String, String> row2 = new HashMap<String, String>();
      row2.put(COLUMN1, "'2'");
      row2.put(COLUMN2, "'0'");
      row2.put(COLUMN3, "'fghij'");
      row2.put(COLUMN4, "'Une longue string'");
      row2.put(COLUMN5, "true");
      try{
		PostgreHelper psqlHelper = new PostgreHelper();
        
        psqlHelper.createTable(TABLE_NAME,column1, column2, column3,column4, column5);
        psqlHelper.insertRow(TABLE_NAME, row1);
        psqlHelper.insertRow(TABLE_NAME, row2);
        
        Condition fromCol1 = new Condition();
        fromCol1.setKey(COLUMN1);
        fromCol1.setValue(String.valueOf(1));
        fromCol1.setType(Type.EQUAL);
        fromCol1.setCombination(Combination.AND);
        
        ResultSet rs = psqlHelper.selectRow(TABLE_NAME, fromCol1);
        displayQueryResult(rs);
        rs.close();

	      HashMap<String, String> newValues = new HashMap<String, String>();
//	      newValues.put(COLUMN1, "2");
	      newValues.put(COLUMN2, "0");
	      newValues.put(COLUMN3, "'fghij'");
//	      newValues.put(COLUMN4, "'Une longue string'");
//	      newValues.put(COLUMN5, "true");
        
	      psqlHelper.updateRow(TABLE_NAME, newValues, fromCol1);
	      psqlHelper.deleteRow(TABLE_NAME, COLUMN2 + "=0");

	      rs = psqlHelper.selectRow(TABLE_NAME, null);
	      displayQueryResult(rs);
	      rs.close();
        
	      pause();
	      psqlHelper.dropTable(TABLE_NAME);

	      psqlHelper.destroy();
		}catch(Exception e){
			
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
	
	private static void pause() throws IOException {
		System.out.println("Press enter to end the program and drop the created table.");	         
		System.in.read();
	}
}
