/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.Alternative;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.Question;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;
import br.com.ufpi.systematicmap.model.enums.QuestionType;

/**
 * @author Gleison Andrade
 *
 */
public class QuestionDao extends Dao<Question>{
	private final EntityManager entityManager;

	/**
	 * @deprecated CDI eyes only
	 */
	protected QuestionDao() {
		this(null);
	}
	
	@Inject
	public QuestionDao(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}
	
	public List<Alternative> getAlternativesFinalExtraction(Question question, MapStudy mapStudy) {
		List<Alternative> alternatives;// = new ArrayList<Alternative>();
		
		if (question.getType().equals(QuestionType.SIMPLE)){
			alternatives = entityManager
					.createQuery("select distinct(e.alternative) from EvaluationExtractionFinal e where e.article.classification = null and e.article.finalEvaluation = :finalEvaluation and e.mapStudy = :mapStudy and e.question = :question order by e.article.id asc", Alternative.class)
					.setParameter("finalEvaluation", EvaluationStatusEnum.ACCEPTED)
					.setParameter("question", question)
					.setParameter("mapStudy", mapStudy)
					.getResultList();
		}else{
			alternatives = new ArrayList<Alternative>(question.getAlternatives());
		}
		
		return alternatives;
	}
	
}
