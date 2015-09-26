package br.com.ufpi.systematicmap.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.ParseException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.ufpi.systematicmap.components.FilterArticles;
import br.com.ufpi.systematicmap.dao.ArticleDao;
import br.com.ufpi.systematicmap.dao.EvaluationDao;
import br.com.ufpi.systematicmap.dao.ExclusionCriteriaDao;
import br.com.ufpi.systematicmap.dao.InclusionCriteriaDao;
import br.com.ufpi.systematicmap.dao.MapStudyDao;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.files.FilesUtils;
import br.com.ufpi.systematicmap.interceptor.UserInfo;
import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.Evaluation;
import br.com.ufpi.systematicmap.model.ExclusionCriteria;
import br.com.ufpi.systematicmap.model.InclusionCriteria;
import br.com.ufpi.systematicmap.model.MapStudy;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.UsersMapStudys;
import br.com.ufpi.systematicmap.model.enums.ArticleSourceEnum;
import br.com.ufpi.systematicmap.model.enums.ClassificationEnum;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;
import br.com.ufpi.systematicmap.model.enums.Roles;
import br.com.ufpi.systematicmap.model.vo.ArticleCompareVO;
import br.com.ufpi.systematicmap.utils.BibtexToArticleUtils;
import br.com.ufpi.systematicmap.utils.BibtexUtils;
import br.com.ufpi.systematicmap.utils.FleissKappa;

@Controller
public class MapStudyController {

	private final Result result;
	private final Validator validator;
	private final UserInfo userInfo;
	private final FilesUtils files;
	
	private final MapStudyDao mapStudyDao;
	private final UserDao userDao;
	private final ArticleDao articleDao;
	private final InclusionCriteriaDao inclusionDao;
	private final ExclusionCriteriaDao exclusionDao;
	private final EvaluationDao evaluationDao;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected MapStudyController() {
		this(null, null, null, null, null, null, null, null, null, null);
	}


	@Inject
	public MapStudyController(MapStudyDao musicDao, UserInfo userInfo, 
				Result result, Validator validator, FilesUtils files, 
				UserDao userDao, ArticleDao articleDao, 
				InclusionCriteriaDao inclusionDao,
				ExclusionCriteriaDao exclusionDao,
				EvaluationDao evaluationDao) {
		this.mapStudyDao = musicDao;
		this.result = result;
        this.validator = validator;
        this.userInfo = userInfo;
		this.files = files;
		this.userDao = userDao;
		this.articleDao = articleDao;
		this.inclusionDao = inclusionDao;
		this.exclusionDao = exclusionDao;
		this.evaluationDao = evaluationDao;
	}
	
	
	@Get("/maps")
	public void list() {
		result.include("mapStudys", mapStudyDao.mapStudys(userInfo.getUser()));
	}

	@Post("/maps")
	public void add(final @NotNull @Valid MapStudy mapstudy) {
		validator.onErrorForwardTo(this).list();
		
		User user = userInfo.getUser();
		userDao.refresh(user);
		
		//TODO Em fase de teste precisa ser testando ainda.
		UsersMapStudys usersMapStudys = new UsersMapStudys();
		usersMapStudys.setUser(user);
		usersMapStudys.setMapStudy(mapstudy);
		usersMapStudys.setRole(Roles.CREATOR);
		mapstudy.getUsersMapStudys().add(usersMapStudys);
		//end test
		
		mapStudyDao.add(mapstudy);
		userDao.update(user);
		
		result.include("notice", "mapstudy.add.sucess");
		result.redirectTo(this).list();
	}

	
	@Get("/maps/{id}")
	public void show(Long id) {
		validator.onErrorForwardTo(this).list();
		
		MapStudy mapStudy = mapStudyDao.find(id);
		List<User> mapStudyUsersList = userDao.mapStudyUsers(mapStudy);
		List<User> mapStudyArentUsers = userDao.mapStudyArentUsers(mapStudy);
		
		HashMap<User, String> mapStudyUsers = new HashMap<User, String>();
		for(User u : mapStudyUsersList){
			Double percentEvaluatedDouble = mapStudy.percentEvaluatedDouble(articleDao, u);
			mapStudyUsers.put(u, mapStudy.percentEvaluated(percentEvaluatedDouble));
		}
		
		Double percentEvaluatedDouble = mapStudy.percentEvaluatedDouble(articleDao, userInfo.getUser());
		
	    result.include("map", mapStudy);
	    result.include("sources", ArticleSourceEnum.values());
	    result.include("percentEvaluated", mapStudy.percentEvaluated(percentEvaluatedDouble));
	    result.include("mapStudyUsers", mapStudyUsers);
	    result.include("mapStudyArentUsers", mapStudyArentUsers);
	}

