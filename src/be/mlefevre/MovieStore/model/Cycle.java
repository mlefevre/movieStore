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
		String movieId = getNewMovieId(movie); 
		elements.put(movieId, movie);
		if(storyOrder >= storyTellingChronology.size()){
			storyOrder = storyTellingChronology.size();
		}
		storyTellingChronology.add(storyOrder, movieId);
		if(productionOrder >= productionChronology.size()){
			productionOrder = productionChronology.size();
		}
		productionChronology.add(productionOrder,movieId);
	}
	
	/**
	 * This method allows to remove a movie from the cycle.
	 * @param movie The movie to remove.
	 */
	public void removeMovie(Movie movie){
		
	}
	
	public void changeMovieOrder(){
		// TODO
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
	 * Get a new movie id based on the original movie title.
	 * <p>
	 * If the movie title is "A new movie splendid title", the id will be :
	 * <ul>
	 * <li><i>anewmovi</i></li>
	 * <li><i>anewmovi1</i> if the previous id already exists in this cycle</li>
	 * <li><i>anewmovi2</i> if both previous ids already exist.</li>
	 * </ul>
	 * @param movie
	 * @return
	 */
	private String getNewMovieId(Movie movie){
		String movieTitle = movie.getTitle().getOriginal();
		String movieId = movieTitle.trim().toLowerCase();
		if(movieId.length()>KEY_SIZE){
			movieId = movieId.substring(0, KEY_SIZE);
		}
		return checkMovieId(movieId,0);
	}
	
	private String checkMovieId(String movieId, int recCount){
		recCount++;
		if(elements.keySet().contains(movieId)){
			String idToCheck = movieId.substring(0,KEY_SIZE)
					.concat(String.valueOf(recCount));
			movieId = checkMovieId(idToCheck, recCount);
		}
		return movieId;
	}
}
