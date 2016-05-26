/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.SearchString;

/**
 * @author Gleison Andrade
 *
 */
@RequestScoped
public class SearchStringDao extends Dao<SearchString>{
	private EntityManager entityManager;

	/**
	 * @deprecated CDI eyes only
	 */
	protected SearchStringDao() {
		this(null);
	}

	@Inject
	public SearchStringDao(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

}
