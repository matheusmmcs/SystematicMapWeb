package br.com.ufpi.systematicmap.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Length(min = 3, max = 20)
	@Pattern(regexp = "[a-z0-9_]+", message = "{invalid_login}")
	private String login;

	@NotNull
	@Length(min = 6, max = 20)
	private String password;

	@NotNull
	@Length(min = 3, max = 100)
	private String name;

	@ManyToMany
	@JoinTable(name="users_mapStudys", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="mapStudy_id")})
	private Set<MapStudy> mapStudys = new HashSet<>();
	
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
}
