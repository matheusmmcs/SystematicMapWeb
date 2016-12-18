/**
 * 
 */
package br.com.ufpi.systematicmap.model.vo;

import java.util.List;

import br.com.ufpi.systematicmap.model.Question;

/**
 * @author Gleison Andrade
 *
 */
public class QuestionVO {
	private Long mapid;
	private Long articleid;
	private List<Question> questions;
	private Long nextArticle;
	private String comment;
	/**
	 * @param mapid
	 * @param articleid
	 * @param questions
	 * @param nextArticle
	 */
	public QuestionVO(Long mapid, Long articleid, List<Question> questions, Long nextArticle, String comment) {
		super();
		this.mapid = mapid;
		this.articleid = articleid;
		this.questions = questions;
		this.nextArticle = nextArticle;
		this.comment = comment;
	}
	/**
	 * @return the mapid
	 */
	public Long getMapid() {
		return mapid;
	}
	/**
	 * @param mapid the mapid to set
	 */
	public void setMapid(Long mapid) {
		this.mapid = mapid;
	}
	/**
	 * @return the articleid
	 */
	public Long getArticleid() {
		return articleid;
	}
	/**
	 * @param articleid the articleid to set
	 */
	public void setArticleid(Long articleid) {
		this.articleid = articleid;
	}
	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	/**
	 * @return the nextArticle
	 */
	public Long getNextArticle() {
		return nextArticle;
	}
	/**
	 * @param nextArticle the nextArticle to set
	 */
	public void setNextArticle(Long nextArticle) {
		this.nextArticle = nextArticle;
	}
	
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QuestionVO [mapid=" + mapid + ", articleid=" + articleid + ", questions=" + questions + ", nextArticle="
				+ nextArticle + "]";
	}
}
