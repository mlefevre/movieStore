/**
 * 
 */
package be.mlefevre.MovieStore.model;

/**
 * @author lefevre
 *
 */
public enum Country {
	USA("US","United States of America"),
	FR("FR","France"),
	BE("BE","Belgique");
	
	private String symbol;
	private String name;

	Country(String symbol, String name){
		this.symbol = symbol;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
