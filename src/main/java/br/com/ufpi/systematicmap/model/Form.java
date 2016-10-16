/**
 * 
 */
package br.com.ufpi.systematicmap.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import br.com.caelum.vraptor.serialization.SkipSerialization;
import br.com.ufpi.systematicmap.model.vo.QuestionVO;

/**
 * @author Gleison Andrade
 *
 */
@Entity
public class Form implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(mappedBy="form")
	@SkipSerialization
	private MapStudy mapStudy;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("name")
	private Set<Question> questions = new HashSet<>();

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
	 * @return the questions
	 */
	public Set<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	/**
	 *
	 * @param question
	 */
	public void addQuestion(Question question){
//		if (!this.questions.contains(question)){
			this.questions.add(question);
//		}		
	}
	
	public void addQuestion(List<Question> questions){
		for (Question question : questions) {
			this.questions.add(question);
//			this.questions.addAll(questions);
		}
	}
	
	public Set<Alternative> getAlternatives(String name, Long id){
		for (Question question : questions) {
			if (question.getId().equals(id) || question.getName().equals(name)) return question.getAlternatives();
		}		
		return null;
	}
	
	private Question containsQuestion(Question question){
		for (Question quest : questions) {
			if(quest.equals(question)){
				return quest;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Form [id=" + id + ", questions=" + questions + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mapStudy == null) ? 0 : mapStudy.hashCode());
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
		Form other = (Form) obj;
		if (mapStudy == null) {
			if (other.mapStudy != null)
				return false;
		} else if (!mapStudy.equals(other.mapStudy))
			return false;
		return true;
	}

	public void removeQuestion(Question q) {
		for (Question question : questions) {
			if (question.equals(q)){
				question.removeAlternatives();
				questions.remove(question);
			}
		}
		
	}

	/**
	 * Pega o questionVO e converte para questão e adiciona a lista de questões de formulário
	 * @param questionVO
	 */
	public void addQuestionVO(QuestionVO questionVO) {
		for (int i = 0; i < questionVO.getQuestions().size(); i++){
			Question question = new Question();
			question.setName(questionVO.getQuestions().get(i).getName());
			question.setType(questionVO.getQuestions().get(i).getType());
			question.addAlternative(questionVO.getQuestions().get(i).getAlternatives());
			addQuestion(question);
		}
	}
	
//	public void removeQuestion(Question question){
//		for (Question q : questions) {
//			if(q.equals(question)){
//				
//			}
//		}
//	}

//	public void clearQuestions() {
//		// TODO Auto-generated method stub	
//	}
	
	

}
