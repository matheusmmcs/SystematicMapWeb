package br.com.ufpi.systematicmap.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.ExclusionCriteria;

public class ExclusionCriteriaDao extends Dao<ExclusionCriteria> {
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected ExclusionCriteriaDao() {
		this(null);
	}

	@Inject
	public ExclusionCriteriaDao(EntityManager entityManager) {
		super(entityManager);
	}
	
}