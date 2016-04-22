/**
 * 
 */
package br.com.ufpi.systematicmap.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.ufpi.systematicmap.dao.AlternativeDao;
import br.com.ufpi.systematicmap.dao.ArticleDao;
import br.com.ufpi.systematicmap.dao.EvaluationExtrationDao;
import br.com.ufpi.systematicmap.dao.FormDao;
import br.com.ufpi.systematicmap.dao.MapStudyDao;
import br.com.ufpi.systematicmap.dao.QuestionDao;
import br.com.ufpi.systematicmap.interceptor.UserInfo;
import br.com.ufpi.systematicmap.model.Alternative;
import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.EvaluationExtraction;
import br.com.ufpi.systematicmap.model.Form;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.Question;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.vo.QuestionVO;

/**
 * @author Gleison Andrade
 *
 */
@Controller
public class ExtractionController {
	private UserInfo userInfo;
	private Result result;
	private Validator validator;
	private MapStudyDao mapStudyDao;
	private FormDao formDao;
	private ArticleDao articleDao;
	private AlternativeDao alternativeDao;
	private QuestionDao questionDao;
	private EvaluationExtrationDao evaluationExtrationDao;
	
	protected ExtractionController(){
		this(null, null, null, null,null, null, null, null, null);
	}

	@Inject
	public ExtractionController(UserInfo userInfo, Result result, MapStudyDao mapStudyDao, FormDao formDao, ArticleDao articleDao, AlternativeDao alternativeDao, QuestionDao questionDao, Validator validator, EvaluationExtrationDao evaluationExtrationDao) {
		this.userInfo = userInfo;
		this.result = result;
		this.mapStudyDao = mapStudyDao;
		this.formDao = formDao;
		this.articleDao = articleDao;
		this.alternativeDao = alternativeDao;
		this.questionDao = questionDao; 
		this.validator = validator;
		this.evaluationExtrationDao = evaluationExtrationDao;
	}
	
	@Post
	@Path("/extraction/form")
	@Consumes("application/json")
	public void formAjax(QuestionVO questionVO){
		Form form = new Form();		
		MapStudy mapStudy = mapStudyDao.find(questionVO.getMapid());
		Set<Question> backQuestions = new HashSet<>();
		Set<Question> removeQuestions = new HashSet<>();
		
		if (mapStudy.getForm() != null) {
			backQuestions = mapStudy.getForm().getQuestions();
			removeQuestions = new HashSet<>();

			for (Question q : backQuestions) {
				if (!questionVO.getQuestions().contains(q)) {
					removeQuestions.add(q);
				}
			}
			
//			mapStudy.getForm().getQuestions().removeAll(removeQuestions);
		}
		
		form.addQuestionVO(questionVO);		
		mapStudy.addForm(form);		
		
		
		for (Question q : removeQuestions) {
			for (Alternative a : q.getAlternatives()){
				alternativeDao.delete(a);
//				evaluationExtrationDao.removeAlternative(a);
			}
			q.getAlternatives().clear();
			questionDao.delete(q);	
			evaluationExtrationDao.removeQuestion(q);
			mapStudy.getForm().getQuestions().remove(q);
		}
		
		mapStudyDao.update(mapStudy);

		result.use(json()).indented().withoutRoot().from(mapStudy).recursive().serialize();
	}
	
