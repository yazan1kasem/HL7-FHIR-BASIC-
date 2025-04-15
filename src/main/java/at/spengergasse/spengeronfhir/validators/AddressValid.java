package at.spengergasse.spengeronfhir.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AddressValidator.class})
@Documented
public @interface AddressValid {
    String message() default "Address invalid";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
