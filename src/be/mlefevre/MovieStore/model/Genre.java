/**
 * 
 */
package be.mlefevre.MovieStore.model;

/**
 * Enum containing existing movie genres.
 * @author lefevre
 *
 */
public enum Genre {

	WAR("guerre"),
	COMEDY("comédie");
	
	private String frName;
	
	Genre(String name){
		this.frName = name;
	}
	
	public String getFrName(){
		return this.frName;
	}
}
