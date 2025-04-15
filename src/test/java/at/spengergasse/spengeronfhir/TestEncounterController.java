package at.spengergasse.spengeronfhir;

import at.spengergasse.spengeronfhir.entities.Encounter;
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

//Testen der Controller Funktionen
@SpringBootTest
@AutoConfigureMockMvc
public class TestEncounterController {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Test
    public void getAllEncounters() { try {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/encounter"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    //in import: Encounter mit der id einfügen
    @Test
    public void
    getAnEncounter(){
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/encounter/f201"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void
    postAnEncounter(){
        Encounter encounter = TestEncounterRepository.returnOneEncounter();
        String json= null;
        try {
            json = om.writeValueAsString(encounter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.post("/api/encounter/")
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
    public void
    putAnEncounter(){
        Encounter encounter= TestEncounterRepository.returnOneEncounter();
        encounter.setId("f201");
        String json= null;
        try {
            json = om.writeValueAsString(encounter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc

                    .perform(MockMvcRequestBuilders.put("/api/encounter/f201")
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
    public void
    deleteAnEncounter(){
        try {
            mockMvc

                    .perform(MockMvcRequestBuilders.delete("/api/encounter/f201"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
