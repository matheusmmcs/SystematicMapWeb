/**
 * 
 */
package br.com.ufpi.systematicmap.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.ufpi.systematicmap.model.enums.ArticleSourceEnum;

/**
 * @author Gleison Andrade
 *
 */
@Entity
public class SearchString {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "required")
	@Lob
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "mapStudy_id")
	private MapStudy mapStudy;
	
	@Enumerated(EnumType.STRING)
	private ArticleSourceEnum source;

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

	/**
	 * @return the mapStudy
	 */
	public MapStudy getMapStudy() {
		return mapStudy;
	}

	/**
	 * @param mapStudy the mapStudy to set
	 */
	public void setMapStudy(MapStudy mapStudy) {
		this.mapStudy = mapStudy;
	}

	/**
	 * @return the source
	 */
	public ArticleSourceEnum getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(ArticleSourceEnum source) {
		this.source = source;
	}
	
}
