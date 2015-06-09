package br.com.ufpi.systematicmap.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ufpi.systematicmap.model.MapStudy;

@RequestScoped
public class MapStudyDao extends Dao<MapStudy> {

	private final EntityManager entityManager;

	/**
	 * @deprecated CDI eyes only
	 */
	protected MapStudyDao() {
		this(null);
	}
	
	@Inject
	public MapStudyDao(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	public void add(MapStudy map) {
		entityManager.persist(map);
	}

	public List<MapStudy> searchSimilarTitle(String title) {
		List<MapStudy> musics = entityManager
			.createQuery("select m from MapStudy m where lower(m.title) like lower(:title)", MapStudy.class)
				.setParameter("title", "%" + title+ "%")
				.getResultList();
		return musics;
	}
	
	

}