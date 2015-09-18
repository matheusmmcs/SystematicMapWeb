package br.com.ufpi.systematicmap.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.ufpi.systematicmap.validation.impl.EmailAvailableValidator;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EmailAvailableValidator.class })
@Documented
public @interface EmailAvailable {

    String message() default "{email_already_exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
