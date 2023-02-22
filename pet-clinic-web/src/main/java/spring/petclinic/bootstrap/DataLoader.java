package spring.petclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.petclinic.model.*;
import spring.petclinic.services.*;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("Fifth Avenue");
        owner1.setCity("New York");
        owner1.setTelephone("23213122321");

        Pet michaelPet = new Pet();
        michaelPet.setPetType(savedDogPetType);
        michaelPet.setOwner(owner1);
        michaelPet.setBirthDate(LocalDate.now());
        michaelPet.setName("Goofy");
        owner1.getPets().add(michaelPet);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("Fifth Avenue");
        owner2.setCity("New York");
        owner2.setTelephone("23213122321");

        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedCatPetType);
        fionasPet.setOwner(owner2);
        fionasPet.setBirthDate(LocalDate.now());
        fionasPet.setName("Mauricio");
        owner2.getPets().add(fionasPet);

        ownerService.save(owner1);
        ownerService.save(owner2);

        Visit catVisit = new Visit();
        catVisit.setPet(fionasPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        visitService.save(catVisit);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jennie");
        vet2.setLastName("Hammer");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet1);
        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
