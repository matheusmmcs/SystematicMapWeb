/**
 * 
 */
package br.com.ufpi.systematicmap.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.commons.io.output.FileWriterWithEncoding;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.ufpi.systematicmap.dao.AlternativeDao;
import br.com.ufpi.systematicmap.dao.ArticleDao;
import br.com.ufpi.systematicmap.dao.EvaluationExtractionFinalDao;
import br.com.ufpi.systematicmap.dao.EvaluationExtrationDao;
import br.com.ufpi.systematicmap.dao.FormDao;
import br.com.ufpi.systematicmap.dao.MapStudyDao;
import br.com.ufpi.systematicmap.dao.QuestionDao;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.interceptor.UserInfo;
import br.com.ufpi.systematicmap.model.Alternative;
import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.EvaluationExtraction;
import br.com.ufpi.systematicmap.model.EvaluationExtractionFinal;
import br.com.ufpi.systematicmap.model.Form;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.Question;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.enums.ReturnStatusEnum;
import br.com.ufpi.systematicmap.model.vo.ExtractionCompareVO;
import br.com.ufpi.systematicmap.model.vo.QuestionVO;
import br.com.ufpi.systematicmap.model.vo.ReturnVO;

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
    private EvaluationExtractionFinalDao evaluationExtractionFinalDao;
    private UserDao userDao;
	
    protected ExtractionController() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    @Inject
    public ExtractionController(UserInfo userInfo, Result result, MapStudyDao mapStudyDao, FormDao formDao, ArticleDao articleDao, AlternativeDao alternativeDao, QuestionDao questionDao, Validator validator, EvaluationExtrationDao evaluationExtrationDao, EvaluationExtractionFinalDao evaluationExtractionFinalDao, UserDao userDao) {
        this.userInfo = userInfo;
        this.result = result;
        this.mapStudyDao = mapStudyDao;
        this.formDao = formDao;
        this.articleDao = articleDao;
        this.alternativeDao = alternativeDao;
        this.questionDao = questionDao;
        this.validator = validator;
        this.evaluationExtrationDao = evaluationExtrationDao;
        this.evaluationExtractionFinalDao = evaluationExtractionFinalDao;
        this.userDao = userDao;
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
	
	@Post
	@Consumes("application/json")
	public void loadQuestions(Long mapid){
		MapStudy mapStudy = mapStudyDao.find(mapid);
		Set<Question> questions = new HashSet<>();
		if (mapStudy != null && mapStudy.getForm() != null) {
			questions = mapStudy.getForm().getQuestions();
		}	
		
		result.use(json()).indented().withoutRoot().from(questions).recursive().serialize();
	}
	
	@Post
	@Consumes("application/json")
	public void removeQuestion(Long mapid, Long questionId){

		MapStudy mapStudy = mapStudyDao.find(mapid);
		Question question = questionDao.find(questionId);
		
		boolean containsQuestion = false;
		for (Question q : mapStudy.getForm().getQuestions()) {
			if (question.getId().equals(q.getId())) {
				containsQuestion = true;
				break;
			}
		}
		
		ReturnVO retorno;
		if (containsQuestion) {
			for (Alternative a : question.getAlternatives()){
				alternativeDao.delete(a);
			}
			question.getAlternatives().clear();
			evaluationExtrationDao.removeQuestion(question);
			mapStudy.getForm().getQuestions().remove(question);
			questionDao.delete(question);
			mapStudyDao.update(mapStudy);
			
			retorno = new ReturnVO(ReturnStatusEnum.SUCESSO, "");
		} else {
			retorno = new ReturnVO(ReturnStatusEnum.ERRO, "Ops, não foi possível concluir. Tente novamente.");
		}
			
		result.use(json()).indented().withoutRoot().from(retorno).recursive().serialize();
	}
	
	@Post
	@Consumes("application/json")
	public void addQuestion(Long mapid, Question question){
		Form form = new Form();		
		MapStudy mapStudy = mapStudyDao.find(mapid);
		
		//se já existir form no mapeamento
		if (mapStudy.getForm() != null && question.getId() != null) {
			Set<Question> questions = new HashSet<Question>(mapStudy.getForm().getQuestions());
			for (Question q : questions) {
				//verifico se já existe algum com o id passado
				if (question.getId().equals(q.getId())) {
					for (Alternative a : q.getAlternatives()){
						alternativeDao.delete(a);
					}
					q.getAlternatives().clear();
					questionDao.delete(q);	
					evaluationExtrationDao.removeQuestion(q);
					mapStudy.getForm().getQuestions().remove(q);
				}
			}
		}
		
		question.setId(null);
		for (Alternative a : question.getAlternatives()) {
			a.setQuestion(question);
		}
		form.addQuestion(question);
		mapStudy.addForm(form);
		mapStudyDao.update(mapStudy);

		result.use(json()).indented().withoutRoot().from(mapStudy).recursive().serialize();
	}
	
	@Post
	@Consumes("application/json")
	public void getQuestion(Long mapid, Long questionId){
		MapStudy mapStudy = mapStudyDao.find(mapid);
		Question question = questionDao.find(questionId);
		
		boolean containsQuestion = false;
		for (Question q : mapStudy.getForm().getQuestions()) {
			if (question.getId().equals(q.getId())) {
				containsQuestion = true;
				break;
			}
		}
		
		ReturnVO retorno;
		if (containsQuestion) {
			retorno = new ReturnVO(ReturnStatusEnum.SUCESSO, "");
			retorno.setData(question);
		} else {
			retorno = new ReturnVO(ReturnStatusEnum.ERRO, "Ops, não foi possível concluir. Tente novamente.");
		}
			
		result.use(json()).indented().withoutRoot().from(retorno).recursive().serialize();
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
		
		Long countArticlesToExtraction = articleDao.countArticleToEvaluateExtraction(user, mapStudy);
		
		//se existe artigos para realizar extração entra
		validator.check(countArticlesToExtraction > 0l, new SimpleMessage("mapstudy", "mapstudy.is.not.article.to.extraction"));
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
		
//		System.out.println("Next article: " + nextArticle);

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
		MapStudy mapStudy = this.mapStudyDao.find(mapid);
        User user = this.userInfo.getUser();
        
        validator.check(mapStudy != null, new SimpleMessage("mapstudy", "mapstudy.is.not.exist"));
        validator.onErrorRedirectTo(MapStudyController.class).list();
        
        validator.check(mapStudy.members().contains(user), new SimpleMessage("user", "user.is.not.mapstudy"));
        validator.onErrorRedirectTo(MapStudyController.class).list();
        
        List<Article> extractions = this.articleDao.getExtractions(this.userInfo.getUser(), mapStudy);
        
        HashMap<String,HashMap<String, Long>> ext = new HashMap<String, HashMap<String,Long>>();
        
        for (Article article : extractions) {
			for (EvaluationExtraction ee : article.getEvaluationExtraction(user)) {
				String questionName = ee.getQuestion().getName();
				HashMap<String, Long> aux = new HashMap<String, Long>();
				
				if (ext.containsKey(questionName)){					
					aux = ext.get(questionName);
				}
				
				String alternativeValue = ee.getAlternative().getValue();
				Long count = 1l;
				
				if (aux.containsKey(alternativeValue)){
					count = aux.get(alternativeValue) + 1;
				}		
				
				aux.put(alternativeValue, count);
				ext.put(questionName, aux);				
			}
		}
        
        
        validator.check(extractions.size() > 0, new SimpleMessage("mapstudy.articles", "mapstudy.extraction.none"));
        validator.onErrorRedirectTo(MapStudyController.class).show(mapid);
        
        Double percentEvaluatedDouble = mapStudy.percentExtractedDouble(this.articleDao, user);
        
        this.result.include("map", mapStudy);
        this.result.include("article", extractions.get(0));
        this.result.include("extractions", ext);
        this.result.include("form", mapStudy.getForm());
        this.result.include("percentEvaluatedDouble", percentEvaluatedDouble);
	}
	
	private Question questionMapStudy(MapStudy mapStudy, Long questid){
		for (Question q : mapStudy.getForm().getQuestions()) {
			if (q.getId() == questid){
				return q;
			}
		}
		
		return null;
	}
	
	@Get("/maps/{mapid}/compareExtractions")
	public void compare(Long mapid){
		MapStudy mapStudy = mapStudyDao.find(mapid);
		User user = userInfo.getUser();
		
		validator.check(mapStudy != null, new SimpleMessage("mapstudy", "mapstudy.is.not.exist"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		validator.check(mapStudy.members().contains(user), new SimpleMessage("user", "user.is.not.mapstudy"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		Double percentEvaluatedDouble = mapStudy.percentExtractedDouble(articleDao, user);
		
		validator.check((percentEvaluatedDouble >= 100.0), new SimpleMessage("mapstudy", "mapstudy.evaluations.compare.undone"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		result.redirectTo(this).finalExtractionLoad(mapid, 0l);		
	}
	
	@Get("/maps/{mapid}/articleCompare/{articleid}")
	public void finalExtractionLoad (Long mapid, Long articleid){
		MapStudy mapStudy = mapStudyDao.find(mapid);
//		User user = userInfo.getUser();
		
		validator.check(mapStudy != null, new SimpleMessage("mapstudy", "mapstudy.is.not.exist"));
		validator.onErrorRedirectTo(MapStudyController.class).list();
		
		List<Article> articlesToCompare = articleDao.getArticlesToFinalExtraction(mapStudy);
		List<Article> articlesFinalExtracted = articleDao.getArticlesFinalExtraction(mapStudy);
		
		Article article = null, nextArticle = null;
		Long nextArticleId = null;
		if(articleid != 0){
			article = articleDao.find(articleid);
		}else{
			article = getNextToEvaluate(articlesToCompare, null);
			if(article != null){
				articleid = article.getId();
			}
		}
		
		if(article != null){
			nextArticle = getNextToEvaluate(articlesToCompare, articleid);
			if(nextArticle != null){
				nextArticleId = nextArticle.getId();
			}
		}
		
		if (article  == null){
			article = articlesFinalExtracted.get(0);
			result.include("warning", new SimpleMessage("mapstudy.article", "mapstudy.extraction.final.articles.none"));
		}
		
//		validator.check((article != null), new SimpleMessage("mapstudy", "mapstudy.extraction.compare.articles.none"));
//		validator.onErrorRedirectTo(MapStudyController.class).show(mapid);

		List<User> members = userDao.mapStudyUsers(mapStudy);
		Collections.sort(members, new Comparator<User>() {
			@Override
			public int compare(User u1, User u2){
				return u1.getLogin().compareTo(u2.getLogin());
			}
		});
		
		ExtractionCompareVO articleLoad = new ExtractionCompareVO(article);
		
		for(User u : members){
			List<EvaluationExtraction> extractionsList = article.getEvaluationExtraction(u);
			
			for (EvaluationExtraction ee : extractionsList) {
				articleLoad.addQueston(ee.getQuestion(), ee.getAlternative(), ee.getUser());
			}	
		}
		
		result.include("mapStudy", mapStudy);
		result.include("members", members);
		result.include("articlesFinalExtracted", articlesFinalExtracted);
		result.include("articlesToCompare", articlesToCompare);
		result.include("article", articleLoad);
		result.include("notice", new SimpleMessage("mapstudy.article", "mapstudy.article.load.success"));
		
//		result.use(Results.json()).indented().withoutRoot().from(articlesCompare).recursive().serialize();
	}
	
	@Post
	public void finalExtraction(Long mapid, Long articleid, List<Long> questions, List<Long> alternatives){
//		System.out.println("mapid: " + mapid + " articleid: " + articleid + " questions: " + questions + " alternatives: " + alternatives);
		EvaluationExtractionFinal eef = null;
		
		MapStudy mapStudy = mapStudyDao.find(mapid);
		Article article = articleDao.find(articleid);
		
		int count = questions.size();
		
		for (int i = 0; i < count; i++) {
			eef = new EvaluationExtractionFinal();
			eef.setMapStudy(mapStudy);
			eef.setArticle(article);		
			
			Question question = questionDao.find(questions.get(i));
			Alternative alternative = alternativeDao.find(alternatives.get(i));
			
			eef.setQuestion(question);
			eef.setAlternative(alternative);
			
			article.getEvaluationExtractionsFinal().add(eef);
		}
		
		articleDao.update(article);
		
		result.redirectTo(this).finalExtractionLoad(mapid, 0l);		
	}
	
	
	@Path("/maps/{mapStudyId}/extractions/mine")
	@Get
	public Download downloadMine(Long mapStudyId) throws IOException {
		MapStudy mapStudy = mapStudyDao.find(mapStudyId);
		List<Article> articles = articleDao.getExtractions(userInfo.getUser(), mapStudy);		
		
		validator.check(articles.size() > 0, new SimpleMessage("mapstudy.articles", "mapstudy.articles.extraction.none"));
		validator.onErrorRedirectTo(this).showExtractionEvaluates(mapStudyId);
		
		return generateFile(mapStudy, articles, false);
	}
	
	@Path("/maps/{mapStudyId}/extractions/all")
	@Get
	public Download downloadAll(Long mapStudyId) throws IOException {
		MapStudy mapStudy = mapStudyDao.find(mapStudyId);
		
		// Todos os artigos com a avaliação final aceita
		List<Article> articles = articleDao.getArticlesFinalExtraction(mapStudy);
		
		validator.check(articles.size() > 0, new SimpleMessage("mapstudy.articles", "mapstudy.articles.extractionfinal.all.none"));
		validator.onErrorRedirectTo(this).showExtractionEvaluates(mapStudyId);
		
		return generateFile(mapStudy, articles, true);
	}
	
	private Download generateFile(MapStudy mapStudy, List<Article> articles, boolean all) throws IOException {
		//create file
		String filename = "Extractions_" + mapStudy.getTitle().replaceAll(" ", "_")+ ".csv";
		File file = new File(filename);
		String encoding = "ISO-8859-1";
		FileWriterWithEncoding writer = new FileWriterWithEncoding(file, encoding, false);
//		FileWriter writer = new FileWriter(file, false);
		
		Collections.sort(articles, new Comparator<Article>() {
			@Override
			public int compare(Article a1, Article a2){
				return a1.getId().compareTo(a2.getId());
			}
		});
		
		String data = "";
		
		String delimiter = ";";
		
		//create header
//		writer.append("ID"+delimiter);
//		writer.append("Author"+delimiter);
//		writer.append("Title"+delimiter);
//	    writer.append("Journal"+delimiter);
//	    writer.append("Year"+delimiter);
//	    writer.append("DocType"+delimiter);
//	    writer.append("Source"+delimiter);
		
		data += "Id"+delimiter;
		data += "Author"+delimiter;
		data += "Title"+delimiter;
		data += "Journal"+delimiter;
		data += "Year"+delimiter;
		data += "DocType"+delimiter;
		data += "Source"+delimiter;
	    
	    String[] head = questionsName(mapStudy);
	    //create head name questions	    
	    for (String s : head) {
//	    	 writer.append(s + delimiter);
	    	data += (s + delimiter);
		}
	    
	    
//	    writer.append('\n');
	    data += "\n";
	    
	    HashMap<String, String> questionsAndAlternative = new HashMap<String, String>();
	
		for(Article a : articles){
//			writer.append(a.getId() + delimiter);
//			writer.append(a.getAuthor() + delimiter);
//			writer.append(a.getTitle() + delimiter);
//		    writer.append((a.getJournal() != null ? a.getJournal() : "" ) + delimiter);
//			writer.append((a.getYear() != null ? a.getYear() : "" ) +delimiter);
//		    writer.append((a.getDocType() != null ? a.getDocType() : "" ) + delimiter);
//		    writer.append(a.getSource() + delimiter);
			
			data += a.getId() + delimiter;
			data += a.getAuthor() + delimiter;
			data += a.getTitle() + delimiter;
			data += (a.getJournal() != null ? a.getJournal() : "Dado não extraído") + delimiter;
			data += (a.getYear() != null ? a.getYear() : "Dado não extraído") +delimiter;
			data += (a.getDocType() != null && !a.getDocType().equals("") ? a.getDocType() : "Dado não extraído") + delimiter;
			data += a.sourceView(a.getSource()) + delimiter;
		    
		    if (all){
		    	questionsAndAlternative = a.getEvaluateFinalExtractionAlternative();
		    }else{
		    	questionsAndAlternative = a.getEvaluateFinalExtractionAlternative(userInfo.getUser());
		    }
		    
		    for (String s : head) {
//		    	 writer.append((questionsAndAlternative.get(s) != null ? questionsAndAlternative.get(s) : "Dado não extraído") + delimiter);
		    	String v = questionsAndAlternative.get(s); 
		    	data = data + ((v != null ? v : "Dado não extraído") + delimiter);
			} 
//		    writer.append('\n');
		    data += "\n";
		}
		
		writer.append(data);
		
		writer.flush();
	    writer.close();
	    
		String contentType = "text/csv";
		return new FileDownload(file, contentType, filename);	
	}
	

	private String[] questionsName(MapStudy mapStudy){//, String defaultExtraction[]) {
		int count = mapStudy.getForm().getQuestions().size();// + defaultExtraction.length;
		String[] head = new String[count];		
		
		int i = 0;
		for (Question q : mapStudy.getForm().getQuestions()) {
			head[i] = q.getName();
			++i;
		}
		
		return head;
	}
	
	public void alternatives(Long questionId){
		Question question = questionDao.find(questionId);
		Set<Alternative> alternatives= question.getAlternatives();
		
		result.use(Results.json()).indented().withoutRoot().from(alternatives).recursive().serialize();	
	}

}
