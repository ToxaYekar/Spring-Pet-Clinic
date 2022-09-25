package spring.petclinic.services;

import spring.petclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudeService<Owner, Long> {

    Owner findByLastName(String lastName);

}
