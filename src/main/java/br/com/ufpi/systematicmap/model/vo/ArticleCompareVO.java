package br.com.ufpi.systematicmap.model.vo;

import java.util.HashMap;
import java.util.List;

import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.Evaluation;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;

public class ArticleCompareVO {
	
	private Article article;
	private List<User> users;
	private HashMap<User, Evaluation> evaluations;
	
	public ArticleCompareVO(Article article, List<User> users,
			HashMap<User, Evaluation> evaluations) {
		super();
		this.article = article;
		this.users = users;
		this.evaluations = evaluations;
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
}
