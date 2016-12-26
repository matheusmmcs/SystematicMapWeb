/**
 * 
 */
package br.com.ufpi.systematicmap.model.vo;

import java.util.List;

/**
 * @author Gleison Andrade
 *
 */
public class ExtractionFinalVO {
	private Long questionId;
	private List<Long> alternatives;
	
	public ExtractionFinalVO() {
		
	}
	
	/**
	 * @param questionId
	 * @param alternatives
	 */
	public ExtractionFinalVO(Long questionId, List<Long> alternatives) {
		super();
		this.questionId = questionId;
		this.alternatives = alternatives;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the alternatives
	 */
	public List<Long> getAlternatives() {
		return alternatives;
	}

	/**
	 * @param alternatives the alternatives to set
	 */
	public void setAlternatives(List<Long> alternatives) {
		this.alternatives = alternatives;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExtractionFinalVO [questionId=" + questionId
				+ ", alternatives=" + alternatives + "]";
	}
	
	

}
