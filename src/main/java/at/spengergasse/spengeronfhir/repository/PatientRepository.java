package at.spengergasse.spengeronfhir.repository;

import at.spengergasse.spengeronfhir.entities.Patient;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends ListCrudRepository<Patient, String> {


}
