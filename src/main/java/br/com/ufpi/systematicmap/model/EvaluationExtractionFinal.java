/**
 * 
 */
package br.com.ufpi.systematicmap.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.serialization.SkipSerialization;

/**
 * @author Gleison Andrade
 *
 */
@Entity
@Table(name="evaluation_extraction_final")
public class EvaluationExtractionFinal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "mapStudy_id")
	private MapStudy mapStudy;
	
	@ManyToOne
	@JoinColumn(name = "article_id")
	@SkipSerialization
	private Article article;
	
	@ManyToOne
	@JoinColumn(name = "alternative_id")
	private Alternative alternative;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

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
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @return the alternative
	 */
	public Alternative getAlternative() {
		return alternative;
	}

	/**
	 * @param alternative the alternative to set
	 */
	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}

	/**
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alternative == null) ? 0 : alternative.hashCode());
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result
				+ ((mapStudy == null) ? 0 : mapStudy.hashCode());
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
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
		EvaluationExtractionFinal other = (EvaluationExtractionFinal) obj;
		if (alternative == null) {
			if (other.alternative != null)
				return false;
		} else if (!alternative.equals(other.alternative))
			return false;
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		if (mapStudy == null) {
			if (other.mapStudy != null)
				return false;
		} else if (!mapStudy.equals(other.mapStudy))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EvaluationExtractionFinal [id=" + id + ", mapStudy=" + mapStudy.getId()
				+ ", article=" + article.getId() + ", alternative=" + alternative.getId()
				+ ", question=" + question.getId() + "]";
	}
	
	

}
