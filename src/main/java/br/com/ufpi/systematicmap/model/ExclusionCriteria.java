package br.com.ufpi.systematicmap.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ExclusionCriteria  implements Serializable {

	private static final long serialVersionUID = 1;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "mapStudy_id")
	private MapStudy mapStudy;
	
	@ManyToMany(mappedBy = "exclusionCriterias")
	private Set<Evaluation> evaluations = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MapStudy getMapStudy() {
		return mapStudy;
	}

	public void setMapStudy(MapStudy mapStudy) {
		this.mapStudy = mapStudy;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	public void addEvaluation(Evaluation evaluation) {
		getEvaluations().add(evaluation);
		evaluation.addExclusion(this);
	}
	
}
