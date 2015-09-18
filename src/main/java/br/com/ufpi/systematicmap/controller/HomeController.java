package br.com.ufpi.systematicmap.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Messages;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.interceptor.Public;
import br.com.ufpi.systematicmap.interceptor.UserInfo;
import br.com.ufpi.systematicmap.model.User;

@Controller
public class HomeController {

	private final Result result;
	private final Validator validator;
	private final UserInfo userInfo;
	private final UserDao dao;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected HomeController() {
		this(null, null, null, null);
	}

	@Inject
	public HomeController(UserDao dao, UserInfo userInfo, Result result, Validator validator) {
	    this.dao = dao;
		this.result = result;
	    this.validator = validator;
        this.userInfo = userInfo;
	}
	
	@Public
	@Get("/")
	public void home() {
		if(userInfo == null){
			result.redirectTo(this).login();
		}else{
			result.redirectTo(MapStudyController.class).list();
		}
	}

	@Post
	@Public
	public void login(String login, String password) {
		final User currentUser = dao.find(login, password);
		validator.check(currentUser != null, new SimpleMessage("login", "invalid_login_or_password"));
		validator.onErrorUsePageOf(this).login();

		userInfo.login(currentUser);
		
//		result.include("notice", currentUser.getName() + " login feito com sucesso !");
		validator.add(new SimpleMessage("login",currentUser.getName() + " login feito com sucesso !", Severity.INFO));
		validator.add(new SimpleMessage("login", "nome do cliente possui acentuação", Severity.WARN));
//		validator.add(new SimpleMessage("login", "nome do cliente possui acentuação", Severity.ERROR));
		Messages m = new Messages();
		for (Message men : m.getAll()) {
			System.out.println(men.getMessage());
		}
		
		result.redirectTo(MapStudyController.class).list();
	}

	public void logout() {
	    userInfo.logout();
	    result.redirectTo(this).login();
	}

	@Public
	@Get
	public void login() {
	}
	
	@Public
	@Get
	public void create() {
	}
	
	@Public
	@Get
	public void recovery() {
	}

}
