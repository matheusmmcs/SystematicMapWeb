/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ufpi.systematicmap.model.Alternative;
import br.com.ufpi.systematicmap.model.EvaluationExtraction;
import br.com.ufpi.systematicmap.model.Question;

/**
 * @author Gleison Andrade
 *
 */
public class EvaluationExtrationDao extends Dao<EvaluationExtraction>{
	/**
	 * @deprecated CDI eyes only
	 */
	protected EvaluationExtrationDao() {
		this(null);
	}

	@Inject
	public EvaluationExtrationDao(EntityManager entityManager) {
		super(entityManager);
	}

	public int removeAlternative(Alternative alternative) {
		 Query query = entityManager.createQuery("DELETE FROM EvaluationExtraction ee WHERE ee.alternative = :alternative");
		 int deletedCount = query.setParameter("alternative", alternative).executeUpdate();
		 return deletedCount;
	}

	public int removeQuestion(Question question) {
		Query query = entityManager.createQuery("DELETE FROM EvaluationExtraction ee WHERE ee.question = :question");
		int deletedCount = query.setParameter("question", question).executeUpdate();
		return deletedCount;
	}

}
