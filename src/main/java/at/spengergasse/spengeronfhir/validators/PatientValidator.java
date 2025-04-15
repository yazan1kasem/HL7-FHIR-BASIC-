package at.spengergasse.spengeronfhir.validators;

import at.spengergasse.spengeronfhir.entities.Coding;
import at.spengergasse.spengeronfhir.entities.Identifier;
import at.spengergasse.spengeronfhir.entities.Patient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class PatientValidator implements ConstraintValidator<PatientValid, Patient> {
    @Override
    public void initialize(PatientValid constraintAnnotation){
    }
    @Override
    public boolean isValid(Patient patient, ConstraintValidatorContext
            context) {
        boolean isValid = true;
        if (patient.getDeceasedBoolean() != null && patient.getDeceasedDateTime() != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Patient.deceasedBoolean and Patient.deceasedDateTime must not both have a value")
                    .addConstraintViolation();
            isValid = false;
        }

        List<Identifier> identifiers = patient.getIdentifier();
        for (Identifier identifier : identifiers) {
            if (identifier.getType() != null && !identifier.getType().getCoding().isEmpty()) {
                for (Coding coding : identifier.getType().getCoding()) {
                    if (coding.getDisplay() != null && coding.getDisplay().equals("Sozialversicherungsnummer")) {
                        if (!isValidSVNR(identifier.getValue())) {
                            context.disableDefaultConstraintViolation();
                            context.buildConstraintViolationWithTemplate("Invalid Sozialversicherungsnummer (SVNR): " + identifier.getValue())
                                    .addConstraintViolation();
                            isValid = false;
                        }
                    }
                }
            }
        }
        return isValid;
    }
    WebClient client = WebClient.create();

    private boolean isValidSVNR(String value) {

        if (value == null || value.length() < 10) {
            return false;
        }
        if(value.length()>10){
            return false;
        }
        String str1 = value.substring(0, 4);
        String str2 = value.substring(4, 10);

        WebClient.ResponseSpec responseSpec = client.get()
                .uri(String.format("http://www2.argedaten.at/php/svnummer.php?str[1]=%s&str[2]=%s", str1, str2))
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();

        if (responseBody == null) {
            return false;
        }
        return responseBody.contains("OK!");
    }

}