	//TODO Não está funcionando
	@Post("/maps/remove")
	public void remove(Long id) {
		validator.onErrorForwardTo(this).list();
		System.out.println("Select "+id);
		MapStudy mapStudy = mapStudyDao.find(id);
		
		mapStudyDao.delete(mapStudyDao.update(mapStudy));		

		result.redirectTo(this).list();
	}
	
	@Post("/maps/addmember")
	public void addmember(Long id, Long userId){
		validator.onErrorForwardTo(this).show(id);
		
		MapStudy mapStudy = mapStudyDao.find(id);
		User user = userDao.find(userId);
		mapStudy.addUser(user);
		
		mapStudyDao.update(mapStudy);
		userDao.update(user);
		
		result.include("notice", "member.add.sucess");
		result.redirectTo(this).show(id);
	}
	//TODO Não está funcionando
	@Post("/maps/removemember")
	public void removemember(Long id, Long userId){
		validator.onErrorForwardTo(this).show(id);
		
		MapStudy mapStudy = mapStudyDao.find(id);
		User user = userDao.find(userId);
		
//		mapStudy.removeUser(user);
		
		mapStudyDao.update(mapStudy);
		userDao.update(user);
		
		result.redirectTo(this).show(id);
	}
	
	@Post("/maps/addarticles")
	public void addarticles(Long id, UploadedFile upFile, ArticleSourceEnum source) throws IOException, ParseException {
		validator.onErrorForwardTo(this).show(id);
		
		MapStudy mapStudy = mapStudyDao.find(id);
		
		files.save(upFile, mapStudy);
		
		BibTeXDatabase database = BibtexUtils.parseBibTeX(files.getFile(mapStudy));
	    Map<Key, BibTeXEntry> entryMap = database.getEntries();
	    Collection<BibTeXEntry> entries = entryMap.values();
	    
		for(BibTeXEntry entry : entries){
			Article a = BibtexToArticleUtils.bibtexToArticle(entry, source);
			mapStudy.addArticle(a);
			articleDao.insert(a);
		}
		
		mapStudyDao.update(mapStudy);
		
		result.include("notice", "articules.add.sucess");
	    result.redirectTo(this).show(id);
	}
	
	@Post("/maps/addinclusion")
	public void addinclusion(Long id, InclusionCriteria criteria) {
		validator.onErrorForwardTo(this).show(id);
		
		MapStudy mapStudy = mapStudyDao.find(id);
		mapStudy.addInclusionCriteria(criteria);
		inclusionDao.insert(criteria);
		mapStudyDao.update(mapStudy);
		
		result.include("notice", "inclusion.criteria.add.sucess");
	    result.redirectTo(this).show(id);
	}
	
	@Post("/maps/addexclusion")
	public void addexclusion(Long id, ExclusionCriteria criteria) {
		validator.onErrorForwardTo(this).show(id);
		
		MapStudy mapStudy = mapStudyDao.find(id);
		mapStudy.addExclusionCriteria(criteria);
		exclusionDao.insert(criteria);
		mapStudyDao.update(mapStudy);
		
		result.include("notice", "exclusion.criteria.add.sucess");
	    result.redirectTo(this).show(id);
	}
	
	@Post("/maps/refinearticles")
	public void refinearticles(Long id, Integer levenshtein, String regex, 
			Integer limiartitulo, Integer limiarabstract, Integer limiarkeywords, Integer limiartotal){
		
		MapStudy mapStudy = mapStudyDao.find(id);
		FilterArticles filter = new FilterArticles(mapStudy.getArticles(), levenshtein, regex.trim(), limiartitulo, limiarabstract, limiarkeywords, limiartotal);
		filter.filter();
		
		validator.onErrorForwardTo(this).show(id);
		result.include("notice", "refine.articles.sucess");
		result.redirectTo(this).show(id);
	}
	
