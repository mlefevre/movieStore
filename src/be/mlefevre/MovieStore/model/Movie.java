/**
 * 
 */
package be.mlefevre.MovieStore.model;

import java.util.List;

/**
 * represents a movie
 * 
 * @author lefevre
 */

public class Movie {

	private Person director;
	private List<Person> actors;
	private Country origin;
	/* length of the movie in minutes */
	private int length;
	private Title title;
	private Genre genre;
//	private Date production ; // TODO
	
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	public List<Person> getActors() {
		return actors;
	}
	public void setActors(List<Person> actors) {
		this.actors = actors;
	}
	public Country getOrigin() {
		return origin;
	}
	public void setOrigin(Country origin) {
		this.origin = origin;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((director == null) ? 0 : director.hashCode());
		result = prime * result + length;
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (length != other.length)
			return false;
		if (origin != other.origin)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
