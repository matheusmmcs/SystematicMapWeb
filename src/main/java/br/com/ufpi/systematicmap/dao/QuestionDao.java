/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.Question;

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
	
}
