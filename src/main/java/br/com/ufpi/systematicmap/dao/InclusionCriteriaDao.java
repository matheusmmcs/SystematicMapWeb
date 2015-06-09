package br.com.ufpi.systematicmap.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.InclusionCriteria;

@RequestScoped
public class InclusionCriteriaDao extends Dao<InclusionCriteria> {
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected InclusionCriteriaDao() {
		this(null);
	}

	@Inject
	public InclusionCriteriaDao(EntityManager entityManager) {
		super(entityManager);
	}
	
}