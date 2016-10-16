/**
 * 
 */
package br.com.ufpi.systematicmap.model.vo;

import java.util.List;

/**
 * @author Gleison Andrade
 *
 */
public class QuestionAndAlternativeCSV {
	private String questionName;
	private List<String> alternatives;

	/**
	 * @param questionName
	 * @param alternatives
	 */
	public QuestionAndAlternativeCSV(String questionName,
			List<String> alternatives) {
		super();
		this.questionName = questionName;
		this.alternatives = alternatives;
	}

	/**
	 * @return the questionName
	 */
	public String getQuestionName() {
		return questionName;
	}

	/**
	 * @param questionName
	 *            the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * @return the alternatives
	 */
	public List<String> getAlternatives() {
		return alternatives;
	}

	/**
	 * @param alternatives
	 *            the alternatives to set
	 */
	public void setAlternatives(List<String> alternatives) {
		this.alternatives = alternatives;
	}

}
