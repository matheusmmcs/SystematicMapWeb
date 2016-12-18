package br.com.ufpi.systematicmap.model.enums;

public enum ClassificationEnum {
	WITHOUT_AUTHORS("Sem Autores"), REPEAT("Duplicado"), WORDS_DONT_MATCH("Limiar Baixo"), WITHOUT_ABSTRACT("Sem Abstract");
	
	private String description;
	
	private ClassificationEnum(String description){
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
