package at.spengergasse.spengeronfhir.validators;

import at.spengergasse.spengeronfhir.entities.Identifier;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;

public class SSNValidator implements ConstraintValidator<SSNValid, String> {

    private WebClient client;

    @Override
    public void initialize(SSNValid constraintAnnotation) {
        this.client = WebClient.builder()
                .baseUrl("http://www2.argedaten.at")
                .build();
    }


    @Override
    public boolean isValid(String svnr, ConstraintValidatorContext context) {

        if (svnr == null || svnr.length() < 10) {
            return false;
        }
        // svnr ausgeben
        System.out.println(svnr);
        String ssn = svnr.substring(0, 4);
        String dob = svnr.substring(4, 10);

        // Anfrage an Prüftool-Seite
        WebClient.ResponseSpec responseSpec = client.get()
                .uri(String.format("/php/svnummer.php?str[1]=%s&str[2]=%s", ssn, dob))
                .retrieve();

        // Antwort
        String responseBody = responseSpec.bodyToMono(String.class).block();

        // Ist die Antwort ok?
        if (responseBody == null) {
            return false;
        }
        return responseBody.contains("OK!");
    }
}
