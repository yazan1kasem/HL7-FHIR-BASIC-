package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.*;
import at.spengergasse.spengeronfhir.repository.EncounterRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestEncounterRepository {

    @Autowired
    private EncounterRepository encounterRepository;

    @Test
    @Order(1)
    @Transactional
    void saveAndLoadOnePatient() {

        Encounter en = returnOneEncounter();
        Encounter savedEn = encounterRepository.save(en);
        Encounter loadedEncounter =
                encounterRepository.findById(savedEn.getId()).get();
        System.out.println(loadedEncounter);
        assertEquals(en.getStatus(), loadedEncounter.getStatus());
        assertEquals(en.getSubject(), loadedEncounter.getSubject());
        assertEquals(en.getPeriod(), loadedEncounter.getPeriod());
        assertEquals(en.getPartOf(), loadedEncounter.getPartOf());


        assertTrue(CollectionUtils.isEqualCollection(en.getIdentifier(),loadedEncounter.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(en.getStatusHistory(),
                loadedEncounter.getStatusHistory()));
        assertTrue(CollectionUtils.isEqualCollection(en.getType(),
                loadedEncounter.getType()));
        assertTrue(CollectionUtils.isEqualCollection(en.getEpisodeOfCare(),
                loadedEncounter.getEpisodeOfCare()));
        assertTrue(CollectionUtils.isEqualCollection(en.getParticipant(),
                loadedEncounter.getParticipant()));
        assertTrue(CollectionUtils.isEqualCollection(en.getAppointment(),
                loadedEncounter.getAppointment()));
        assertTrue(CollectionUtils.isEqualCollection(en.getReasonReference(),
                loadedEncounter.getReasonReference()));
        assertTrue(CollectionUtils.isEqualCollection(en.getDiagnosis(),
                loadedEncounter.getDiagnosis()));

    }

    //https://jsonformatter.org/json-reader
    public static Encounter returnOneEncounter() {
        List<Identifier> identifiers = new ArrayList<>();
        List<Diagnosis> diagnosis = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();
        List<CodeableConcept> types = new ArrayList<>();
        List<StatusHistory> statusHistoryList = new ArrayList<>();
        List<Reference> appointments = new ArrayList<>();
        List<Coding> codings1 = new ArrayList<>();
        List<Coding> codings2 = new ArrayList<>();
        List<Coding> codings3 = new ArrayList<>();
        List<Coding> codings4 = new ArrayList<>();

        //Identifier
        identifiers.add(Identifier.builder()
                .use(Identifier.UseCode.temp)
                .value("Encounter_Roel_20130404")
                .build());

        codings1.add(Coding.builder()
                .system("http://snomed.info/sct")
                .code("11429006")
                .display("Consultation")
                .build());
        //Type
        types.add(CodeableConcept.builder()
                .coding(codings1)
                .build());

        //Participant
        participants.add(Participant.builder()
                .individual(new Reference("Practitioner/f201", null))
                .build());

        codings2.add(Coding.builder()
                .system("http://terminology.hl7.org/CodeSystem/diagnosis-role")
                .code("AD")
                .display("Admission diagnosis")
                .build());

        codings3.add(Coding.builder()
                .system("http://terminology.hl7.org/CodeSystem/diagnosis-role")
                .code("CC")
                .display("Chief complaint")
                .build());

        //Diagnosis
        diagnosis.add(Diagnosis.builder()
                .condition(new Reference(null, "Complications from Roel's TPF chemotherapy on January 28th, 2013"))
                .use(CodeableConcept.builder()
                        .coding(codings2)
                        .build())
                .rank(2)
                .build());
        diagnosis.add(Diagnosis.builder()
                .condition(new Reference(null, "The patient is treated for a tumor"))
                .use(CodeableConcept.builder()
                        .coding(codings3)
                        .build())
                .rank(1)
                .build());

        //Appointment
        appointments.add(new Reference("Appointment/example", null));

        //PartOf
        Reference partOf = new Reference("Encounter/f203", null);

        //StatusHistory
        statusHistoryList.add(StatusHistory.builder()
                .status(StatusHistory.StatusCode.in_progress)
                .period(new Period(LocalDateTime.of(2013, 3, 8, 0, 0), null))
                .build());

        return Encounter.builder()
                .id("f201")
                .text(Narrative.builder()
                        .status(Narrative.NarrativeStatus.generated)
                        .div("<div xmlns=\"http://www.w3.org/1999/xhtml\"><p><b>Generated Narrative</b></p><div style=\"display: inline-block; background-color: #d9e0e7; padding: 6px; margin: 4px; border: 1px solid #8da1b4; border-radius: 5px; line-height: 60%\"><p style=\"margin-bottom: 0px\">Resource &quot;f201&quot; </p></div><p><b>identifier</b>: id: Encounter_Roel_20130404 (TEMP)</p><p><b>status</b>: finished</p><p><b>class</b>: ambulatory (Details: http://terminology.hl7.org/CodeSystem/v3-ActCode code AMB = 'ambulatory', stated as 'ambulatory')</p><p><b>type</b>: Consultation <span style=\"background: LightGoldenRodYellow; margin: 4px; border: 1px solid khaki\"> (<a href=\"https://browser.ihtsdotools.org/\">SNOMED CT</a>#11429006)</span></p><p><b>priority</b>: Normal <span style=\"background: LightGoldenRodYellow; margin: 4px; border: 1px solid khaki\"> (<a href=\"https://browser.ihtsdotools.org/\">SNOMED CT</a>#17621005)</span></p><p><b>subject</b>: <a href=\"patient-f201.html\">Patient/f201: Roel</a> &quot;Roel&quot;</p><h3>Participants</h3><table class=\"grid\"><tr><td>-</td><td><b>Actor</b></td></tr><tr><td>*</td><td><a href=\"practitioner-f201.html\">Practitioner/f201</a> &quot;Dokter Bronsig&quot;</td></tr></table><p><b>serviceProvider</b>: <a href=\"organization-f201.html\">Organization/f201</a> &quot;Artis University Medical Center (AUMC)&quot;</p></div>")
                        .build())
                .identifier(identifiers)
                .status(Encounter.StatusCode.finished)
                .type(types)
                .subject(new Reference("Patient/f201", "Roel"))
                .participant(participants)
                .diagnosis(diagnosis)
                .appointment(appointments)
                .partOf(partOf)
                .statusHistory(statusHistoryList)
                .build();
    }
}
