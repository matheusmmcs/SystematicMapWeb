package br.com.ufpi.systematicmap.model.enums;

public enum EvaluationStatusEnum {
	ACCEPTED("Aceito"), REJECTED("Rejeitado"), NOT_EVALUATED("Não Avaliado");
	
	private String description;
	
	private EvaluationStatusEnum(String description){
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
