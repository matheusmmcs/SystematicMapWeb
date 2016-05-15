/**
 * 
 */
package br.com.ufpi.systematicmap.model.enums;

/**
 * @author Gleison Andrade
 *
 */
public enum QuestionType {
	SIMPLE("Texto"), LIST("Listagem"), MULT("Multiplas opções");
	
	private String description;
	
	private QuestionType(String description){
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
