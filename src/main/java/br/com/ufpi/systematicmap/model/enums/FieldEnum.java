package br.com.ufpi.systematicmap.model.enums;

public enum FieldEnum {
	TITLE("Título"), ABS("Abstrat"), KEYS("Palavras chave");
	
	private String description;
	
	private FieldEnum(String description){
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
