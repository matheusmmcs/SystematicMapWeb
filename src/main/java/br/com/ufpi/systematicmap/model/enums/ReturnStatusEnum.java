package br.com.ufpi.systematicmap.model.enums;

public enum ReturnStatusEnum {
	SUCESSO("Sucesso"), ERRO("Erro");
	
	private String description;
	
	private ReturnStatusEnum(String description){
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
