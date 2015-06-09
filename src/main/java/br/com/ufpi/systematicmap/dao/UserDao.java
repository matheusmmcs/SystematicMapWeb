package br.com.ufpi.systematicmap.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.User;

@RequestScoped
public class UserDao extends Dao<User> {
	
	private final EntityManager entityManager;

	/**
	 * @deprecated CDI eyes only
	 */
	protected UserDao() {
		this(null);
	}

	@Inject
	public UserDao(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	public User find(String login, String password) {
		try {
			User user = entityManager
				.createQuery("select u from User u where u.login = :login and u.password = :password", User.class)
					.setParameter("login", login)
					.setParameter("password", password)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	public User find(String login) {
		User user = entityManager
				.createQuery("select u from User u where u.login = :login", User.class)
					.setParameter("login", login)
					.getSingleResult();
		return user;
	}
	
	public boolean containsUserWithLogin(String login) {
		Long count = entityManager
				.createQuery("select count(u) from User u where u.login = :login", Long.class)
				.setParameter("login", login)
				.getSingleResult();
		return count > 0;
	}
	
	public List<User> mapStudyUsers(MapStudy mapStudy) {
		List<User> users = entityManager
				.createQuery("select distinct u from User u inner join u.mapStudys m where m = :mapStudy", User.class)
					.setParameter("mapStudy", mapStudy)
					.getResultList();
		return users;
	}
	
	public List<User> mapStudyArentUsers(MapStudy mapStudy) {
		List<User> users = entityManager
				.createQuery("select distinct u from User u where u.id not in (select distinct u2.id from User u2 inner join u2.mapStudys m where m = :mapStudy)", User.class)
					.setParameter("mapStudy", mapStudy)
					.getResultList();
		return users;
	}

}