package at.spengergasse.spengeronfhir.repository;

import at.spengergasse.spengeronfhir.entities.Encounter;
import at.spengergasse.spengeronfhir.entities.Patient;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncounterRepository extends ListCrudRepository<Encounter, String> {
}
