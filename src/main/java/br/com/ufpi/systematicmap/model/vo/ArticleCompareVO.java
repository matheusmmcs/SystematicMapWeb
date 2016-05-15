package br.com.ufpi.systematicmap.model.vo;

import java.util.HashMap;
import java.util.List;

import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.Evaluation;
import br.com.ufpi.systematicmap.model.EvaluationExtraction;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;

public class ArticleCompareVO {
	
	private Article article;
	private List<User> users;
	private HashMap<User, Evaluation> evaluations;
	private HashMap<User, EvaluationExtraction> extractions;
	
	public ArticleCompareVO(Article article, List<User> users, HashMap<User, Evaluation> evaluations) {
        this.article = article;
        this.users = users;
        this.evaluations = evaluations;
    }

    public ArticleCompareVO(Article article, List<User> users, HashMap<User, Evaluation> evaluations, HashMap<User, EvaluationExtraction> extractions) {
        this.article = article;
        this.users = users;
        this.evaluations = evaluations;
        this.extractions = extractions;
    }
	
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public HashMap<User, Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(HashMap<User, Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	public Evaluation getEvaluation(User user){
		return evaluations.get(user);
	}
	
	public EvaluationStatusEnum getEvaluationClassification(User user){
		if(evaluations.get(user) != null){
			return evaluations.get(user).getEvaluationStatus();
		}
		return EvaluationStatusEnum.NOT_EVALUATED;
	}
	
	public HashMap<User, EvaluationExtraction> getExtractions() {
        return this.extractions;
    }

    public void setExtractions(HashMap<User, EvaluationExtraction> extractions) {
        this.extractions = extractions;
    }

    public EvaluationExtraction getEvaluateExtraction(User user) {
        if (this.extractions.get(user) != null) {
            return this.extractions.get(user);
        }
        return null;
    }

    public String toString() {
        return "ArticleCompareVO [article=" + this.article + ", users=" + this.users + ", evaluations=" + this.evaluations + ", extractions=" + this.extractions + "]";
    }
}
