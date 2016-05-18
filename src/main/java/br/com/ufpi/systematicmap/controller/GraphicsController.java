/**
 * 
 */
package br.com.ufpi.systematicmap.controller;

import static br.com.caelum.vraptor.view.Results.json;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.ufpi.systematicmap.dao.EvaluationDao;
import br.com.ufpi.systematicmap.dao.MapStudyDao;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.interceptor.Public;
import br.com.ufpi.systematicmap.interceptor.UserInfo;
import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.Data;
import br.com.ufpi.systematicmap.model.Drilldown;
import br.com.ufpi.systematicmap.model.Evaluation;
import br.com.ufpi.systematicmap.model.ExclusionCriteria;
import br.com.ufpi.systematicmap.model.InclusionCriteria;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.Pie;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.enums.ArticleSourceEnum;
import br.com.ufpi.systematicmap.model.enums.ClassificationEnum;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;
import br.com.ufpi.systematicmap.model.vo.Bubble;
import br.com.ufpi.systematicmap.model.vo.SubQuestion;

/**
 * @author Gleison
 *
 */
@Controller
public class GraphicsController {
	private final UserInfo userInfo;
	private MapStudyDao mapStudyDao;
	private final UserDao userDao;
	private final EvaluationDao evaluationDao;
	private final Result result;
	
	protected GraphicsController(){
		this(null, null, null, null, null);
	}
	
	/**
	 * @param userInfo
	 * @param mapStudyDao
	 * @param userDao
	 * @param result
	 */
	@Inject
	public GraphicsController(UserInfo userInfo, MapStudyDao mapStudyDao,
			UserDao userDao, Result result, EvaluationDao evaluationDao) {
		super();
		this.userInfo = userInfo;
		this.mapStudyDao = mapStudyDao;
		this.userDao = userDao;
		this.result = result;
		this.evaluationDao = evaluationDao;
	}


	@Get("/test")
	@Public
	public void test(){
		
	}

	@Get("/graphics/{mapid}/sources")
	public void articlesSources(Long mapid){
		System.out.println(mapid);
		MapStudy mapStudy = mapStudyDao.find(mapid);
		mapStudyDao.refresh(mapStudy);
		
		Set<Article> articles = mapStudy.getArticles();
		
		HashMap<String, Double> sources = new HashMap<>();
		List<ArticleSourceEnum> listSources = asList(ArticleSourceEnum.values());
		
		for (ArticleSourceEnum articleSourceEnum : listSources) {
			sources.put(articleSourceEnum.toString(), 0d);
		}
		
		for (Article article : articles) {
			Double value = sources.get(article.getSource().toString());
			++value;
			sources.put(article.getSource().toString(), value);
		}	
		int total = articles.size();
		
		Pie pie = new Pie();
		List<Data> data = new ArrayList<>();
		
		for (ArticleSourceEnum articleSourceEnum : listSources) {
			Data d = new Data();
			d.setName(articleSourceEnum.getDescription());
			d.setY(sources.get(articleSourceEnum.toString()));
			Double percent = (d.getY() / total) * 100;
			d.setPercent(percent);
			data.add(d);
		}		
		
		pie.setTitle("Artigos por base de busca");
		pie.setColorByPoint(true);
		pie.setName("Artigos");
		pie.setData(data);		
		
		System.out.println("total: " + total + " pie: " + pie);
		
		result.use(json()).indented().withoutRoot().from(pie).recursive().serialize();
	}

