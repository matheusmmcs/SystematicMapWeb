package br.com.ufpi.systematicmap.validation.impl;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.ufpi.systematicmap.dao.UserDao;
import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.validation.EmailAvailable;

public class EmailAvailableValidator implements ConstraintValidator<EmailAvailable, User> {
    
    @Inject
    private UserDao userDao;

    @Override
    public void initialize(EmailAvailable constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
    	return !userDao.containsUserWithEMail(user.getEmail());
    }
}
