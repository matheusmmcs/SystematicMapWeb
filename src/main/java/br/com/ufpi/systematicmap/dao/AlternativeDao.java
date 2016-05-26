/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.Alternative;

/**
 * @author Gleison Andrade
 *
 */
public class AlternativeDao extends Dao<Alternative>{
	/**
	 * @deprecated CDI eyes only
	 */
	protected AlternativeDao() {
		this(null);
	}

	@Inject
	public AlternativeDao(EntityManager entityManager) {
		super(entityManager);
	}

	public Alternative find(Long questionid, String value) {
		Alternative alternative = null;
		
		try {
			alternative = entityManager.createQuery("select a from Alternative a where a.question.id =:questionid and a.value = :value", Alternative.class).setParameter("questionid", questionid).setParameter("value", value).getSingleResult();
		} catch (Exception e) {
			e.getMessage();
		}
		
		return alternative;
	}

}
