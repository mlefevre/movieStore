/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a column in a DB.
 * @author lefevre
 */
public class ColumnDB {
	
	private final String SEPARATOR = "\t";

	private String name;
	private String type;
	private List<String> conditions;
	
	public ColumnDB(){
		super();
		this.conditions = new ArrayList<String>();
	}
	public ColumnDB(String name, String type){
		this.name = name;
		this.type = type;
		this.conditions = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getConditions() {
		return conditions;
	}
	public void setConditions(List<String> conditions) {
		this.conditions = conditions;
	}
	public void addCondition(String... conditions){
		for(String condition : conditions){
			this.conditions.add(condition);
		}
	}
	
	private String conditionsToString(){
		StringBuilder builder = new StringBuilder();
		for(String condition : conditions){
			builder.append(condition).append(SEPARATOR);
		}
		return builder.toString();
	}
	
	/**
	 * Format the column in the following output :<p>
	 * name	type	condition1	condition2	...	conditionx
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (name != null)
			builder.append(name).append(SEPARATOR);
		if (type != null)
			builder.append(type).append(SEPARATOR);
		if (conditions != null)
			builder.append(conditionsToString());
		return builder.toString();
	}
	
}
