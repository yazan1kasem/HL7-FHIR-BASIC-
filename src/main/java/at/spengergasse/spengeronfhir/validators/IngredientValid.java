package at.spengergasse.spengeronfhir.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IngredientValidator.class})
@Documented
public @interface IngredientValid {
    String message() default "invalid";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
