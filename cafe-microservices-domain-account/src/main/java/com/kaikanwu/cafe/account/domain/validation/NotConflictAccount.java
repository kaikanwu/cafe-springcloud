package com.kaikanwu.cafe.account.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Constraint(validatedBy = AccountValidation.NotConflictAccountValidator.class)
public @interface NotConflictAccount {
    String message() default "用户名、邮箱、手机号已经被使用";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