	@Get("/maps/evaluate/{mapid}")
	public void evaluate(Long mapid) {
		result.redirectTo(this).evaluateArticle(mapid, 0l);
	}
	
	@Get("/maps/evaluate/{mapid}/article/{articleid}")
	public void evaluateArticle(Long mapid, Long articleid) {
		MapStudy mapStudy = mapStudyDao.find(mapid);
		
		validator.check((mapStudy.getArticles().size() > 0), 
				new SimpleMessage("mapstudy", "mapstudy.evaluate.articles.none"));
		
		List<Article> articlesToEvaluate = articleDao.getArticlesToEvaluate(userInfo.getUser(), mapStudy);
		List<Evaluation> evaluations = evaluationDao.getEvaluations(userInfo.getUser(), mapStudy);
		
		Article article = null, nextArticle = null;
		Long nextArticleId = null;
		if(articleid != 0){
			article = articleDao.find(articleid);
		}else{
			article = getNextToEvaluate(articlesToEvaluate, null);
			if(article != null){
				articleid = article.getId();
			}
		}
		
		if(article != null){
			nextArticle = getNextToEvaluate(articlesToEvaluate, articleid);
			if(nextArticle != null){
				nextArticleId = nextArticle.getId();
			}
		}
		
		validator.check((article != null), new SimpleMessage("mapstudy", "mapstudy.evaluate.articles.none"));
		validator.onErrorRedirectTo(this).show(mapid);
		
		Evaluation evaluationDone = evaluationDao.getEvaluation(userInfo.getUser(), mapStudy, article);
		
		//sort criterias
		TreeSet<InclusionCriteria> inclusionOrdered = new TreeSet<InclusionCriteria>(new Comparator<InclusionCriteria>(){
		    public int compare(InclusionCriteria a, InclusionCriteria b){
		        return a.getDescription().compareTo(b.getDescription());
		    }
		});
		inclusionOrdered.addAll(mapStudy.getInclusionCriterias());
		//
		TreeSet<ExclusionCriteria> exclusionOrdered = new TreeSet<ExclusionCriteria>(new Comparator<ExclusionCriteria>(){
		    public int compare(ExclusionCriteria a, ExclusionCriteria b){
		        return a.getDescription().compareTo(b.getDescription());
		    }
		});
		exclusionOrdered.addAll(mapStudy.getExclusionCriterias());
		
		Double percentEvaluatedDouble = mapStudy.percentEvaluatedDouble(articleDao, userInfo.getUser());
		
		result.include("map", mapStudy);
		result.include("exclusionOrdered", exclusionOrdered);
		result.include("inclusionOrdered", inclusionOrdered);
		
		result.include("article", article);
		result.include("nextArticleId", nextArticleId);
		result.include("articlesToEvaluate", articlesToEvaluate);
		result.include("percentEvaluated", mapStudy.percentEvaluated(percentEvaluatedDouble));
		result.include("evaluations", evaluations);
		result.include("evaluationDone", evaluationDone);
	}
	
	private static Article getNextToEvaluate(List<Article> articlesToEvaluate, Long actual){
		if(actual == null){
			return articlesToEvaluate.size() > 0 ? articlesToEvaluate.get(0) : null;
		}else{
			Article next = null;
			for(Article a : articlesToEvaluate){
				if(!a.getId().equals(actual)){
					next = a;
					break;
				}
			}
			return next;
		}
	}
	
	@Post("/maps/includearticle")
	public void includearticle(Long mapid, Long articleid, Long nextArticleId, List<Long> inclusions, String comment){
		doEvaluate(mapid, articleid, inclusions, comment, true);
		nextArticleId = nextArticleId != null ? nextArticleId : 0l;
		result.redirectTo(this).evaluateArticle(mapid, nextArticleId);
	}
	
	@Post("/maps/excludearticle")
	public void excludearticle(Long mapid, Long articleid, Long nextArticleId, List<Long> exclusions, String comment){
		doEvaluate(mapid, articleid, exclusions, comment, false);
		nextArticleId = nextArticleId != null ? nextArticleId : 0l;
		result.redirectTo(this).evaluateArticle(mapid, nextArticleId);
	}
	
