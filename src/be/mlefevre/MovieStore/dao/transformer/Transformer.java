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
	
	public HashMap<String, String> getValuesMap(T entity);
	
	public T getObject(ResultSet result)throws SQLException, DaoException;
	
	public void createTable(SQLHelper helper) throws SQLException;

}
