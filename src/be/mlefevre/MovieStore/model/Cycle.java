/**
 * 
 */
package be.mlefevre.MovieStore.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represent a movie's cycle.
 * @author lefevre
 *
 */
public class Cycle {

	private static final int KEY_SIZE = 8;
	
	private String name;
	private Map<String, Movie> elements;
	private List<String> storyTellingChronology;
	private List<String> productionChronology;
	
	Cycle(){
		this.name = "";
		elements = new HashMap<String, Movie>();
		storyTellingChronology = new ArrayList<String>();
		productionChronology = new ArrayList<String>();
	}
	
	Cycle(String name){
		this();
		this.name = name;
	}
	
	public int getLength(){
		return elements.values().size();
	}

	public List<Genre> getGenres(){
		List<Genre> returnList = new ArrayList<Genre>();
		for(Movie movie : elements.values()){
			Genre genre = movie.getGenre();
			if(! returnList.contains(genre)){
				returnList.add(genre);
			}
		}
		return returnList;
	}
	
	/**
	 * Add a movie to the cycle, specifying the rank in the story 
	 * and the production chronologies.<p>
	 * If one of the rank is too large (ie.larger than the current cycle size),
	 * the movie is added at the end of the cycle. 
	 * @param movie The movie to be added.
	 * @param storyOrder the story chronology rank.
	 * @param productionOrder the production rank.
	 */
	public void addMovie(Movie movie, int storyOrder, int productionOrder){
		String movieId = getMovieId(movie); 
		elements.put(movieId, movie);
		setStoryOrder(storyOrder, movieId);
		setProductionOrder(productionOrder, movieId);
	}
	
	/**
	 * This method allows to remove a movie from the cycle.
	 * @param movie The movie to remove.
	 */
	public void removeMovie(Movie movie){
		String movieKey = getMovieId(movie);
		
		elements.remove(movieKey);
		storyTellingChronology.remove(movieKey);
		productionChronology.remove(movieKey);
	}
	
	/**
	 * Set a new value for the production or story order
	 * of a movie contained in the cycle.
	 * <p>
	 * If the movie doesn't exist in the cycle, nothing happens.
	 * <p>
	 * If the given order is too big, the movie is put at the end.
	 * @param movie The movie to move
	 * @param storyOrder the new rank of story order
	 * @param prodOrder the new rank of production order.
	 */
	public void changeMovieOrder(Movie movie, int storyOrder, int prodOrder){
		
		if(elements.containsValue(movie)){
			String movieKey = getMovieId(movie);
			storyTellingChronology.remove(movieKey);
			setStoryOrder(storyOrder, movieKey);
			productionChronology.remove(movieKey);
			setProductionOrder(prodOrder, movieKey);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, Movie> getElements() {
		return elements;
	}

	public List<String> getStoryTellingChronology() {
		return storyTellingChronology;
	}

	public List<String> getProductionChronology() {
		return productionChronology;
	}

	/**
	 * Get a new movie id based on the {@link Movie#hashCode}
	 * @param movie
	 * @return
	 */
	private String	getMovieId(Movie movie){
		return String.valueOf(movie.hashCode());
	}
	
	/**
	 * @param productionOrder
	 * @param movieId
	 */
	private void setProductionOrder(int productionOrder, String movieId) {
		if(productionOrder >= productionChronology.size()){
			productionOrder = productionChronology.size();
		}
		productionChronology.add(productionOrder,movieId);
	}
	
	/**
	 * @param storyOrder
	 * @param movieId
	 */
	private void setStoryOrder(int storyOrder, String movieId) {
		if(storyOrder >= storyTellingChronology.size()){
			storyOrder = storyTellingChronology.size();
		}
		storyTellingChronology.add(storyOrder, movieId);
	}
	
}
