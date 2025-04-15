package at.spengergasse.spengeronfhir.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) //Bei Feldern
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SSNValidator.class})
@Documented
public @interface SSNValid {
    // Fehlermeldung
    String message() default "SSN invalid";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
