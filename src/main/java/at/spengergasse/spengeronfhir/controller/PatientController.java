package at.spengergasse.spengeronfhir.controller;

import at.spengergasse.spengeronfhir.entities.Patient;
import at.spengergasse.spengeronfhir.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/patient/")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Patient> getAllPatients() {
        // This returns a JSON or XML with the users
        return patientRepository.findAll();
    }

    @Operation(summary = "Get a Patient by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Patient",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content) })
    @GetMapping("/{id}") //http://localhost:8080/api/patient/123
    public ResponseEntity<Patient> getPatient(@PathVariable String id) {
        return patientRepository
                .findById(id)
                .map(patient -> ResponseEntity.ok().body(patient))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping()
    public ResponseEntity<Patient> createPatient( @RequestBody Patient patient) {
        patientRepository.save(patient);
        return ResponseEntity.created(URI.create("/api/patient/" + patient.getId()))
                .body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable(value =
            "id") String patientId,@Valid @RequestBody Patient patientDetails) {
        return patientRepository.findById(patientId)
                .map(patient -> {
                    patient.setActive(patientDetails.getActive());
                    patient.setGender(patientDetails.getGender());
                    patient.setIdentifier(patientDetails.getIdentifier());
                    patient.setName(patientDetails.getName());
                    patient.setAddress(patientDetails.getAddress());
                    patient.setBirthDate(patientDetails.getBirthDate());
                    patient.setText(patientDetails.getText());
                    patient.setTelecom(patientDetails.getTelecom());
                    patient.setDeceasedDateTime(patientDetails.getDeceasedDateTime());
                    patient.setDeceasedBoolean(patientDetails.getDeceasedBoolean());
                    Patient updatedPatient =
                            patientRepository.save(patient);
                    return ResponseEntity.ok(updatedPatient);
                }).orElseGet(() -> createPatient(patientDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable(value =
            "id") String patientId) {
        return patientRepository.findById(patientId).map(patient -> {
            patientRepository.delete(patient);
            return ResponseEntity.ok().<Patient>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // ExceptionHandler for ConstraintViolations
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleConstraintValidationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errors;
    }

    // ExceptionHandler for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
