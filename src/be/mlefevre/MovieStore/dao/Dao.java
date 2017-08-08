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
	
	public abstract void save(T entity) throws DaoException;
	public abstract void create() throws DaoException;
	public abstract void destroy() throws DaoException;
}
