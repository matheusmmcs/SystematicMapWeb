package br.com.ufpi.systematicmap.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "required")
	@Length(min = 3, max = 20)
	@Pattern(regexp = "[a-zA-Z0-9_]+", message = "invalid_login")
	private String login;

	@NotNull(message = "required")
	@Length(min = 6)
	private String password;
	
	private String recoveryCode;
	
	@NotNull(message = "required")
	@Email(message = "invalid_mail")
	private String email;

	@NotNull(message = "required")
	@Length(min = 3, max = 100)
	private String name;

	@ManyToMany
	@JoinTable(name="users_mapStudys", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="mapStudy_id")})
	private Set<MapStudy> mapStudys = new HashSet<>();
//FIXME	
//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//	private Collection<UsersMapStudys> usersAnsMapStudys;
	
	@OneToMany(mappedBy="user")
	private Set<Evaluation> evaluations = new HashSet<>();

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Set<MapStudy> getMapStudys() {
		return mapStudys;
	}

	public void setMapStudys(Set<MapStudy> mapStudys) {
		this.mapStudys = mapStudys;
	}

	public void add(MapStudy mapStudys) {
		getMapStudys().add(mapStudys);
		mapStudys.getMembers().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the recoveryCode
	 */
	public String getRecoveryCode() {
		return recoveryCode;
	}

	/**
	 * @param recoveryCode the recoveryCode to set
	 */
	public void setRecoveryCode(String recoveryCode) {
		this.recoveryCode = recoveryCode;
	}
	
//	/**
//	 * @return the usersAnsMapStudys
//	 */
//	public Collection<UsersMapStudys> getUsersAnsMapStudys() {
//		return usersAnsMapStudys;
//	}
//
//	/**
//	 * @param usersAnsMapStudys the usersAnsMapStudys to set
//	 */
//	public void setUsersAnsMapStudys(Collection<UsersMapStudys> usersAnsMapStudys) {
//		this.usersAnsMapStudys = usersAnsMapStudys;
//	}
	
}
