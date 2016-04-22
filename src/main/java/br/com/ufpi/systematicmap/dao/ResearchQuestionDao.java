/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.ResearchQuestion;

/**
 * @author Gleison Andrade
 *
 */
@RequestScoped
public class ResearchQuestionDao extends Dao<ResearchQuestion>{
	private final EntityManager entityManager;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected ResearchQuestionDao() {
		this(null);
	}

	@Inject
	public ResearchQuestionDao(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

}
