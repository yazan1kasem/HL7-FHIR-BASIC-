package at.spengergasse.spengeronfhir.repository;


import at.spengergasse.spengeronfhir.entities.Practitioner;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PractitionerRepository extends ListCrudRepository<Practitioner, String> {
}