	private void doEvaluate(Long mapid, Long articleid, List<Long> ids, String comment, boolean include){
		validator.check((ids != null), 
				new SimpleMessage("mapstudy", "mapstudy.evaluate.criterias.none"));
		validator.onErrorRedirectTo(this).evaluateArticle(mapid, articleid);
		
		MapStudy mapStudy = mapStudyDao.find(mapid);
		Article article = articleDao.find(articleid);
		
		//remove last evaluation
		Evaluation existingEvaluation = evaluationDao.getEvaluation(userInfo.getUser(), mapStudy, article);
		if(existingEvaluation != null){
			evaluationDao.delete(existingEvaluation.getId());
		}
		
		Evaluation e = new Evaluation();
		e.setArticle(article);
		e.setMapStudy(mapStudy);
		e.setComment(comment);
		e.setUser(userInfo.getUser());
		
		for(Long id : ids){
			if(include){
				InclusionCriteria criteria = inclusionDao.find(id);
				e.addInclusion(criteria);
				inclusionDao.update(criteria);
			}else{
				ExclusionCriteria criteria = exclusionDao.find(id);
				e.addExclusion(criteria);
				exclusionDao.update(criteria);
			}
		}
		
		evaluationDao.insert(e);
	}
	
	/* Remove Criteria */
	
	@Get("/maps/{studyMapId}/removeexclusioncriteria/{criteriaId}")
	public void removeexclusioncriteriapage(Long studyMapId, Long criteriaId) {
		validator.onErrorForwardTo(this).show(studyMapId);
		
		ExclusionCriteria criteria = exclusionDao.find(criteriaId);
		Set<Evaluation> evaluations = criteria.getEvaluations();
		
		List<Evaluation> evaluationsImpacted = new ArrayList<Evaluation>();
		List<Evaluation> evaluationsRemoved = new ArrayList<Evaluation>();
		for(Evaluation e : evaluations){
			if(e.getExclusionCriterias().size() == 1 &&
				e.getExclusionCriterias().contains(criteria)){
				evaluationsRemoved.add(e);
			}
			evaluationsImpacted.add(e);
		}
		
		result.include("criteria", criteria);
		result.include("evaluationsImpacted", evaluationsImpacted);
		result.include("evaluationsRemoved", evaluationsRemoved);
	}
	
	@Post("/maps/removeexclusioncriteria/")
	public void removeexclusioncriteria(Long studyMapId, Long criteriaId) {
		validator.onErrorForwardTo(this).show(studyMapId);
		
		ExclusionCriteria criteria = exclusionDao.find(criteriaId);
		Set<Evaluation> evaluations = criteria.getEvaluations();
		
		for(Evaluation e : evaluations){
			if(e.getExclusionCriterias().size() == 1 &&
				e.getExclusionCriterias().contains(criteria)){
				evaluationDao.delete(e.getId());
			}else{
				e.getExclusionCriterias().remove(criteria);
				evaluationDao.update(e);
			}
		}
		
		exclusionDao.delete(criteriaId);
		
		result.redirectTo(this).show(studyMapId);
	}
	
