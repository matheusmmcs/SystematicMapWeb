package br.com.ufpi.systematicmap.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.utils.GenerateHashPasswordUtil;

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
					.setParameter("password", GenerateHashPasswordUtil.generateHash(password))
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
	
	public List<User> findUserName(String name) {	 
		List<User> users = entityManager
				.createQuery("select u from User u where u.name like :name", User.class)
					.setParameter("name", "%" + name + "%").getResultList();
		return users;
	}
	
	public User findEmail(String mail){
		TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email = :mail", User.class);
		query.setParameter("mail", mail);
		
		List<User> listUser = query.getResultList();
		
		if (listUser.isEmpty())
			return null;
		else
			return listUser.get(0);
	}
	
	public User findCodeRecovery(String code){
		User user = entityManager.createQuery("select u from User u where u.recoveryCode = :recoverycode", User.class)
					.setParameter("recoverycode", code)
					.getSingleResult();
		return user;
	}
	
	public void clearAllCodeRecovery(){
			
	}

	public boolean containsUserWithEMail(String email) {
		Long count = entityManager
				.createQuery("select count(u) from User u where u.email = :email", Long.class)
				.setParameter("email", email)
				.getSingleResult();
		return count > 0;
	}

	
//	public JsonArray findUserNameJson(String name) {
//		JsonArray users = new JsonArray();
//		
//		List<User> listUsers = findUserName(name);
//		
//		for (User user : listUsers) {
//			JsonObject jsonObject = new JsonObject();
//			jsonObject.addProperty("label", user.getName());
//			jsonObject.addProperty("value", user.getId());
////			jsonObject.addProperty("date", user);
//			users.add(jsonObject);
//		}
//		
//		return users;
//	}

}