/**
 * 
 */
package be.mlefevre.MovieStore.dao.transformer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import be.mlefevre.MovieStore.dao.SQLHelper;
import be.mlefevre.MovieStore.exceptions.DaoException;

/**
 * @author lefevre
 *
 */
public interface Transformer<T> {
	
	/**
	 * Give a HashMap which can be use to prepare SQL request to represent the 
	 * given object in DB.
	 * @param entity
	 * @return a hasMap of the attributes of the given object.
	 */
	public HashMap<String, String> getValuesMap(T entity);
	
	/**
	 * Construct a object on the basis of sql request's result.
	 * @param result the request result
	 * @return An object of type <T>
	 * @throws SQLException
	 * @throws DaoException
	 */
	public T getObject(ResultSet result)throws SQLException, DaoException;
	
	/**
	 * Create an empty table in DB.
	 * @param helper the helper to be used to access the DB.
	 * @throws SQLException
	 */
	public void createTable(SQLHelper helper) throws SQLException;

}
