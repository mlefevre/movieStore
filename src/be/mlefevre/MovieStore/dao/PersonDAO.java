/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;

import be.mlefevre.MovieStore.dao.transformer.Transformer;
import be.mlefevre.MovieStore.dao.transformer.impl.PersonTransformer;
import be.mlefevre.MovieStore.exceptions.DaoException;
import be.mlefevre.MovieStore.model.Person;

/**
 * Translate a {@link Person} object into db.
 * @author lefevre
 *
 */
public class PersonDAO extends Dao<Person> {

	private Transformer<Person> personTransformer;
	
	public PersonDAO(SQLHelper helper) throws DaoException{
		super(helper);
		personTransformer = new PersonTransformer();
	}
	
	public List<Person> getPersonFromName(String name) throws DaoException{
		List<Person> result = new ArrayList<Person>();
		try{
			ResultSet resultSet = helper.selectRow(PersonTransformer.PERSON_TABLE_NAME, PersonTransformer.NAME_COL_NAME+"='"+name+"'");
			while(resultSet.next()){
				Person person = personTransformer.getObject(resultSet);
				result.add(person);
			}
			resultSet.close();
		}catch(SQLException e){
			throw new DaoException("Error when getting person with name = " + name + " : "+ e);
		}
		return result;
	}
	
	public List<Person> getAllPersons() throws DaoException{
		List<Person> result = new ArrayList<Person>();
		try{
			ResultSet rs = helper.selectRow(PersonTransformer.PERSON_TABLE_NAME, null);

			while(rs.next()){
				Person person = personTransformer.getObject(rs);
				result.add(person);
			}
			rs.close();
		}catch(SQLException e){
			throw new DaoException("Error when getting persons : "+ e);
		}
		return result;
	}
	
	@Override
	public void save(Person person)throws DaoException{
		try{
			if(getPersonFromName(person.getName()).isEmpty()){
				helper.insertRow(PersonTransformer.PERSON_TABLE_NAME, personTransformer.getValuesMap(person));
			}else{
				helper.updateRow(PersonTransformer.PERSON_TABLE_NAME, personTransformer.getValuesMap(person), PersonTransformer.NAME_COL_NAME+"='"+person.getName()+"'");
			}
		}catch(SQLException e){
			throw new DaoException("Error when saving person " + person + " : " + e);
		}
	}

	@Override
	public void delete(Person person) throws DaoException{
		try{
			helper.deleteRow(PersonTransformer.PERSON_TABLE_NAME, PersonTransformer.NAME_COL_NAME + " = '" + person.getName() + "';");
		}catch(SQLException e){
			throw new DaoException("Error when deleting person " + person + " : " + e);
		}
	}
	
	@Override
	public void create() throws DaoException {
		try{
			personTransformer.createTable(helper);
		}catch (SQLException e){
			throw new DaoException("Error when creating table " + e);
		}
	}
	
	@Override
	public void destroy() throws DaoException{
		try{
			helper.dropTable(PersonTransformer.PERSON_TABLE_NAME);
		}catch(SQLException e){
			throw new DaoException("Error when dropping the table "+e);
		}
	}
}
