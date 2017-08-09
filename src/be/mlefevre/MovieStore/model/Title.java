/**
 * 
 */
package be.mlefevre.MovieStore.model;

/**
 * Represent an artwork's title.
 * The work may have different title in different language but only one original title.
 * @author lefevre
 *
 */
public class Title {
	
	private String id;
	private String original;
	private String frenchVersion;
	private String englishVersion;
	
	private static int index = 0;
	
	public Title(){
		
		//TODO : solution temporaire : la db doit prendre cet index en charge.
		this.id = String.valueOf(index++);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getFrenchVersion() {
		return frenchVersion;
	}
	public void setFrenchVersion(String frenchVersion) {
		this.frenchVersion = frenchVersion;
	}
	public String getEnglishVersion() {
		return englishVersion;
	}
	public void setEnglishVersion(String englishVersion) {
		this.englishVersion = englishVersion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((englishVersion == null) ? 0 : englishVersion.hashCode());
		result = prime * result
				+ ((frenchVersion == null) ? 0 : frenchVersion.hashCode());
		result = prime * result
				+ ((original == null) ? 0 : original.hashCode());
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
		Title other = (Title) obj;
		if (original == null) {
			if (other.original != null)
				return false;
		} else if (!original.equals(other.original))
			return false;
		if (englishVersion == null) {
			if (other.englishVersion != null)
				return false;
		} else if (!englishVersion.equals(other.englishVersion))
			return false;
		if (frenchVersion == null) {
			if (other.frenchVersion != null)
				return false;
		} else if (!frenchVersion.equals(other.frenchVersion))
			return false;
		return true;
	}
	
	

}
