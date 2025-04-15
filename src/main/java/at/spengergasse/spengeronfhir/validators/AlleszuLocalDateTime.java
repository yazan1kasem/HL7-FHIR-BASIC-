package at.spengergasse.spengeronfhir.validators;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

public class AlleszuLocalDateTime extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return LocalDateTime.parse(p.getText());
        } catch (Exception e) {
            try {
                LocalDate localDate = LocalDate.parse(p.getText());
                return localDate.atStartOfDay();
            } catch (Exception ex) {
                try {
                    Date date = new Date(p.getText());
                    return date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                }catch (Exception exc){
                    Logger logger = Logger.getLogger("a.s.s.SpengerOnFhirApplication");
                    logger.warning("Die Eingabe fürs "+p+" ist nicht gültig, der Wert würde auf null gesetzt");
                    return null;
                }
            }
        }
    }
}