package be.mlefevre.MovieStore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent a person (living or dead).
 * @author lefevre
 */

public class Person {
	
	private String name;
	private String firstName;
	private Date dateOfBirth;
	private Country origin;
	private List<String> alternativeNames;
	
	public void addAlternativeName(String name){
		if(alternativeNames == null){
			alternativeNames = new ArrayList<String>();
		}
		alternativeNames.add(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Country getOrigin() {
		return origin;
	}
	public void setOrigin(Country origin) {
		this.origin = origin;
	}
	public List<String> getAlternativeNames() {
		return alternativeNames;
	}
	public void setAlternativeNames(List<String> alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (firstName != null)
			builder.append("firstName=").append(firstName).append(", ");
		if (dateOfBirth != null)
			builder.append("dateOfBirth=").append(dateOfBirth).append(", ");
		if (origin != null)
			builder.append("origin=").append(origin).append(", ");
		if (alternativeNames != null)
			builder.append("alternativeNames=").append(alternativeNames);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Person other = (Person) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null) {
				return false;
			}
		} else if (!dateOfBirth.equals(other.dateOfBirth)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (origin != other.origin) {
			return false;
		}
		return true;
	}
}