	@Get("/graphics/{mapid}/evaluates")
	public void articlesEvaluates(Long mapid){
		MapStudy mapStudy = mapStudyDao.find(mapid);
		mapStudyDao.refresh(mapStudy);
		User user = userInfo.getUser();
		
		Set<Article> articles = mapStudy.getArticles();
		List<Evaluation> evaluations = evaluationDao.getEvaluations(user, mapStudy);
		
		HashMap<String, Double> sources = new HashMap<>();
		List<EvaluationStatusEnum> listEvaluate = asList(EvaluationStatusEnum.values());
		
		for (EvaluationStatusEnum evaluationStatusEnum : listEvaluate) {
			sources.put(evaluationStatusEnum.toString(), 0d);
		}
		
		HashMap<String, Double> acceptedMap = new HashMap<String, Double>();
		HashMap<String, Double> rejectedMap = new HashMap<String, Double>();
		HashMap<String, Double> noEvaluate = new HashMap<String, Double>();
		
		for(Evaluation e : evaluations){
			String criteriaType = e.getEvaluationStatus().toString();
			Double value = sources.get(criteriaType);
			++value;
			sources.put(criteriaType, value);
			
			if ("ACCEPTED".equals(criteriaType)){
				for(InclusionCriteria i : e.getInclusionCriterias()){
					if(acceptedMap.containsKey(i.getId().toString())){
						Double cont = acceptedMap.get(i.getId().toString());
						++cont;
						acceptedMap.put(i.getId().toString(), cont);
					}else{
						acceptedMap.put(i.getId().toString(), (double) 1);
					}					
				}
				
			}else if ("REJECTED".equals(criteriaType)){
				for(ExclusionCriteria i : e.getExclusionCriterias()){
					if(rejectedMap.containsKey(i.getId().toString())){
						Double cont = rejectedMap.get(i.getId().toString());
						++cont;
						rejectedMap.put(i.getId().toString(), cont);
					}else{
						rejectedMap.put(i.getId().toString(), (double) 1);
					}					
				}
			}			
		}
		
		Double count = sources.get("ACCEPTED") + sources.get("REJECTED");
		sources.put("NOT_EVALUATED", count);
		
		int total = articles.size();
		
		System.out.println("total: " + total);
		
		Pie pie = new Pie();
		List<Data> data = new ArrayList<>();
		
		for (EvaluationStatusEnum evaluationStatusEnum : listEvaluate) {
			Data d = new Data();
			d.setName(evaluationStatusEnum.toString());
			
			if (d.getName().equals("ACCEPTED")){
				Drilldown drilldown = new Drilldown();
				drilldown.setData(acceptedMap);
				drilldown.setName(d.getName());
				drilldown.setId(d.getName());				
				d.setDrilldown(drilldown);
			}else if(d.getName().equals("REJECTED")){
				Drilldown drilldown = new Drilldown();
				drilldown.setData(rejectedMap);
				drilldown.setName(d.getName());
				drilldown.setId(d.getName());				
				d.setDrilldown(drilldown);
			}else{
				Drilldown drilldown = new Drilldown();
				drilldown.setData(noEvaluate);
				drilldown.setName(d.getName());
				drilldown.setId(d.getName());				
				d.setDrilldown(drilldown);
			}
			
			d.setY(sources.get(evaluationStatusEnum.toString()));
			Double percent = (d.getY() / total) * 100;
			d.setPercent(percent);
			data.add(d);
			System.out.println();
			System.out.println(d);
		}	
		
		pie.setTitle("Avaliação dos artigos");
		pie.setColorByPoint(true);
		pie.setName("Artigos");
		pie.setData(data);		
		
		System.out.println("total: " + total + " pie: " + pie);
		
		result.use(json()).indented().withoutRoot().from(pie).recursive().serialize();
	}
	
	@Get("/graphics/{mapid}/refine")
	public void articlesRefine(Long mapid){
		System.out.println(mapid);
		MapStudy mapStudy = mapStudyDao.find(mapid);
		mapStudyDao.refresh(mapStudy);
		
		Set<Article> articles = mapStudy.getArticles();
		
		HashMap<String, Double> sources = new HashMap<>();
		List<ClassificationEnum> listRefines = asList(ClassificationEnum.values());
		
		for (ClassificationEnum classificationEnum : listRefines) {
			sources.put(classificationEnum.toString(), 0d);
		}
		
		sources.put("NO_CLASSIFICATION", (double) 0);
		
		for (Article article : articles) {
			if (article.getClassification() != null){
				Double value = sources.get(article.getClassification().toString());
				++value;
				sources.put(article.getClassification().toString(), value);
			}else{
				Double value = sources.get("NO_CLASSIFICATION");
				++value;
				sources.put("NO_CLASSIFICATION", value);
			}
		}	
		int total = articles.size();
		
		Pie pie = new Pie();
		List<Data> data = new ArrayList<>();
		
		for (ClassificationEnum articleSourceEnum : listRefines) {
			Data d = new Data();
			d.setName(articleSourceEnum.toString());
			d.setY(sources.get(articleSourceEnum.toString()));
			Double percent = (d.getY() / total) * 100;
			d.setPercent(percent);
			data.add(d);
		}		
		
		Data d = new Data();
		d.setName("NO_CLASSIFICATION");
		d.setY(sources.get("NO_CLASSIFICATION"));
		Double percent = (d.getY() / total) * 100;
		d.setPercent(percent);
		data.add(d);
		
		pie.setTitle("Artigos Refinados");
		pie.setColorByPoint(true);
		pie.setName("Artigos");
		pie.setData(data);		
		
		System.out.println("total: " + total + " pie: " + pie);
		
		result.use(json()).indented().withoutRoot().from(pie).recursive().serialize();
	}
	
