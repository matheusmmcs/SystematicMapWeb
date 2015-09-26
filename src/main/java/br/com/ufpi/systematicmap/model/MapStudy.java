package br.com.ufpi.systematicmap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.ufpi.systematicmap.dao.ArticleDao;
import br.com.ufpi.systematicmap.model.enums.Roles;


@Entity 
public class MapStudy implements Serializable{

	private static final long serialVersionUID = 1;
	
	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	@Size(min = 3)
	private String title;

    @NotEmpty
    @Size(min = 3)
	private String description;
    
    private boolean remove;
    
    //FIXME	    
    @OneToMany(mappedBy = "mapStudy", cascade = CascadeType.ALL)
	private Set<UsersMapStudys> usersMapStudys = new HashSet<>();
    
    @OneToMany(mappedBy="mapStudy")
	private Set<Article> articles = new HashSet<>();
    
    @OneToMany(mappedBy="mapStudy")
	private Set<InclusionCriteria> inclusionCriterias = new HashSet<>();
    
    @OneToMany(mappedBy="mapStudy")
	private Set<ExclusionCriteria> exclusionCriterias = new HashSet<>();
    
    @OneToMany(mappedBy="mapStudy")
	private Set<Evaluation> evaluations= new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Set<InclusionCriteria> getInclusionCriterias() {
		return inclusionCriterias;
	}

	public void setInclusionCriterias(Set<InclusionCriteria> inclusionCriterias) {
		this.inclusionCriterias = inclusionCriterias;
	}

	public Set<ExclusionCriteria> getExclusionCriterias() {
		return exclusionCriterias;
	}

	public void setExclusionCriterias(Set<ExclusionCriteria> exclusionCriterias) {
		this.exclusionCriterias = exclusionCriterias;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	public void addArticle(Article article) {
		getArticles().add(article);
		article.setMapStudy(this);
	}
	
	public void addInclusionCriteria(InclusionCriteria criteria) {
		getInclusionCriterias().add(criteria);
		criteria.setMapStudy(this);
	}
	
	public void addExclusionCriteria(ExclusionCriteria criteria) {
		getExclusionCriterias().add(criteria);
		criteria.setMapStudy(this);
	}
	
	public String percentEvaluated(Double percentEvaluated){
		return String.format("%.2f", percentEvaluated);
	}
	
	public Double percentEvaluatedDouble(ArticleDao articleDao, User user){
		int total = articleDao.countArticleNotRefined(this).intValue(),
		    toEvaluate = articleDao.countArticleToEvaluate(user, this).intValue();
		 
		BigDecimal tot = new BigDecimal(total);
		BigDecimal dontEval = new BigDecimal(toEvaluate);
		BigDecimal bigdecimal;
		
		if(tot.equals(BigDecimal.ZERO)){
			bigdecimal = BigDecimal.ZERO;
		}else{
			if(dontEval.equals(BigDecimal.ZERO)){
				bigdecimal = new BigDecimal(100);
			}else{
				bigdecimal = tot.subtract(dontEval).divide(tot, 100, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
			}
		}
		
		return bigdecimal.doubleValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MapStudy other = (MapStudy) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MapStudy [id=" + id + ", title=" + title + 
			", description=" + description + "]";
	}

	/**
	 * @return the remove
	 */
	public boolean isRemove() {
		return remove;
	}

	/**
	 * @param remove the remove to set
	 */
	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	/**
	 * @return the usersMapStudys
	 */
	public Set<UsersMapStudys> getUsersMapStudys() {
		return usersMapStudys;
	}

	/**
	 * @param usersMapStudys the usersMapStudys to set
	 */
	public void setUsersMapStudys(Set<UsersMapStudys> usersMapStudys) {
		this.usersMapStudys = usersMapStudys;
	}

	public void addUser(User user) {
		UsersMapStudys usersMapStudys = new UsersMapStudys();
		usersMapStudys.setUser(user);
		usersMapStudys.setMapStudy(this);
		usersMapStudys.setRole(Roles.PARTICIPANT);
		getUsersMapStudys().add(usersMapStudys);
	}
}
