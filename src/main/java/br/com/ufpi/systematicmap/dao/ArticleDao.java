package br.com.ufpi.systematicmap.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.User;

public class ArticleDao extends Dao<Article> {
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected ArticleDao() {
		this(null);
	}

	@Inject
	public ArticleDao(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Article> getArticlesToEvaluate(User user, MapStudy mapStudy){
		List<Article> articles = entityManager
			.createQuery("select a from Article a where a.classification = null and a.mapStudy = :mapStudy and a.id not in (select e.article.id from Evaluation e where e.user = :user and e.mapStudy = :mapStudy) order by a.title asc", Article.class)
				.setParameter("user", user)
				.setParameter("mapStudy", mapStudy)
				.getResultList();
		return articles;
	}
	
	public List<Article> getArticlesEvaluated(User user, MapStudy mapStudy){
		List<Article> articles = entityManager
			.createQuery("select a from Article a where a.classification = null and a.mapStudy = :mapStudy and a.id in (select e.article.id from Evaluation e where e.user = :user and e.mapStudy = :mapStudy) order by a.title asc", Article.class)
				.setParameter("user", user)
				.setParameter("mapStudy", mapStudy)
				.getResultList();
		return articles;
	}
	
	public Long countArticleToEvaluate(User user, MapStudy mapStudy){
		Long count = entityManager
		.createQuery("select count(1) from Article a where a.classification = null and a.mapStudy = :mapStudy and a.id not in (select e.article.id from Evaluation e where e.user = :user and e.mapStudy = :mapStudy)", Long.class)
			.setParameter("user", user)
			.setParameter("mapStudy", mapStudy)
			.getSingleResult();
		return count;
		}
	
	public Long countArticleEvaluated(User user, MapStudy mapStudy){
		Long count = entityManager
		.createQuery("select count(1) from Article a where a.classification = null and a.mapStudy = :mapStudy and a.id in (select e.article.id from Evaluation e where e.user = :user and e.mapStudy = :mapStudy)", Long.class)
			.setParameter("user", user)
			.setParameter("mapStudy", mapStudy)
			.getSingleResult();
		return count;
	}
	
	
	public Long countArticleNotRefined(MapStudy mapStudy){
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);

		Root<Article> root = cq.from(Article.class);
		cq.select(qb.count(root));
		
		Predicate p = qb.conjunction();
        p = qb.and(qb.equal(root.get("mapStudy"), mapStudy), qb.isNull(root.get("classification")));
		cq.where(p);
		
		return entityManager.createQuery(cq).getSingleResult();
	}
}