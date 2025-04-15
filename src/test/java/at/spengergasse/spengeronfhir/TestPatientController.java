package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.Narrative;
import at.spengergasse.spengeronfhir.entities.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPatientController {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Test
    public void getAllPatients() { try {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/patient"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    //Ein einzelner Patient wird mit der id mit GET abgefragt. Dabei muss der Patient mit der id in der DB existieren.
            //im import.sql muss dieser Patient somit eingefügt werden.
            //andExpect überprüft, ob der zurückgegebene Status 200 (OK) ist.
    @Test
    public void
    getAPatient(){
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/patient/asdf"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Es wird ein neuer Patient mit POST an den Controller geschickt und somit in der DB gespeichert.
    //Wir können die Methode aus PatientRepositoryTest, die uns eine Patienten Instanz erzeugt auch hier verwenden.
    //Der erwartete Rückgabecode ist "CREATED" Also 201.
    @Test
    public void
    postAPatient(){
        Patient patient = TestPatientRepository.returnOnePatient();
        String json= null;
        try {
            json = om.writeValueAsString(patient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/patient/")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //PUT aktualisiert einen Patienten. Dieser muss somit bereits in der DB existieren (über import.sql)
    //Die id im Patienten und die id in der URL sollten gesetzt sein und müssen in der DB existieren.
            //Wir erwarten ein 200- OK für einen aktualisierten Patienten.
            //Kein 201 CREATED, sonst wäre der Patient neu angelegt worden.
    @Test
    public void
    putAPatient(){
        Patient patient= TestPatientRepository.returnOnePatient();
        patient.setId("7439re");
        String json= null;
        try {
            json = om.writeValueAsString(patient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc

                    .perform(MockMvcRequestBuilders.put("/api/patient/7439re")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Der Patient wird geöscht.
    //Die id muss es bereits geben.
    //Erwartete Antwort ist 200 (OK)
    @Test
    public void
    deleteAPatient(){
        try {
            mockMvc

                    .perform(MockMvcRequestBuilders.delete("/api/patient/gjuerighirgh"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Validator wird getestet, wenn deceasedDateTime und deceasedBoolean beide gesetzt sind
    //soll ein 400 bad Request retourniert werden.
    @Test
    @Transactional
    public void
    postInvalidDeceasedPatient(){
        Patient patient = TestPatientRepository.returnOnePatient();
        //patient.setDeceasedDateTime(LocalDateTime.now());
        patient.setDeceasedDateTime(LocalDateTime.now());
        patient.setDeceasedBoolean(true);
        String json= null;
        try {
            json = om.writeValueAsString(patient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/patient/")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Validator wird getestet, ob in Narrative (text) beide Attribute nicht null sind
    //soll ein 400 bad Request retourniert werden.
    @Test
    public void
    putInvalidNarrativePatient(){
        Patient patient = TestPatientRepository.returnOnePatient();
        Narrative narrative = new Narrative();
        narrative.setStatus(null);
        narrative.setDiv(null);
        patient.setText(narrative);
    //patient.setActive(null);
        String json= null;
        try {
            json = om.writeValueAsString(patient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/patient")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
