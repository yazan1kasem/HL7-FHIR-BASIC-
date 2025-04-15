package at.spengergasse.spengeronfhir.controller;

import at.spengergasse.spengeronfhir.entities.Practitioner;
import at.spengergasse.spengeronfhir.repository.PractitionerRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/practitioner/")
public class PractitionerController {
    @Autowired
    private PractitionerRepository practitionerRepository;
    @GetMapping
    public @ResponseBody
    Iterable<Practitioner> getAllPractitioners() {
        // This returns a JSON or XML with the users
        return practitionerRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Practitioner> getPractitioner(@PathVariable String id) {
        return practitionerRepository
                .findById(id)
                .map(practitioner -> ResponseEntity.ok().body(practitioner))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping()
    public ResponseEntity<Practitioner> createPractitioner(@RequestBody
                                                           Practitioner practitioner) {
        practitionerRepository.save(practitioner);
        return ResponseEntity.created(URI.create("/api/practitioner/" + practitioner.getId()))
                .body(practitioner);
    }
    @PutMapping("{id}")
    public ResponseEntity<Practitioner> updatePractitioner(@PathVariable(value =
            "id") String practitionerId, @RequestBody Practitioner practitionerDetails) {
        return practitionerRepository.findById(practitionerId)
                .map(practitioner -> {
                    practitioner.setActive(practitionerDetails.getActive());
                    practitioner.setBirthDate(practitionerDetails.getBirthDate());
                    practitioner.setGender(practitionerDetails.getGender());
                    practitioner.setIdentifier(practitionerDetails.getIdentifier());
                    practitioner.setName(practitionerDetails.getName());
                    practitioner.setAddress(practitionerDetails.getAddress());
                    practitioner.setTelecom(practitionerDetails.getTelecom());
                    practitioner.setQualification(practitionerDetails.getQualification());
                    practitioner.setCommunication(practitionerDetails.getCommunication());
                    practitioner.setPhoto(practitionerDetails.getPhoto());
                    Practitioner updatedPractitioner =
                            practitionerRepository.save(practitioner);
                    return ResponseEntity.ok(updatedPractitioner);
                }).orElseGet(() -> createPractitioner(practitionerDetails));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Practitioner> deletePractitioner(@PathVariable(value =
            "id") String practitionerId) {
        return practitionerRepository.findById(practitionerId).map(practitioner -> {
            practitionerRepository.delete(practitioner);
            return ResponseEntity.ok().<Practitioner>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