	/*  */
	@Get("/maps/{studyMapId}/evaluates/")
	public void showEvaluates(Long studyMapId) {
		MapStudy mapStudy = mapStudyDao.find(studyMapId);
		
		validator.check((mapStudy != null), 
				new SimpleMessage("mapstudy", "mapstudy.evaluate.criterias.none"));
		
		validator.onErrorRedirectTo(this).list();
		
		List<Evaluation> evaluations = evaluationDao.getEvaluations(userInfo.getUser(), mapStudy);
		List<Article> articles = articleDao.getArticles(mapStudy);
		
		HashMap<InclusionCriteria, Integer> inclusionCriterias = new HashMap<InclusionCriteria, Integer>();
		HashMap<ExclusionCriteria, Integer> exclusionCriterias = new HashMap<ExclusionCriteria, Integer>();
		
		int countRejected = 0, countAccepted = 0, countToDo = 0;
		for(Evaluation e : evaluations){
			if(e.getEvaluationStatus().equals(EvaluationStatusEnum.ACCEPTED)){
				countAccepted++;
			}else if(e.getEvaluationStatus().equals(EvaluationStatusEnum.REJECTED)){
				countRejected++;
			}
			
			if(e.getEvaluationStatus().equals(EvaluationStatusEnum.ACCEPTED)){
				for(InclusionCriteria i : e.getInclusionCriterias()){
					if(inclusionCriterias.containsKey(i)){
						inclusionCriterias.put(i, inclusionCriterias.get(i)+1);
					}else{
						inclusionCriterias.put(i, 1);
					}
				}
			}else if(e.getEvaluationStatus().equals(EvaluationStatusEnum.REJECTED)){
				for(ExclusionCriteria ec : e.getExclusionCriterias()){
					if(exclusionCriterias.containsKey(ec)){
						exclusionCriterias.put(ec, exclusionCriterias.get(ec)+1);
					}else{
						exclusionCriterias.put(ec, 1);
					}
				}
			}
		}
		
		int countRepeated = 0, countDontMatch = 0, countWithoutAuthors = 0, countWithoutAbstracts = 0, countWithoutClassification = 0, countClassified= 0;
		for(Article a : articles){
			if(a.getClassification() != null){
				if(a.getClassification().equals(ClassificationEnum.REPEAT)){
					countRepeated++;
				}else if(a.getClassification().equals(ClassificationEnum.WORDS_DONT_MATCH)){
					countDontMatch++;
				}else if(a.getClassification().equals(ClassificationEnum.WITHOUT_AUTHORS)){
					countWithoutAuthors++;
				}else if(a.getClassification().equals(ClassificationEnum.WITHOUT_ABSTRACT)){
					countWithoutAbstracts++;
				}
				countClassified++;
			}else{
				countWithoutClassification++;
			}
		}
		
		Double percentEvaluatedDouble = mapStudy.percentEvaluatedDouble(articleDao, userInfo.getUser());
		
		result.include("user", userInfo.getUser());
		result.include("mapStudy", mapStudy);
		result.include("articles", articles);
		result.include("inclusionCriteriasMap", inclusionCriterias);
		result.include("exclusionCriteriasMap", exclusionCriterias);
		
		result.include("percentEvaluated", mapStudy.percentEvaluated(percentEvaluatedDouble));
		result.include("percentEvaluatedDouble", percentEvaluatedDouble);
				
		result.include("countAccepted", countAccepted);
		result.include("countRejected", countRejected);
		result.include("countToDo", countToDo);
		
		result.include("countRepeated", countRepeated);
		result.include("countDontMatch", countDontMatch);
		result.include("countWithoutAuthors", countWithoutAuthors);
		result.include("countWithoutAbstracts", countWithoutAbstracts);
		result.include("countWithoutClassification", countWithoutClassification);
		result.include("countClassified", countClassified);
	}
	
	@Path("/maps/{mapStudyId}/accepted/mine")
	@Get
	public Download downloadMine(Long mapStudyId) throws IOException {
		MapStudy mapStudy = mapStudyDao.find(mapStudyId);
		List<Evaluation> evaluations = evaluationDao.getEvaluations(userInfo.getUser(), mapStudy);
		List<Article> articles = new ArrayList<Article>();
		for(Evaluation e : evaluations){
			if(e.getEvaluationStatus().equals(EvaluationStatusEnum.ACCEPTED)){
				articles.add(e.getArticle());
			}
		}
		return generateFile(mapStudy, articles);
	}
	
	@Path("/maps/{mapStudyId}/accepted/all")
	@Get
	public Download downloadAll(Long mapStudyId) throws IOException {
		MapStudy mapStudy = mapStudyDao.find(mapStudyId);
		List<Article> articles = articleDao.getArticlesFinalAccepted(mapStudy);
		return generateFile(mapStudy, articles);
	}
	
