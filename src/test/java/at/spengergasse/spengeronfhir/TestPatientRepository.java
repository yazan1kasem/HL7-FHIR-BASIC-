package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.*;
import at.spengergasse.spengeronfhir.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestPatientRepository {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    @Order(1)
    @Transactional
    void saveAndLoadOnePatient() {

        //1. Erstellen einer mit Daten befüllten Patienteninstanz
        Patient p = returnOnePatient();
        //2. Instanz in die DB speichern
        Patient savedP = patientRepository.save(p);
        //3. Gespeicherte Daten aus der DB lesen
        Patient loadedPatient =
                patientRepository.findById(savedP.getId()).get();
        System.out.println(loadedPatient);
        //4. Vergleich des gespeicherten Objekts mit dem geladenen
        //Alle einfachen Datentypen können mit Equals verglichen werden.
        //Assert prüft, ob die beiden gleich sind. Schlägt ein Assert fehl, ist der Test fehlgeschlagen
        //Asserts sind die eigentlichen "Tests"
        assertEquals(p.getBirthDate(), loadedPatient.getBirthDate());
        assertEquals(p.getDeceasedDateTime(),
                loadedPatient.getDeceasedDateTime());
        assertEquals(p.getGender(), loadedPatient.getGender());
        assertEquals(p.getText(), loadedPatient.getText());
        //Es sollen alle Attribute verglichen werden, auch die geerbten.
        //Collections werden mit CollectionUtils auf gleichheit getestet.
        // Dabei werden die einzelnen Elemente verglichen,nicht ob die Collectionobjekte gleich sind.
                assertTrue(CollectionUtils.isEqualCollection(p.getIdentifier(),
                        loadedPatient.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(p.getName(),
                loadedPatient.getName()));
        assertTrue(CollectionUtils.isEqualCollection(p.getTelecom(),
                loadedPatient.getTelecom()));
        assertTrue(CollectionUtils.isEqualCollection(p.getAddress(),
                loadedPatient.getAddress()));
        //Es sollen alle Collections getestet werden.
    }
    //Ein Patientenobjekt. Alle Attribute sollen Werte bekommen.
    //Auch die Collections sollen zumindest 1 Wert haben.
    public static Patient returnOnePatient(){
        List<Identifier> identifiers = new ArrayList<>();
        List<Coding> codings = new ArrayList<>();
        List<ContactPoint> contactPoints = new ArrayList<>();
        List<HumanName> names = new ArrayList<>();
        List<Address> address = new ArrayList<>();
        List<String> prefixes = null;
        List<String> suffixes = null;
        //Ein Coding Objekt wird mit wie bisher mit einem Konstruktor gebaut.
                //codings.add(new Coding("System", "0.1.1", "Code", "<div>...<div>",false));
        //Eine Period mit Kontsturktor
        Period period = new Period(LocalDateTime.of(2000, 01,01,1,1),
                LocalDateTime.of(2001,01,01,1,1));
        //Eine Period mit dem Builder Pattern. Es ist offensichtlicher welche Attribute gesetzt werden.
        Period period2 = Period.builder()
                .start(LocalDateTime.of(2000, 01,01,1,1))
                .end(LocalDateTime.of(2010, 02,02,2,2))
                .build();
        Period period3 = Period.builder()
                .start(LocalDateTime.of(2001, 01,01,1,1))
                .end(LocalDateTime.of(2011, 02,02,2,2))
                .build();
//        CodeableConcept ccType = CodeableConcept.builder()
//                .coding(codings)
//                .text("<div></div>")
//                .build();// new CodeableConcept(codings, "Text");
//        identifiers.add(
//                Identifier.builder()
//                        .use(Identifier.UseCode.official)
//                        .period(period)
//                        .system("System")
//                        .type(ccType)
//                        .value("value")
//                        .build()
//        );
        contactPoints.add(
                ContactPoint.builder()
                        .period(period2)
                        .rank(1)
                        .system(ContactPoint.SystemCode.email)
                        .use(ContactPoint.UseCode.home)
                        .value("pirker@spengergasse.at")
                        .build()
                //new ContactPoint(ContactPoint.SystemCode.phone, "123454321", ContactPoint.UseCode.home, 1, period2)
 );
        List<String> givenNames = new ArrayList<>();
        givenNames.add("Simon");
        givenNames.add("2.Vorname");
        names.add(HumanName.builder()
                .family("Pirker")
                .given(givenNames)

                .period(Period.builder().start(LocalDateTime.now()).end(LocalDateTime.now()
                ).build())
                .use(HumanName.UseCode.anonymous)
                .build());
        address.add(
                Address.builder()
                        .city("Wien")
                        .country("Österreich")
                        .district("Wien")
                        .line(List.of("Spengergasse 20", "Spengergasse 20"))
                        .postalcode("1050")
                        .period(period3)
                        .state("Wien")
                        .text("<div>.../</div>")
                        .type(Address.TypeCode.both)
                        .use(Address.UseCode.home)
                        .build()
        );
        return Patient.builder()
                .active(true)
                .birthDate(LocalDate.of(1999, 01, 01))
                .identifier(identifiers)
                .deceasedBoolean(false)
                .gender(Patient.GenderCode.male)
                .name(names)
                .telecom(contactPoints)
                .address(address)
                .build();
    }


    }

