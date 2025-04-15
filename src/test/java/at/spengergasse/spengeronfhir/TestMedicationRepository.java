package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.*;
import at.spengergasse.spengeronfhir.repository.MedicationRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestMedicationRepository {

    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    @Order(1)
    @Transactional
    void saveAndLoadOneMedication() {

        Medication m = returnOneMedication();

        Medication savedM = medicationRepository.save(m);

        Medication loadedMedication = medicationRepository.findById(savedM.getId()).get();
        System.out.println(loadedMedication);

        assertEquals(m.getStatus(), loadedMedication.getStatus());
        assertEquals(m.getCode(), loadedMedication.getCode());
        assertEquals(m.getManufacturer(), loadedMedication.getManufacturer());
        assertEquals(m.getForm(), loadedMedication.getForm());
        assertEquals(m.getAmount(), loadedMedication.getAmount());
        assertEquals(m.getBatch(), loadedMedication.getBatch());

        assertTrue(CollectionUtils.isEqualCollection(m.getIdentifier(), loadedMedication.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(m.getIngredient(), loadedMedication.getIngredient()));
    }

    public static Medication returnOneMedication() {
        List<Identifier> identifiers = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        List<Coding> codings1 = new ArrayList<>();
        List<Coding> codings2 = new ArrayList<>();
        List<Coding> codings3 = new ArrayList<>();

        identifiers.add(Identifier.builder()
                .use(Identifier.UseCode.temp)
                .value("med0336")
                .build());

        codings1.add(Coding.builder()
                .system("http://snomed.info/sct")
                .code("385219001")
                .display("Injection Solution (qualifier value)")
                .build());

        CodeableConcept form = CodeableConcept.builder()
                .coding(codings1)
                .text("Injection Solution (qualifier value)")
                .build();

        codings2.add(Coding.builder()
                .system("http://www.nlm.nih.gov/research/umls/rxnorm")
                .code("1114879")
                .display("Dopamine")
                .build());

        ingredients.add(Ingredient.builder()
                .itemCodeableConcept(CodeableConcept.builder()
                        .coding(codings2)
                        .build())
                .strength(new Ratio(
                        new Quantity("400", "http://unitsofmeasure.org", "mg"),
                        new Quantity("500", "http://unitsofmeasure.org", "mL")
                ))
                .build());

        codings3.add(Coding.builder()
                .system("http://hl7.org/fhir/sid/ndc")
                .code("0264751010")
                .display("Dextrose 5% injection USP")
                .build());

        ingredients.add(Ingredient.builder()
                .itemCodeableConcept(CodeableConcept.builder()
                        .coding(codings3)
                        .build())
                .strength(new Ratio(
                        new Quantity("5", "http://unitsofmeasure.org", "g"),
                        new Quantity("100", "http://unitsofmeasure.org", "mL")
                ))
                .build());

        Batch batch = Batch.builder()
                .lotNumber("AT 9035 ZU69")
                .expirationDate(LocalDate.of(2030, 11, 30))
                .build();

        return Medication.builder()
                .id("med0336")
                .status(Medication.StatusCode.active)
                .form(form)
                .identifier(identifiers)
                .ingredient(ingredients)
                .batch(batch)
                .build();
    }
}
