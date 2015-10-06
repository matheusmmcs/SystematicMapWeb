/**
 * 
 */
package br.com.ufpi.systematicmap.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.ufpi.systematicmap.model.enums.Roles;

/**
 * @author Gleison
 *
 */
@Entity(name = "users_mapStudys")
public class UsersMapStudys {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "mapStudy_id")
	private MapStudy mapStudy;
	
	private boolean removed;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the mapStudy
	 */
	public MapStudy getMapStudy() {
		return mapStudy;
	}

	/**
	 * @param mapStudy the mapStudy to set
	 */
	public void setMapStudy(MapStudy mapStudy) {
		this.mapStudy = mapStudy;
	}

	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

}
