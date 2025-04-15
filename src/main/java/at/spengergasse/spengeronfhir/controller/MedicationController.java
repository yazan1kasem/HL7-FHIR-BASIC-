package at.spengergasse.spengeronfhir.controller;

import at.spengergasse.spengeronfhir.entities.Medication;
import at.spengergasse.spengeronfhir.repository.MedicationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/medication/")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedication(@PathVariable String id) {
        return medicationRepository
                .findById(id)
                .map(medication -> ResponseEntity.ok().body(medication))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Medication> createMedication(@Valid @RequestBody Medication medication) {
        medicationRepository.save(medication);
        return ResponseEntity.created(URI.create("/api/medication/" + medication.getId()))
                .body(medication);
    }

    @PutMapping("{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable(value = "id") String medicationId, @Valid @RequestBody Medication medicationDetails) {
        return medicationRepository.findById(medicationId)
                .map(medication -> {
                    medication.setIdentifier(medicationDetails.getIdentifier());
                    medication.setCode(medicationDetails.getCode());
                    medication.setStatus(medicationDetails.getStatus());
                    medication.setManufacturer(medicationDetails.getManufacturer());
                    medication.setForm(medicationDetails.getForm());
                    medication.setAmount(medicationDetails.getAmount());
                    medication.setIngredient(medicationDetails.getIngredient());
                    medication.setBatch(medicationDetails.getBatch());

                    Medication updatedMedication = medicationRepository.save(medication);
                    return ResponseEntity.ok(updatedMedication);
                }).orElseGet(() -> createMedication(medicationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medication> deleteMedication(@PathVariable String id) {
        medicationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

