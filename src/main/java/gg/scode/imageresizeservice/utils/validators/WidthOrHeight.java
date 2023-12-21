package gg.scode.imageresizeservice.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = WidthOrHeightValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WidthOrHeight {
    String message() default "Either width or height must be not null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
