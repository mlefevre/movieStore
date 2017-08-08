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
	BE("BE","Belgique"),
	UK("UK","United Kingdoms"),
	DEFAULT("UNK","Unknown");
	
	private String symbol;
	private String name;

	Country(String symbol, String name){
		this.symbol = symbol;
		this.name = name;
	}
	
	static public Country getCountry(String name){
		Country retValue = DEFAULT;
		for(Country c : values()){
			if (c.name.equals(name)){
				retValue = c;
			}
		}
		return retValue;
	}
	
	public String getName(){
		return this.name;
	}
	
}
