package at.spengergasse.spengeronfhir.repository;

import at.spengergasse.spengeronfhir.entities.Medication;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends GenericRepository<Medication> {
}
