package spring.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import spring.petclinic.model.Owner;
import spring.petclinic.model.Pet;
import spring.petclinic.model.Visit;
import spring.petclinic.services.PetService;
import spring.petclinic.services.VisitService;

import java.util.Map;

@Controller
public class VisitController {

    private static final String VIEWS_VISITS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable Long petId, Map<String, Object> model) {
        return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(Visit visit, BindingResult result, Owner owner) {
        if (result.hasErrors()) {
            return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
