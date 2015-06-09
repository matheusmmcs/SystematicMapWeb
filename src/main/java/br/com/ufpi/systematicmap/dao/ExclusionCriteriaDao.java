package br.com.ufpi.systematicmap.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.ExclusionCriteria;

@RequestScoped
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