	private HashMap<String, HashMap<String, Long>> alternativesHash(List<Long> x, List<Long> y){
		HashMap<String, HashMap<String, Long>> map = new HashMap<String, HashMap<String, Long>>();
		
		return map;
	}
	
	@Get("/graphics/bubble")
	public void bubble(){ //Long mapid, List<Long> eixoX, List<Long> eixoY
		List<Bubble> bubble = new ArrayList<>();
		
		SubQuestion subquestion1[] = new SubQuestion[2];
		
		subquestion1[0] = new SubQuestion(); 
		subquestion1[1] = new SubQuestion(); 
		
		subquestion1[0].setQuestion("Tipo");
		subquestion1[0].setSubquestion("Framework");
		subquestion1[1].setQuestion("Tipo");
		subquestion1[1].setSubquestion("Feramenta");
		
		SubQuestion subquestion2[] = new SubQuestion[3];
		subquestion2[0] = new SubQuestion();
		subquestion2[1] = new SubQuestion();
		subquestion2[2] = new SubQuestion();
		
		subquestion2[0].setQuestion("Plataforma");
		subquestion2[0].setSubquestion("Mobile");
		subquestion2[1].setQuestion("Plataforma");
		subquestion2[1].setSubquestion("Web");
		subquestion2[1].setQuestion("Plataforma");
		subquestion2[1].setSubquestion("Desktop");
		
		// Question
		Bubble b = new Bubble();
		
		b.setQ1(subquestion1[0].getQuestion());
		b.setQ2(subquestion2[0].getQuestion());
		
		b.setSub_q1(subquestion1[0].getSubquestion());
		b.setSub_q2(subquestion2[0].getSubquestion());
		
		b.setQnt(0);
		
		// Question
		Bubble b2 = new Bubble();
		
		b2.setQ1(subquestion1[0].getQuestion());
		b2.setQ2(subquestion2[1].getQuestion());
		
		b2.setSub_q1(subquestion1[0].getSubquestion());
		b2.setSub_q2(subquestion2[1].getSubquestion());
		
		b2.setQnt(0);
		
		// Question
		Bubble b3 = new Bubble();
		
		b3.setQ1(subquestion1[0].getQuestion());
		b3.setQ2(subquestion2[2].getQuestion());
		
		b3.setSub_q1(subquestion1[0].getSubquestion());
		b3.setSub_q2(subquestion2[2].getSubquestion());
		
		b3.setQnt(16);
		
		
		
		// Question
		Bubble b4 = new Bubble();
		
		b4.setQ1(subquestion1[1].getQuestion());
		b4.setQ2(subquestion2[0].getQuestion());
		
		b4.setSub_q1(subquestion1[1].getSubquestion());
		b4.setSub_q2(subquestion2[0].getSubquestion());
		
		b4.setQnt(0);
		
		// Question
		Bubble b5 = new Bubble();
		
		b5.setQ1(subquestion1[1].getQuestion());
		b5.setQ2(subquestion2[1].getQuestion());
		
		b5.setSub_q1(subquestion1[1].getSubquestion());
		b5.setSub_q2(subquestion2[1].getSubquestion());
		
		b5.setQnt(2);
		
		// Question
		Bubble b6 = new Bubble();
		
		b6.setQ1(subquestion1[1].getQuestion());
		b6.setQ2(subquestion2[2].getQuestion());
		
		b6.setSub_q1(subquestion1[1].getSubquestion());
		b6.setSub_q2(subquestion2[2].getSubquestion());
		
		b6.setQnt(4);
		
		bubble.add(b);
		bubble.add(b2);
		bubble.add(b3);
		bubble.add(b4);
		bubble.add(b5);
		bubble.add(b6);
				
		result.use(json()).indented().withoutRoot().from(bubble).recursive().serialize();		
				
	}
	

}
