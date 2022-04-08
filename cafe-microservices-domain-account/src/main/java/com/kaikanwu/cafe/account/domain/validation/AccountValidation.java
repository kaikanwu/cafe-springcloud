package com.kaikanwu.cafe.account.domain.validation;

import com.kaikanwu.cafe.account.domain.AccountRepository;
import com.kaikanwu.cafe.infrastructure.domain.account.Account;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Predicate;

public class AccountValidation<T extends Annotation> implements ConstraintValidator<T, Account> {
    @Autowired
    protected AccountRepository repository;

    protected Predicate<Account> predicate = c -> true;


    @Override
    public boolean isValid(Account value, ConstraintValidatorContext context) {
        return repository == null || predicate.test(value);
    }


    public static class NotConflictAccountValidator extends AccountValidation<NotConflictAccount> {
        public void initialize(NotConflictAccount constraintAnnotation) {
            predicate = c -> {
                if (c.getId() == null) {
                    return false;
                }
                Collection<Account> collection = repository.findByUsernameOrEmailOrTelephone(c.getUsername(), c.getEmail(), c.getTelephone());
                return collection.isEmpty() || (collection.size() == 1) && collection.iterator().next().getId().equals(c.getId());
            };
        }
    }
}

