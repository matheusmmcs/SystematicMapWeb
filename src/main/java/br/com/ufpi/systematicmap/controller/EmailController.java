package br.com.ufpi.systematicmap.controller;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.interceptor.Public;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.utils.EmailUltils;
import br.com.ufpi.systematicmap.utils.GenerateHashPasswordUtil;

@Controller
@Named
public class EmailController {
	private final UserDao userDao;
	private final Result result;
	private final Validator validator;

	protected EmailController() {
		this(null, null, null);
	}

	@Inject
	public EmailController(UserDao userDao, Result result,Validator validator) {
		this.userDao = userDao;
		this.result = result;
		this.validator = validator;
	}
	
	@Public
	@Post
	@Path("/mail/recovery")
	public void recoveryPassword(String email){
		boolean sucess = false;
		User user = userDao.findEmail(email);
//		System.out.println("e-mail: " + email);
		validator.check(user != null, new SimpleMessage("user.email", "invalid_email"));
		validator.onErrorUsePageOf(HomeController.class).recovery();
		
		Random random = new Random();
		
		// Gerar code de recuperação
		String code = GenerateHashPasswordUtil.generateCodeRecovery(user.getLogin() + random.nextLong());
		
		user.setRecoveryCode(code);
		userDao.update(user);
		
		String linkRecovery = "http://localhost:8080/SystematicMap/recovery/";
		linkRecovery = linkRecovery.concat(code);
		
		String url = "<a href=\""+linkRecovery+"\" target=\"_blank\">Clique aqui</a> para criar uma nova senha";
		
//		System.out.println(linkRecovery);
//		System.out.println(url);		
		
		
		//Send Email
		try {
			EmailUltils.RECEIVER_NAME = user.getName();
			EmailUltils.RECEIVER_EMAIL = user.getEmail();
			EmailUltils.MENSSAGE = "<p>Ol&aacute; " + user.getName()+ ",</p>"
					+ "<p>Seu pedido de altera&ccedil;&atilde;o de senha foi atendido com sucesso pelo sistema.</p>"
					+ "<p>Clique no link a seguir para realizar a altera&ccedil;&atilde;o de sua senha.</p>"
					+ "<p>"+ url +"</p>";
			EmailUltils.SUBJECT = "[TheEND] - Solicitação de alteração de senha";
			EmailUltils.send();
			sucess = true;
		} catch (Exception e) {
			sucess = false;
//			e.printStackTrace();
		}
		
		validator.check(sucess, new SimpleMessage("user.email", "error_email"));
		// if send mail fail, clear recovery code.
		if (!sucess){
			System.out.println("Erro !");
			user.setRecoveryCode(null);
			userDao.update(user);
		}
		
		validator.onErrorUsePageOf(HomeController.class).recovery();
		
//		result.include("notice", "Um e-mail foi enviado para " + user.getEmail() );
		validator.add(new SimpleMessage("user.email", "Um e-mail foi enviado para " + user.getEmail(), Severity.INFO));
		result.redirectTo(HomeController.class).recovery();
	}
	
	@Public
	@Get("/recovery/{code}")
	public void validateCode(String code){
		final User user = userDao.findCodeRecovery(code);
		
		validator.check(user != null, new SimpleMessage("user.email", "invalid_code_recovery"));
		validator.onErrorUsePageOf(HomeController.class).recovery();
		
		result.include("code", code);
	}
	
	@Public
	@Post("/recovery/newpassword")
	public void newPassword(String code, String password, String repassword){
		final User user = userDao.findCodeRecovery(code);
		
		validator.check(password.equals(repassword), new SimpleMessage("user.password", "password_different"));
		validator.onErrorRedirectTo(this).validateCode(code);
		
		user.setRecoveryCode(null);
		userDao.update(user);		
		
		result.include("notice", "Senha alterada com sucesso !");
		result.redirectTo(HomeController.class).login();	
	}
}
