package br.com.ufpi.systematicmap.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;

@Dependent
public abstract class Dao <T> {
	
	    private Class<T> entityClass;
	    protected final EntityManager entityManager;

		/**
		 * @deprecated CDI eyes only
		 */
		protected Dao() {
			this(null);
		}

		@Inject
		public Dao(EntityManager entityManager) {
			this.entityManager = entityManager;
		}
	 
	    @SuppressWarnings("unchecked")
		public Class<T> getEntityClass() {
	        if (entityClass == null) {
	            //only works if one extends BaseDao, we will take care of it with CDI
	            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	        }
	        return entityClass;
	    }
	 
	    public void setEntityClass(Class<T> entityClass) {
	        this.entityClass = entityClass;
	    }  
	 
	    //utility database methods
	    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	    public T find(Long id) {
	        return (T) this.entityManager.find(getEntityClass(), id);
	    }
	 
	    public void delete(Long id) {
	        Object ref = this.entityManager.getReference(getEntityClass(), id);
	        this.entityManager.remove(ref);
	    }
	    
	    public void delete(T t) {
	        this.entityManager.remove(t);
	    }
	 
	    public T update(T t) {
	        return (T) this.entityManager.merge(t);
	    }
	 
	    public void insert(T t) {
	        this.entityManager.persist(t);
	    }
	 
	    @SuppressWarnings("unchecked")
		@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	    public List<T> findAll() {
	        return (List<T>) entityManager.createQuery("Select entity FROM "+getEntityClass().getSimpleName() +" entity").getResultList();
	    }
	    
	    public void refresh(T t) {
			getSession().refresh(t);
		}
		
		private Session getSession() {
			return entityManager.unwrap(Session.class);
		}
	
}