	@Get("/maps/extraction/{mapid}")
	public void extraction(Long mapid){		
		MapStudy mapStudy = mapStudyDao.find(mapid);
		User user = userInfo.getUser();
		
		validator.check(mapStudy != null, new SimpleMessage("mapstudy", "mapstudy.is.not.exist"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		validator.check(mapStudy.members().contains(user), new SimpleMessage("user", "user.is.not.mapstudy"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		validator.check(mapStudy.getForm() != null, new SimpleMessage("mapstudy", "mapstudy.is.not.form"));
		validator.onErrorRedirectTo(MapStudyController.class).show(mapid);
		
		validator.check(mapStudy.getForm().getQuestions() != null && mapStudy.getForm().getQuestions().size() > 0, new SimpleMessage("mapstudy", "mapstudy.is.not.form"));
		validator.onErrorRedirectTo(MapStudyController.class).show(mapid);
		
		Long countArticlesToFinal = articleDao.countArticlesFinalEvaluation(mapStudy);
		
		validator.check(countArticlesToFinal == 0l, new SimpleMessage("mapstudy", "mapstudy.is.not.final.evaluation"));
		validator.onErrorRedirectTo(MapStudyController.class).show(mapid);
		
		result.redirectTo(this).evaluateExtraction(mapid, 0l);
	}
	
	@Get("/maps/extraction/{mapid}/article/{articleid}")
	public void evaluateExtraction(Long mapid, Long articleid){
		MapStudy mapStudy = mapStudyDao.find(mapid);
		
		validator.check(mapStudy != null, new SimpleMessage("mapstudy", "mapstudy.is.not.exist"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		
		List<Article> articlesToExtraction = articleDao.getArticlesToExtraction(userInfo.getUser(), mapStudy);
		//FIXME
		List<Article> extractions = articleDao.getExtractions(userInfo.getUser(), mapStudy);
		
		Article article = null, nextArticle = null;
		Long nextArticleId = null;
		if(articleid != 0){
			article = articleDao.find(articleid);
		}else{
			article = getNextToEvaluate(articlesToExtraction, null);
			if(article != null){
				articleid = article.getId();
			}
		}
		
		if(article != null){
			nextArticle = getNextToEvaluate(articlesToExtraction, articleid);
			if(nextArticle != null){
				nextArticleId = nextArticle.getId();
			}
		}
		
		validator.check((article != null), new SimpleMessage("mapstudy", "mapstudy.extraction.articles.none"));
		validator.onErrorRedirectTo(MapStudyController.class).show(mapid);
		
//		EvaluationExtraction extractionDone = article.getEvaluationExtractions(userInfo.getUser());
		
		Double percentExtractedDouble = mapStudy.percentExtractedDouble(articleDao, userInfo.getUser());
		
		result.include("map", mapStudy);		
		result.include("article", article);
		result.include("nextArticleId", nextArticleId);
		result.include("articlesToExtraction", articlesToExtraction); // artigos a serem avaliados
		result.include("percentExtracted", mapStudy.percentEvaluated(percentExtractedDouble));
		result.include("extractions", extractions); // artigos já avaliados
		result.include("form", mapStudy.getForm());
	}
	
	private static Article getNextToEvaluate(List<Article> articlesToExtraction, Long actual){
		if(actual == null){
			return articlesToExtraction.size() > 0 ? articlesToExtraction.get(0) : null;
		}else{
			Article next = null;
			for(Article a : articlesToExtraction){
				if(!a.getId().equals(actual)){
					next = a;
					break;
				}
			}
			return next;
		}
	}
	
	@Post("/maps/extraction")
	@Consumes("application/json")
	public void evaluateAjax(QuestionVO questionVO){
		User user = userInfo.getUser();
		Article article = articleDao.find(questionVO.getArticleid());
		MapStudy mapStudy = mapStudyDao.find(questionVO.getMapid());
		
		validator.check(article != null, new SimpleMessage("mapstudy", "mapstudy.extraction.articles.none"));
		validator.onErrorRedirectTo(MapStudyController.class).show(questionVO.getMapid());

		int numberQuestions = questionVO.getQuestions().size();
		for (int i = 0; i < numberQuestions; i++) {
			Set<Alternative> auxList = questionVO.getQuestions().get(i).getAlternatives();
			if (auxList != null && auxList.size() > 0){
			Alternative alternative = questionVO.getQuestions().get(i).getAlternatives().iterator().next();
			
			EvaluationExtraction evaluationExtraction = new EvaluationExtraction();
			
			if (alternative.getId() == null){
				Alternative aux = alternativeDao.find(questionVO.getQuestions().get(i).getId(), alternative.getValue());
				
				if (aux == null){
					alternativeDao.insert(alternative);
					questionMapStudy(mapStudy, questionVO.getQuestions().get(i).getId()).addAlternative(alternative);
				}else{
					alternative = aux;
				}
			}else{
				if(alternative.getQuestion() == null){
					alternative.setQuestion(questionMapStudy(mapStudy, questionVO.getQuestions().get(i).getId()));
				}
			}
			
			evaluationExtraction.setAlternative(alternative);
			evaluationExtraction.setArticle(article);
			evaluationExtraction.setUser(user);
			evaluationExtraction.setQuestion(alternative.getQuestion());

			//TODO ao adicionar seria melhor verificar aqui ? se a alternativa alternativa então deveria só atualizar
			article.AddEvaluationExtractions(evaluationExtraction);
			}
		}
		
		Double percentExtractedDouble = mapStudy.percentExtractedDouble(articleDao, userInfo.getUser());

		HashMap<String, Object> returns = new HashMap<>();
		Article nextArticle = null;
		List<EvaluationExtraction> extraction = new ArrayList<>();

		if (questionVO.getNextArticle() != null) {
			nextArticle = articleDao.find(questionVO.getNextArticle());
			extraction = nextArticle.getEvaluationExtraction(userInfo.getUser());
		} else {
			nextArticle = new Article();
			nextArticle.setId(-1l);
		}

		returns.put("extraction", extraction);
		returns.put("article", nextArticle);
		returns.put("percent", mapStudy.percentEvaluated(percentExtractedDouble));

		result.use(Results.json()).indented().withoutRoot().from(returns).recursive().serialize();

		articleDao.update(article);
	}
	

	@Get
	@Path("/extraction/article/{articleid}/load")
	public void loadArticleAjax(Long mapid, Long articleid){
		Article article = articleDao.find(articleid);
		
		// se o artigo não existir
		validator.check(article != null, new SimpleMessage("mapstudy", "mapstudy.evaluate.articles.none"));
		validator.onErrorRedirectTo(MapStudyController.class).show(mapid);	
		
		HashMap<String, Object> returns = new HashMap<>();
		List<EvaluationExtraction> extraction = null;
		
		if (article != null){
			extraction = article.getEvaluationExtraction(userInfo.getUser());
		}		
		
		TreeSet<EvaluationExtraction> extractionOrdered = new TreeSet<EvaluationExtraction>(new Comparator<EvaluationExtraction>(){
		    public int compare(EvaluationExtraction a, EvaluationExtraction b){
		        return a.getQuestion().getName().compareTo(b.getQuestion().getName());
		    }
		});
		extractionOrdered.addAll(extraction);
		
		returns.put("extraction", extractionOrdered);
		returns.put("article", article);
		
		result.use(Results.json()).indented().withoutRoot().from(returns).recursive().serialize();		
	}
	
	@Get("/extraction/show/{mapid}")
	public void showExtractionEvaluates(Long mapid){
		
	}
	
	
	private Question questionMapStudy(MapStudy mapStudy, Long questid){
		for (Question q : mapStudy.getForm().getQuestions()) {
			if (q.getId() == questid){
				return q;
			}
		}
		
		return null;
	}

}
