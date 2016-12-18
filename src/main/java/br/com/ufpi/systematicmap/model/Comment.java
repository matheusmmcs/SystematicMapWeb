/**
 * 
 */
package br.com.ufpi.systematicmap.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import br.com.caelum.vraptor.serialization.SkipSerialization;

/**
 * @author Gleison Andrade
 *
 */
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Lob
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@SkipSerialization
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "mapStudy_id")
	@SkipSerialization
	private MapStudy mapStudy;
	
	@ManyToOne
	@JoinColumn(name = "article_id")
	@SkipSerialization
	private Article article;

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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
}
