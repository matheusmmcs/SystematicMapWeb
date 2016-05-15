/**
 * 
 */
package br.com.ufpi.systematicmap.model.enums;

/**
 * @author Gleison
 *
 */
public enum Roles {
	CREATOR("Criador"), PARTICIPANT("Participante");
	
	private String description;
	
	private Roles(String description){
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