	private Download generateFile(MapStudy mapStudy, List<Article> articles) throws IOException {
		//create file
		String filename = mapStudy.getTitle().replaceAll(" ", "_")+ ".csv";
		File file = new File(filename);
		FileWriter writer = new FileWriter(file, false);
		
		String delimiter = ";";
		
		//create header
		writer.append("Author"+delimiter);
		writer.append("Title"+delimiter);
	    writer.append("Journal"+delimiter);
//	    writer.append("Year"+delimiter);
//	    writer.append("Pages"+delimiter);
	    writer.append("Doi"+delimiter);
//	    writer.append("URL"+delimiter);
	    writer.append("DocType"+delimiter);
	    writer.append("Source"+delimiter);
//	    writer.append("Language"+delimiter);
//	    writer.append("Abstract"+delimiter);
//	    writer.append("Keywords");
	    writer.append('\n');
		
		for(Article a : articles){
			writer.append(a.getAuthor()+delimiter);
			writer.append(a.getTitle()+delimiter);
		    writer.append(a.getJournal()+delimiter);
//			writer.append(a.getYear()+delimiter);
//			writer.append(a.getPages()+delimiter);
		    writer.append(a.getDoi()+delimiter);
//			writer.append(a.getUrl()+delimiter);
		    writer.append(a.getDocType()+delimiter);
		    writer.append(a.getSource()+delimiter);
//			writer.append(a.getLanguage()+delimiter);
//			writer.append(a.getAbstrct()+delimiter);
//			writer.append(a.getKeywords());
		    writer.append('\n');
		}
		writer.flush();
	    writer.close();
		
		String contentType = "text/csv";
		return new FileDownload(file, contentType, filename);
	}
	
	//comparar as avaliações dos usuários
	@Path("/maps/{mapStudyId}/compare")
	@Get
	public void compareEvaluations(Long mapStudyId){
		MapStudy mapStudy = mapStudyDao.find(mapStudyId);
		
		validator.check((mapStudy != null), 
				new SimpleMessage("mapstudy", "mapstudy.evaluate.criterias.none"));
		validator.onErrorRedirectTo(this).show(mapStudyId);
		
		Double percentEvaluatedDouble = mapStudy.percentEvaluatedDouble(articleDao, userInfo.getUser());
		
		validator.check((percentEvaluatedDouble >= 100), 
				new SimpleMessage("mapstudy", "mapstudy.evaluations.compare.undone"));
		validator.onErrorRedirectTo(this).list();
		
		List<Article> articles = articleDao.getArticlesToEvaluate(mapStudy);
		//TODO
//		ArrayList<User> members = new ArrayList<User>(mapStudy.getMembers());

		List<User> members = userDao.mapStudyUsers(mapStudy);
		Collections.sort(members, new Comparator<User>() {
			@Override
			public int compare(User u1, User u2){
				return u1.getLogin().compareTo(u2.getLogin());
			}
		});
		
		List<ArticleCompareVO> articlesCompare = new ArrayList<ArticleCompareVO>();
		List<ArticleCompareVO> articlesAcceptedCompare = new ArrayList<ArticleCompareVO>();
		for(Article a : articles){
			HashMap<User, Evaluation> evaluations = new HashMap<User, Evaluation>();
			boolean hasAccepted = false;
			for(User u : members){
				Evaluation evaluation = a.getEvaluation(u);
				evaluations.put(u, evaluation);
				if(evaluation != null && evaluation.getEvaluationStatus().equals(EvaluationStatusEnum.ACCEPTED)){
					hasAccepted = true;
				}
			}
			ArticleCompareVO acvo = new ArticleCompareVO(a, members, evaluations);
			articlesCompare.add(acvo);
			if(hasAccepted){
				articlesAcceptedCompare.add(acvo);
			}
		}
		
		result.include("mapStudy", mapStudy);
		result.include("members", members);
		result.include("articles", articlesCompare);
		result.include("articlesAccepted", articlesAcceptedCompare);
		result.include("evaluationStatus", EvaluationStatusEnum.values());
		result.include("kappa", FleissKappa.combineKappas(articlesCompare, members));
	}
	
	@Path("/maps/finalEvaluate")
	@Post
	public void finalEvaluate(Long mapStudyId, Long articleId, EvaluationStatusEnum evaluation){
		Article article = articleDao.find(articleId);
		article.setFinalEvaluation(evaluation);
		articleDao.update(article);
		result.redirectTo(this).compareEvaluations(mapStudyId);
	}
	
