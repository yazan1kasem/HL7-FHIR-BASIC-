package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.Medication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TestMedicationController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Test
    public void getAllMedications() {
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/medication/"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAMedication() {
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/medication/med0336"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAMedication() {
        Medication medication = TestMedicationRepository.returnOneMedication();
        String json = null;
        try {
            json = om.writeValueAsString(medication);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/medication/")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void putAMedication() {
        Medication medication = TestMedicationRepository.returnOneMedication();
        medication.setId("med0336");
        String json = null;
        try {
            json = om.writeValueAsString(medication);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.put("/api/medication/med0336")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAMedication() {
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.delete("/api/medication/med0336"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
