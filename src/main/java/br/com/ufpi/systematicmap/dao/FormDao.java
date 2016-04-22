/**
 * 
 */
package br.com.ufpi.systematicmap.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.Form;

/**
 * @author Gleison Andrade
 *
 */
@RequestScoped
public class FormDao extends Dao<Form>{
	/**
	 * @deprecated CDI eyes only
	 */
	protected FormDao() {
		this(null);
	}

	@Inject
	public FormDao(EntityManager entityManager) {
		super(entityManager);
	}
}
