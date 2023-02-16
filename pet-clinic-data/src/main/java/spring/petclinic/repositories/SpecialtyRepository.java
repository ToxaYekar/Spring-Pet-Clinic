package spring.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.petclinic.model.Speciality;

public interface SpecialtyRepository extends CrudRepository<Speciality, Long> {
}
