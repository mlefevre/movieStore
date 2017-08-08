/**
 * 
 */
package be.mlefevre.MovieStore.dao.transformer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import be.mlefevre.MovieStore.dao.ColumnDB;
import be.mlefevre.MovieStore.dao.SQLHelper;
import be.mlefevre.MovieStore.dao.transformer.Transformer;
import be.mlefevre.MovieStore.exceptions.DaoException;
import be.mlefevre.MovieStore.model.Country;
import be.mlefevre.MovieStore.model.Person;

/**
 * @author lefevre
 *
 */
public class PersonTransformer implements Transformer<Person> {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String PERSON_TABLE_NAME = "PERSON";
	public static final String NAME_COL_NAME = "name";
	public static final String FIRSTNAME_COL_NAME = "firstName";
	public static final String DOB_COL_NAME = "dateOfBirth";
	public static final String ORIGIN_COL_NAME = "origin";

	/* (non-Javadoc)
	 * @see be.mlefevre.MovieStore.dao.transformer.Transformer#getValuesMap(java.lang.Object)
	 */
	@Override
	public HashMap<String, String> getValuesMap(Person entity) {
		
		HashMap<String, String> row = new HashMap<String, String>();
		row.put(NAME_COL_NAME, "'" + entity.getName() + "'");
		row.put(FIRSTNAME_COL_NAME, "'" + entity.getFirstName() + "'");
	      DateFormat dFormat = new SimpleDateFormat(DATE_FORMAT);
	      
		row.put(DOB_COL_NAME, "'" + dFormat.format(entity.getDateOfBirth()) + "'");
		row.put(ORIGIN_COL_NAME, "'" + entity.getOrigin().getName() + "'" );
		return row;
	}

	/* (non-Javadoc)
	 * @see be.mlefevre.MovieStore.dao.transformer.Transformer#getObject(java.sql.ResultSet)
	 */
	@Override
	public Person getObject(ResultSet result) throws SQLException, DaoException{
		Person person = new Person();
		person.setName(result.getString(NAME_COL_NAME));
		person.setFirstName(result.getString(FIRSTNAME_COL_NAME));
		try{
			DateFormat format = new SimpleDateFormat(DATE_FORMAT);
			Date birthDate = format.parse(result.getString(DOB_COL_NAME));
			person.setDateOfBirth(birthDate);
		}catch(ParseException e){
			throw new DaoException("Error when parsing date :" + e);
		}
		person.setOrigin(Country.getCountry(result.getString(ORIGIN_COL_NAME)));
		
		return person;
	}

	@Override
	public void createTable(SQLHelper helper) throws SQLException {
		
		ColumnDB colName = new ColumnDB(NAME_COL_NAME,"varChar");
		colName.addCondition("NOT NULL");
		ColumnDB colFirstName = new ColumnDB(FIRSTNAME_COL_NAME, "varChar");
		colFirstName.addCondition("NOT NULL");
		ColumnDB colBirth = new ColumnDB(DOB_COL_NAME, "varChar");
		ColumnDB colOrigin = new ColumnDB(ORIGIN_COL_NAME, "varChar");
		
		// TODO : alternative name.
		helper.createTable(PERSON_TABLE_NAME, colName, colFirstName, colBirth, colOrigin);
	}

}