	@Path("/maps/kappa")
	@Post
	public void calcKappa(Long mapStudyId, String usersIds){
		MapStudy mapStudy = mapStudyDao.find(mapStudyId);
		List<Article> articles = articleDao.getArticlesToEvaluate(mapStudy);
		List<User> users = new ArrayList<User>();
		HashMap<String, Object> retorno = new HashMap<String, Object>();
		
		for(String s : usersIds.split(";")){
			users.add(userDao.find(Long.parseLong(s)));
		}
		
		List<ArticleCompareVO> articlesCompare = new ArrayList<ArticleCompareVO>();
		for(Article a : articles){
			HashMap<User, Evaluation> evaluations = new HashMap<User, Evaluation>();
			for(User u : users){
				Evaluation evaluation = a.getEvaluation(u);
				evaluations.put(u, evaluation);
			}
			ArticleCompareVO acvo = new ArticleCompareVO(a, users, evaluations);
			articlesCompare.add(acvo);
		}
		retorno = FleissKappa.combineKappasMap(articlesCompare, users);
		
		result.use(Results.json()).from(retorno).serialize();
	}
	
	@Path("/maps/article/{articleId}/details")
	@Get
	public void articleDetail(Long articleId){
		Article article = articleDao.find(articleId);
		
		HashMap<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("id", article.getId());
		retorno.put("title", article.getTitle());
		retorno.put("abstract", article.getAbstrct());
		
		HashMap<String, Object> evaluations = new HashMap<String, Object>();
		for(Evaluation e : article.getEvaluations()){
			HashMap<String, Object> evaluation = new HashMap<String, Object>();
			String user = e.getUser().getName();
			String comment = e.getComment();
			
			List<String> criterias = new ArrayList<String>();
			if(e.getEvaluationStatus().equals(EvaluationStatusEnum.ACCEPTED)){
				for(InclusionCriteria c : e.getInclusionCriterias()){
					criterias.add(c.getDescription());
				}
			}else if(e.getEvaluationStatus().equals(EvaluationStatusEnum.REJECTED)){
				for(ExclusionCriteria c : e.getExclusionCriterias()){
					criterias.add(c.getDescription());
				}
			}
			
			evaluation.put("criterias", criterias);
			evaluation.put("comment", comment);
			
			evaluations.put(user, evaluation);
		}
		
		retorno.put("evaluations", evaluations);
		
		result.use(Results.json()).from(retorno).serialize();
	}
	
	
	/*
	 * , UploadedFile file
	validator.onErrorForwardTo(UsersController.class).home();

	mapStudyDao.add(map);
	
	User currentUser = userInfo.getUser();
	userDao.refresh(currentUser);
	
	currentUser.add(map);
	
	// is there a file?
	if (file != null) {
	    // Let's save the file
		musics.save(file, map);
		logger.info("Uploaded file: {}", file.getFileName());
	}

	// you can add objects to result even in redirects. Added objects will
	// survive one more request when redirecting.
	result.include("notice", map.getTitle() + " music added");

	result.redirectTo(UsersController.class).home();
	*/
	
	
	/*
	 
	@Path("/maps/download/{id}")
	@Get
	public Download download(Long id) throws FileNotFoundException {
		MapStudy music = mapStudyDao.find(id);
		File file = musics.getFile(music);
		String contentType = "audio/mpeg";
		String filename = music.getTitle() + ".mp3";

		return new FileDownload(file, contentType, filename);
	}
	
	 * Show all list of registered musics in json format
	@Public @Path("/maps/list/json")
	public void showAllMusicsAsJSON() {
		result.use(json()).from(mapStudyDao.findAll()).serialize();
	}
	@Public @Path("/maps/list/xml")
	public void showAllMusicsAsXML() {
		result.use(xml()).from(mapStudyDao.findAll()).serialize();
	}
	@Public @Path("/maps/list/http")
	public void showAllMusicsAsHTTP() {
		result.use(http()).body("<p class=\"content\">"+
			mapStudyDao.findAll().toString()+"</p>");
	}

	@Public @Path("/maps/list/form")
	public void listForm() {}
	
	@Public @Path("maps/listAs")
	public void listAs() {
		result.use(representation())
			.from(mapStudyDao.findAll()).serialize();
	}
	*/
}
