package at.spengergasse.spengeronfhir.controller;

import at.spengergasse.spengeronfhir.entities.Encounter;
import at.spengergasse.spengeronfhir.repository.EncounterRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/encounter/")
public class EncounterController {

    @Autowired
    private EncounterRepository encounterRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Encounter> getAllEncounters() {
        return encounterRepository.findAll();
    }

    @Operation(summary = "Get an encounter by its id")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Found the encounter",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Encounter.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied",
            content = @Content),
    @ApiResponse(responseCode = "404", description = "Encounter not found",
            content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounter(@PathVariable String id) {
        return encounterRepository
                .findById(id)
                .map(encounter -> ResponseEntity.ok().body(encounter))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new encounter")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Encounter created successfully",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Encounter.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid input provided") })
    @PostMapping()
    public ResponseEntity<Encounter> createEncounter(@Valid @RequestBody Encounter encounter) {
        encounter.setId(null); // ensure to create new names
        var saved = encounterRepository.save(encounter);
        return ResponseEntity
                .created(URI.create("/api/encounter/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encounter> updateEncounter(@PathVariable(value =
            "id") String encounterId, @Valid @RequestBody Encounter encounterDetails) {
        return encounterRepository.findById(encounterId)
                .map(encounter -> {
                    encounter.setIdentifier(encounterDetails.getIdentifier());
                    encounter.setStatus(encounterDetails.getStatus());
                    encounter.setStatusHistory(encounterDetails.getStatusHistory());
                    encounter.setType(encounterDetails.getType());
                    encounter.setSubject(encounterDetails.getSubject());
                    encounter.setEpisodeOfCare(encounterDetails.getEpisodeOfCare());
                    encounter.setParticipant(encounterDetails.getParticipant());
                    encounter.setAppointment(encounterDetails.getAppointment());
                    encounter.setPeriod(encounterDetails.getPeriod());
                    encounter.setReasonReference(encounterDetails.getReasonReference());
                    encounter.setDiagnosis(encounterDetails.getDiagnosis());
                    encounter.setPartOf(encounterDetails.getPartOf());
                    Encounter updatedEncounter =
                            encounterRepository.save(encounter);
                    return ResponseEntity.ok(updatedEncounter);
                }).orElseGet(() -> createEncounter(encounterDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Encounter> deleteEncounter(@PathVariable(value =
            "id") String encounterId) {
        return encounterRepository.findById(encounterId).map(encounter -> {
            encounterRepository.delete(encounter);
            return ResponseEntity.ok().<Encounter>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
