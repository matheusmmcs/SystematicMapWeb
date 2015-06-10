package br.com.ufpi.systematicmap.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.ufpi.systematicmap.model.enums.ClassificationEnum;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;

@Entity
public class Article implements Serializable {

	private static final long serialVersionUID = 1;

	//Falta o anotation do artigo
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "mapStudy_id")
	private MapStudy mapStudy;
	
	@OneToMany(mappedBy="article")
	private Set<Evaluation> evaluations = new HashSet<>();
	
	@Size(max=1000)
	private String author;
	
	@NotNull
	@Size(max=1000)
	private String title;
	
	private String journal;
	
	private Integer year;
	
	private Integer volume;
	
	@Size(max=500)
	private String pages;
	
	private String doi;
	
	private String note;
	
	private String url;
	
	private String docType;//
	
	private String source;
	
	private String language;//
	
	@Lob
	private String abstrct;//
	
	@Size(max=1000)
	private String keywords;
	
	//POINTS
	@Enumerated(EnumType.STRING)
	private ClassificationEnum classification;
	private String comments; 
	
	private Integer regexTitle = 0;
	private Integer regexAbs = 0;
	private Integer regexKeys = 0;
	
	@OneToOne
    @JoinColumn(name="same_article_id")
	private Article paperMinLevenshteinDistance;
	private Integer minLevenshteinDistance;
	
	public ClassificationEnum getClassification() {
		return classification;
	}

	public void setClassification(ClassificationEnum classification) {
		this.classification = classification;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getRegexTitle() {
		return regexTitle;
	}

	public void setRegexTitle(Integer regexTitle) {
		this.regexTitle = regexTitle;
	}

	public Integer getRegexAbs() {
		return regexAbs;
	}

	public void setRegexAbs(Integer regexAbs) {
		this.regexAbs = regexAbs;
	}

	public Integer getRegexKeys() {
		return regexKeys;
	}

	public void setRegexKeys(Integer regexKeys) {
		this.regexKeys = regexKeys;
	}

	public Article getPaperMinLevenshteinDistance() {
		return paperMinLevenshteinDistance;
	}

	public void setPaperMinLevenshteinDistance(Article paperMinLevenshteinDistance) {
		this.paperMinLevenshteinDistance = paperMinLevenshteinDistance;
	}

	public Integer getMinLevenshteinDistance() {
		return minLevenshteinDistance;
	}

	public void setMinLevenshteinDistance(Integer minLevenshteinDistance) {
		this.minLevenshteinDistance = minLevenshteinDistance;
	}

	public MapStudy getMapStudy() {
		return mapStudy;
	}

	public void setMapStudy(MapStudy mapStudy) {
		this.mapStudy = mapStudy;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAbstrct() {
		return abstrct;
	}

	public void setAbstrct(String abstrct) {
		this.abstrct = abstrct;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getEvaluationClassification(User user){
		for(Evaluation e : evaluations){
			if(e.getUser().equals(user)){
				return e.getClassification();
			}
		}
		return EvaluationStatusEnum.NOT_EVALUATED.toString();
	}
	
}
