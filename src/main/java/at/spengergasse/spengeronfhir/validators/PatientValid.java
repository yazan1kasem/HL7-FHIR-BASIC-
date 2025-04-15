package at.spengergasse.spengeronfhir.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE}) //Auf Klassenebene
@Retention(RetentionPolicy.RUNTIME)
// Welche Klasse führt die Validierung durch
@Constraint(validatedBy = {PatientValidator.class})
@Documented
public @interface PatientValid { // Name der Annotation
    // Fehlermeldung, wenn Validation fehlschlägt
    String message() default "Patient.deceasedBoolean and Patient.decesaedDateTime must not both have a value";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
