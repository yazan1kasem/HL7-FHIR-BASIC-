package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.*;
import at.spengergasse.spengeronfhir.repository.PractitionerRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestPractitionerRepository {
    @Autowired
    private PractitionerRepository practitionerRepository;

    @Test
    @Order(1)
    @Transactional
    void saveAndLoadOnePractitioner() {
        //1. Erstellen einer mit Daten befüllten Practitioner-Instanz
        Practitioner practitioner = returnOnePractitioner();
        //2. Instanz in die DB speichern
        Practitioner savedPractitioner = practitionerRepository.save(practitioner);

        Practitioner loadedPractitioner =
                practitionerRepository.findById(savedPractitioner.getId()).get();
        System.out.println(loadedPractitioner);

        assertEquals(practitioner.getBirthDate(), loadedPractitioner.getBirthDate());
        assertEquals(practitioner.getGender(), loadedPractitioner.getGender());
        assertTrue(CollectionUtils.isEqualCollection(practitioner.getIdentifier(),
                loadedPractitioner.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(practitioner.getName(),
                loadedPractitioner.getName()));
        assertTrue(CollectionUtils.isEqualCollection(practitioner.getTelecom(),
                loadedPractitioner.getTelecom()));
        assertTrue(CollectionUtils.isEqualCollection(practitioner.getAddress(),
                loadedPractitioner.getAddress()));
    }

    public static Practitioner returnOnePractitioner() {
        List<Identifier> identifiers = new ArrayList<>();
        List<Coding> codings = new ArrayList<>();
        List<ContactPoint> contactPoints = new ArrayList<>();
        List<HumanName> names = new ArrayList<>();
        List<Address> address = new ArrayList<>();
        List<String> givenNames = new ArrayList<>();

        Period period = Period.builder()
                .start(LocalDateTime.of(2000, 01,01,1,1))
                .end(LocalDateTime.of(2001,01,01,1,1))
                .build();

        contactPoints.add(
                ContactPoint.builder()
                        .period(period)
                        .rank(1)
                        .system(ContactPoint.SystemCode.email)
                        .use(ContactPoint.UseCode.home)
                        .value("practitioner@spengergasse.at")
                        .build()
        );

        givenNames.add("John");
        names.add(HumanName.builder()
                .family("Doe")
                .given(givenNames)
                .period(Period.builder().start(LocalDateTime.now()).end(LocalDateTime.now()
                ).build())
                .use(HumanName.UseCode.anonymous)
                .build());

        address.add(
                Address.builder()
                        .city("Vienna")
                        .country("Austria")
                        .district("Vienna")
                        .line(List.of("Spengergasse 20", "Spengergasse 20"))
                        .postalcode("1050")
                        .period(period)
                        .state("Vienna")
                        .text("<div>.../</div>")
                        .type(Address.TypeCode.both)
                        .use(Address.UseCode.home)
                        .build()
        );

        return Practitioner.builder()
                .active(true)
                .birthDate(LocalDate.of(1980, 05, 15))
                .identifier(identifiers)
                .gender(Practitioner.GenderCode.male)
                .name(names)
                .telecom(contactPoints)
                .address(address)
                .build();


    }

    @Test
    @Order(2)
    @Transactional
    void testDeleteOnePractitioner() {
        // Erstellen und Speichern einer Practitioner-Instanz
        Practitioner practitioner = returnOnePractitioner();
        Practitioner savedPractitioner = practitionerRepository.save(practitioner);

        // Löschen der Practitioner-Instanz aus der Datenbank
        practitionerRepository.deleteById(savedPractitioner.getId());

        // Überprüfen, ob die Practitioner-Instanz erfolgreich gelöscht wurde
        assertFalse(practitionerRepository.existsById(savedPractitioner.getId()));
    }

    @Test
    @Order(3)
    @Transactional
    void testUpdateOnePractitioner() {
        // Erstellen und Speichern einer Practitioner-Instanz
        Practitioner practitioner = returnOnePractitioner();
        Practitioner savedPractitioner = practitionerRepository.save(practitioner);

        // Aktualisieren einiger Attribute der Practitioner-Instanz
        savedPractitioner.setActive(false);
        savedPractitioner.setBirthDate(LocalDate.of(1985, 07, 20));

        // Speichern der aktualisierten Practitioner-Instanz in der Datenbank
        Practitioner updatedPractitioner = practitionerRepository.save(savedPractitioner);

        // Überprüfen, ob die Aktualisierung erfolgreich war
        assertEquals(savedPractitioner.getId(), updatedPractitioner.getId());
        assertFalse(updatedPractitioner.getActive());
        assertEquals(LocalDate.of(1985, 07, 20), updatedPractitioner.getBirthDate());
        // Weitere Überprüfungen für andere aktualisierte Attribute
    }

    @Test
    @Order(4)
    @Transactional
    void testOnePractitioner() {
        List<Identifier> identifiers = new ArrayList<>();
        List<HumanName> names = new ArrayList<>();
        List<ContactPoint> contactPoints = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        List<Qualification> qualifications = new ArrayList<>();

        // Identifier
        identifiers.add(
                Identifier.builder()
                        .use(Identifier.UseCode.official)
                        .system("urn:oid:2.16.528.1.1007.3.1")
                        .value("118265112")
                        .build()
        );
        identifiers.add(
                Identifier.builder()
                        .use(Identifier.UseCode.usual)
                        .system("urn:oid:2.16.840.1.113883.2.4.6.3")
                        .value("191REW8WE916")
                        .build()
        );

        // Name
        names.add(
                HumanName.builder()
                        .use(HumanName.UseCode.official)
                        .family("Langeveld")
                        .given(Collections.singletonList("Anne"))
                        .suffix(Collections.singletonList("MD"))
                        .build()
        );

        // Contact Points
        contactPoints.add(
                ContactPoint.builder()
                        .system(ContactPoint.SystemCode.phone)
                        .value("0205563847")
                        .use(ContactPoint.UseCode.work)
                        .build()
        );
        contactPoints.add(
                ContactPoint.builder()
                        .system(ContactPoint.SystemCode.email)
                        .value("a.langeveld@bmc.nl")
                        .use(ContactPoint.UseCode.work)
                        .build()
        );
        contactPoints.add(
                ContactPoint.builder()
                        .system(ContactPoint.SystemCode.fax)
                        .value("0205668916")
                        .use(ContactPoint.UseCode.work)
                        .build()
        );

        // Address
        addresses.add(
                Address.builder()
                        .use(Address.UseCode.work)
                        .line(Collections.singletonList("Galapagosweg 9"))
                        .city("Amsterdam")
                        .postalcode("1105 AZ")
                        .country("NLD")
                        .build()
        );
    }
}
