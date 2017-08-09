/**
 * 
 */
package be.mlefevre.MovieStore.dao.transformer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import be.mlefevre.MovieStore.dao.ColumnDB;
import be.mlefevre.MovieStore.dao.SQLHelper;
import be.mlefevre.MovieStore.dao.transformer.Transformer;
import be.mlefevre.MovieStore.model.Title;

/**
 * @author lefevre
 *
 */
public class TitleTransformer implements Transformer<Title> {

	public static final String TITLE_TABLE_NAME = "TITLE";
	
	public static final String ID_COL_NAME 		= "id";
	public static final String ENGLISH_COL_NAME = "english";
	public static final String FRENCH_COL_NAME 	= "french";
	public static final String ORIGINAL_COL_NAME = "original";

	@Override
	public HashMap<String, String> getValuesMap(Title entity) {
		HashMap<String, String> row = new HashMap<String, String>();
		row.put(ENGLISH_COL_NAME, "'" + entity.getEnglishVersion() + "'");
		row.put(FRENCH_COL_NAME, "'" + entity.getFrenchVersion() + "'");
		row.put(ORIGINAL_COL_NAME, "'" + entity.getOriginal() + "'" );
		row.put(ID_COL_NAME, "'" + entity.getId() + "'");
		return row;
	}

	@Override
	public Title getObject(ResultSet result) throws SQLException{
		Title title = new Title();
		title.setId(result.getString(ID_COL_NAME));
		title.setOriginal(result.getString(ORIGINAL_COL_NAME));
		title.setFrenchVersion(result.getString(FRENCH_COL_NAME));
		title.setEnglishVersion(result.getString(ENGLISH_COL_NAME));
		
		return title;
	}

	@Override
	public void createTable(SQLHelper helper) throws SQLException {
		ColumnDB colId = new ColumnDB(ID_COL_NAME, "varChar");
		colId.addCondition("PRIMARY KEY","NOT NULL");
		ColumnDB colOriginal = new ColumnDB(ORIGINAL_COL_NAME,"varChar");
		colOriginal.addCondition("NOT NULL");
		ColumnDB colFrench = new ColumnDB(FRENCH_COL_NAME, "varChar");
		ColumnDB colEnglish = new ColumnDB(ENGLISH_COL_NAME, "varChar");
		
		helper.createTable(TITLE_TABLE_NAME,colId, colOriginal, colFrench, colEnglish);
	}

}
