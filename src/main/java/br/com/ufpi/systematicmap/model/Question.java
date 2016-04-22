/**
 * 
 */
package br.com.ufpi.systematicmap.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import br.com.ufpi.systematicmap.model.enums.QuestionType;

/**
 * @author Gleison Andrade
 *
 */
@Entity
public class Question {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Lob
	private String name;
	
	@NotNull(message = "question.type.required")
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	
	@OneToMany(mappedBy = "question", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("value")
	private Set<Alternative> alternatives = new HashSet<>();
	
	public void addAlternative(Alternative alternative){
			this.alternatives.add(alternative);
			alternative.setQuestion(this);
			System.out.println("Add: " + alternative + " Size: " + this.alternatives.size());
	}
	
	public void removeAlternative(Alternative alternative){
		this.alternatives.remove(alternative);
		alternative.setQuestion(null);
		System.out.println("Remove: " + alternative + " Size: " + this.alternatives.size());
}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the alternatives
	 */
	public Set<Alternative> getAlternatives() {
		return alternatives;
	}

	/**
	 * @param alternatives the alternatives to set
	 */
	public void setAlternatives(Set<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	/**
	 * @return the type
	 */
	public QuestionType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(QuestionType type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Question [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void removeAlternatives() {
		this.alternatives.clear();		
	}

	public void addAlternative(Set<Alternative> alternatives2) {
		for (Alternative alternative : alternatives2) {
			addAlternative(alternative);
		}
	}
	
}
