package at.spengergasse.spengeronfhir.validators;

import at.spengergasse.spengeronfhir.entities.Address;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;

//nicht 100% fertig/richtig
public class AddressValidator implements ConstraintValidator<AddressValid, Address> {

    private WebClient client;
    private ObjectMapper objectMapper;

    @Override
    public void initialize(AddressValid constraintAnnotation) {
        //initialisieren
        this.client = WebClient.builder()
                .baseUrl("https://data.wien.gv.at")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean isValid(Address address, ConstraintValidatorContext context) {
        if (address == null || address.getText() == null) {
            return false;
        }

        String addressText = address.getText();

        try {
            // Anfrage an den OGD Address Service senden
            String response = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/daten/OGDAddressService.svc/GetAddressInfo")
                            .queryParam("Address", addressText)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Überprüfen, ob die Antwort nicht null oder leer ist
            if (response != null && !response.isEmpty()) {
                try {
                    // JSON-Antwort parsen
                    JsonNode rootNode = objectMapper.readTree(response);
                    JsonNode featuresNode = rootNode.path("features");

                    // Überprüfen, ob die Adresse in den Features enthalten ist
                    for (JsonNode feature : featuresNode) {
                        String streetName = feature.path("properties").path("StreetName").asText();

                        if (streetName.equalsIgnoreCase(addressText)) {
                            // Gültig, wenn exakt gleiche Adresse gefunden
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Fehler beim Parsen der JSON-Antwort
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (Exception e) {
            // Fehler bei der Anfrage an den OGD Address Service
            e.printStackTrace();
            return false;
        }

        return false;
    }

}
