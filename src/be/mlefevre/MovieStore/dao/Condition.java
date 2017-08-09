/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import java.util.Iterator;

/**
 * @author lefevre
 *
 */
public class Condition {

	private String key;
	private String value;
	private Type type;
	private Combination combination = Combination.AND;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public Combination getCombination() {
		return combination;
	}
	public void setCombination(Combination combination) {
		this.combination = combination;
	}

	public enum Type{
		EQUAL(" = ", "", ""),
		IN(" in ", "(", ")"),
		LIKE(" like ", "%", "%"),
		DEFAULT("unknown", null, null);
		
		private String separator;
		private String start;
		private String end;
		
		private Type(String type, String start, String end){
			this.separator = type;
			this.start = start;
			this.end = end;
		}
		
		public String getSeparator() {
			return separator;
		}
		public String getStart() {
			return start;
		}
		public String getEnd() {
			return end;
		}

//		public static Type getConditionType(String typeName){
//			Type type = DEFAULT;
//			for(Type t : Type.values()){
//				if(t.getSeparator().equals(typeName)){
//					type = t;
//					return type;
//				}
//			}
//			return type;
//		}
	}
	public enum Combination{
		AND(" and "),
		OR(" or ");
		
		private String label;
		
		private Combination(String label){
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}
}
