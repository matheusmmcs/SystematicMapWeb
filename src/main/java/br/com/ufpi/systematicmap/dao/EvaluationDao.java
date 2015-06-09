package br.com.ufpi.systematicmap.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.Evaluation;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.User;

@RequestScoped
public class EvaluationDao extends Dao<Evaluation> {
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected EvaluationDao() {
		this(null);
	}

	@Inject
	public EvaluationDao(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Evaluation> getEvaluations(User user, MapStudy mapStudy){
		List<Evaluation> e = entityManager
			.createQuery("select e from Evaluation e where e.mapStudy = :mapStudy and e.user = :user order by e.article.title asc", Evaluation.class)
				.setParameter("user", user)
				.setParameter("mapStudy", mapStudy)
				.getResultList();
		return e;
	}
	
	public Evaluation getEvaluation(User user, MapStudy mapStudy, Article article){
		Evaluation e;
		try {
		    e = entityManager
					.createQuery("select e from Evaluation e where e.mapStudy = :mapStudy and e.user = :user and e.article = :article", Evaluation.class)
					.setParameter("user", user)
					.setParameter("mapStudy", mapStudy)
					.setParameter("article", article)
					.getSingleResult();
		} catch (NoResultException ex) {
			e = null;
		}
		return e;
	}
	
}