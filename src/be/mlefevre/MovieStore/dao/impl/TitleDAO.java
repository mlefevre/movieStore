/**
 * 
 */
package be.mlefevre.MovieStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;

import be.mlefevre.MovieStore.dao.Condition;
import be.mlefevre.MovieStore.dao.Dao;
import be.mlefevre.MovieStore.dao.SQLHelper;
import be.mlefevre.MovieStore.dao.Condition.Combination;
import be.mlefevre.MovieStore.dao.Condition.Type;
import be.mlefevre.MovieStore.dao.transformer.Transformer;
import be.mlefevre.MovieStore.dao.transformer.impl.PersonTransformer;
import be.mlefevre.MovieStore.dao.transformer.impl.TitleTransformer;
import be.mlefevre.MovieStore.exceptions.DaoException;
import be.mlefevre.MovieStore.model.Person;
import be.mlefevre.MovieStore.model.Title;

/**
 * @author lefevre
 *
 */
public class TitleDAO extends Dao<Title> {

	private Transformer<Title> transformer;
	
	public TitleDAO(SQLHelper helper) {
		super(helper);
		this.transformer = new TitleTransformer();
	}

	public List<Title> getTitleFromOriginal(String originalTitle)throws DaoException{
		List<Title> result = new ArrayList<Title>();
		try{
			Condition fromOriginal = new Condition();
			fromOriginal.setKey(TitleTransformer.ORIGINAL_COL_NAME);
			fromOriginal.setValue(originalTitle);
			fromOriginal.setType(Type.LIKE);
			fromOriginal.setCombination(Combination.AND);
			
			ResultSet rs = helper.selectRow(TitleTransformer.TITLE_TABLE_NAME, fromOriginal);
			while(rs.next()){
				Title t = transformer.getObject(rs);
				result.add(t);
			}
			rs.close();
		}catch(SQLException e){
			throw new DaoException("Error when getting titles like " + originalTitle);
		}
		return result;
	}
	
	public List<Title> getAllTitle() throws DaoException{
		List<Title> result = new ArrayList<Title>();
		try{
			ResultSet rs = helper.selectRow(TitleTransformer.TITLE_TABLE_NAME, null);

			while(rs.next()){
				Title t = transformer.getObject(rs);
				result.add(t);
			}
			rs.close();
		}catch(SQLException e){
			throw new DaoException("Error when getting titles : "+ e);
		}
		return result;
	}
	
	
	@Override
	public void save(Title title) throws DaoException {
		try{
			if(isTitleExist(title)){
				Condition fromId = new Condition();
				fromId.setKey(TitleTransformer.ID_COL_NAME);
				fromId.setValue(title.getId());
				fromId.setType(Type.EQUAL);
				fromId.setCombination(Combination.AND);
			
				helper.updateRow(TitleTransformer.TITLE_TABLE_NAME, transformer.getValuesMap(title), fromId);
			}else{
				helper.insertRow(TitleTransformer.TITLE_TABLE_NAME, transformer.getValuesMap(title));
			}
		}catch(SQLException e){
			throw new DaoException("Error when saving Title " + title + " : " + e);
		}
	}

	@Override
	public void delete(Title title) throws DaoException {
		try{
			helper.deleteRow(TitleTransformer.TITLE_TABLE_NAME, TitleTransformer.ID_COL_NAME + " = '" + title.getId() + "';");
		}catch(SQLException e){
			throw new DaoException("Error when deleting title " + title + " : " + e);
		}
	}

	@Override
	public void create() throws DaoException {
		try{
			transformer.createTable(helper);
		}catch(SQLException e){
			throw new DaoException("Error when creating table " + TitleTransformer.TITLE_TABLE_NAME);
		}
	}

	@Override
	public void destroy() throws DaoException {
		try{
			helper.dropTable(TitleTransformer.TITLE_TABLE_NAME);
		}catch(SQLException e){
			throw new DaoException("Error when deleting table " + TitleTransformer.TITLE_TABLE_NAME);
		}
	}
	
	private boolean isTitleExist(Title title)throws DaoException{
		boolean exist = false;
		String id = title.getId();
		try{
			Condition fromId = new Condition();
			fromId.setKey(TitleTransformer.ID_COL_NAME);
			fromId.setValue(id);
			fromId.setType(Type.EQUAL);
			fromId.setCombination(Combination.AND);
			
			ResultSet resultSet = helper.selectRow(TitleTransformer.TITLE_TABLE_NAME, fromId);
			exist = resultSet.isBeforeFirst();
			resultSet.close();
		}catch(SQLException e){
			throw new DaoException("Error when getting title " + title + " : "+ e);
		}
		return exist;
	}

}
