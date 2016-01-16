/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ufpi.systematicmap.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.interceptor.Public;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.utils.GenerateHashPasswordUtil;
import br.com.ufpi.systematicmap.utils.Linker;
import br.com.ufpi.systematicmap.utils.MailUtils;
import br.com.ufpi.systematicmap.validation.EmailAvailable;
import br.com.ufpi.systematicmap.validation.LoginAvailable;

/**
 * The resource <code>UsersController</code> handles all user 
 * operations, such as adding new users, listing users, and so on.
 */
@Controller
public class UsersController {

	private final Validator validator;
	private final Result result;
	private final UserDao userDao;
	private final MailUtils mailUtils;
	private final Linker linker;

	/**
	 * @deprecated CDI eyes only
	 */
	protected UsersController() {
		this(null, null, null, null, null);
	}

	@Inject
	public UsersController(UserDao dao, Result result, Validator validator, MailUtils mailUtils, Linker linker) {
		this.userDao = dao;
		this.result = result;
		this.validator = validator;
		this.mailUtils = mailUtils;
		this.linker = linker;
	}

	@Get("/users")
	public void list() {
        result.include("users", userDao.findAll());
    }

	@Path("/users")
	@Post
	@Public
	public void add(@Valid @LoginAvailable @EmailAvailable User user, String repassword) {
        validator.onErrorUsePageOf(HomeController.class).create();
        
        validator.check(user.getPassword().equals(repassword), new SimpleMessage("user.password", "password_different"));
        validator.onErrorUsePageOf(HomeController.class).create();
        
        GenerateHashPasswordUtil generateHashPasswordUtil = new GenerateHashPasswordUtil();
        user.setPassword(generateHashPasswordUtil.generateHash(user.getPassword()));        
        
		userDao.insert(user);
		
		linker.buildLinkTo(HomeController.class).home();
		
		String url = "<a href=\""+linker.getURL()+"\" target=\"_blank\">Clique aqui</a> para acessar o site.";		
		
		String message = "<p>Ol&aacute; " + user.getName()+ ",</p>"
				+ "<p>Sua conta foi criado com sucesso.</p>"
				+ "<p>Seus dados cadastrais:</p>"
				+ "<p>Login: "+user.getLogin()+"</p>"
				+ "<p>Senha:"+repassword+"</p>"
				+ "<p>"+ url +"</p>";
		
		//Send mail
		try {
			mailUtils.send("[TheEND] - Cadastro Realizado", message, user.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// you can add objects to result even in redirects. Added objects will
		// survive one more request when redirecting.
		result.include("notice", new SimpleMessage("create", "create.user.sucess"));
		result.redirectTo(HomeController.class).login();
	}
	
	@Path("/users/seek.json")
	@Get
	@Public
	public void seekJson(String query) {
		List<User> users = userDao.findUserName(query);
		System.out.println("Entrou json " + users.size()+" palavra: " + query);
		result.use(json()).withoutRoot().from(users)
				.exclude("email", "login", "password").serialize();
	}
	
	@Path("/users/profile/{id}")
	@Get
	@Public
	public void profile(Long id) {
		User user = userDao.find(id);
		result.include("user", user);
		result.include("notice", new SimpleMessage("user.profile", "user.profile.sucess"));
		
	}
	
	/*
    @Path("/users/{user.login}/musics/{id}")
    @Put
	public void addToMyList(User user, Long mapId) {
    	User currentUser = userInfo.getUser();
	    userDao.refresh(currentUser);
	    
	    validator.check(user.getLogin().equals(currentUser.getLogin()), 
	            new SimpleMessage("user", "you_cant_add_to_others_list"));

		validator.onErrorUsePageOf(UsersController.class).home();

		MapStudy map = musicDao.find(mapId);
		currentUser.add(map);
		
		result.redirectTo(UsersController.class).home();
	}
    */

}
