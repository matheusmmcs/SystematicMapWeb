package br.com.ufpi.systematicmap.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.interceptor.Public;
import br.com.ufpi.systematicmap.interceptor.UserInfo;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.utils.GenerateHashPasswordUtil;

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
		GenerateHashPasswordUtil generateHashPasswordUtil = new GenerateHashPasswordUtil();
		final User currentUser = dao.find(login, generateHashPasswordUtil.generateHash(password));
		validator.check(currentUser != null, new SimpleMessage("login", "invalid_login_or_password"));
		validator.onErrorUsePageOf(this).login();

		userInfo.login(currentUser);
		
		result.include("notice", new SimpleMessage("user.login", "mapstudy.login.success"));
		
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
