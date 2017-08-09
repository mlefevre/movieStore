/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.sql.SQLException;

import be.mlefevre.MovieStore.exceptions.DaoException;

/**
 * @author lefevre
 *
 */
public abstract class Dao<T> {

	protected SQLHelper helper;
	
	public Dao(SQLHelper helper){
			this.helper = helper;
	}
	
	/**
	 * Save an entity in db.<p>
	 * If the entity doesn't exist in db, create a new one, otherwise edit the existing one.
	 * @param entity
	 * @throws DaoException
	 */
	public abstract void save(T entity) throws DaoException;
	
	/**
	 * Suppress the entity from the db.
	 * @param entity
	 * @throws DaoException
	 */
	public abstract void delete(T entity) throws DaoException;
	
	/**
	 * Create a table containing the entities.<p>
	 * If the table already exist, do nothing. 
	 * @throws DaoException
	 */
	public abstract void create() throws DaoException;
	
	/**
	 * Destroy (drop) the table containing the entities.<p>
	 * Do nothing if such a table doesn't exist.
	 * @throws DaoException
	 */
	public abstract void destroy() throws DaoException;
}
