package at.spengergasse.spengeronfhir.repository;

import at.spengergasse.spengeronfhir.entities.Resource;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository <T extends Resource> extends ListCrudRepository<T, String> {
}
