package ru.luxtington.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.model.Role;
import ru.luxtington.library.service.PersonService;
import ru.luxtington.library.utils.PersonInitialsHolder;
import ru.luxtington.library.utils.PersonValidator;

import java.time.Year;
import java.util.List;

@Controller
@RequestMapping("/lib/persons")
public class PersonController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String personIndex(Model model){
        model.addAttribute("allPersons", personService.findAll());
        model.addAttribute("personQuantity", personService.findAll().size());
        model.addAttribute("textForPersonSearch", new PersonInitialsHolder());
        model.addAttribute("oldestPerson", personService.findByOldestAge());
        return "all_persons";
    }

    @GetMapping("/{id}")
    public String personConcrete(@PathVariable("id") String id, Model model) {
        model.addAttribute("person", personService.findById(id).orElse(null));
        model.addAttribute("currYear", Year.now().getValue());
        return "concrete_person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("newPerson") Person person){
        return "new_person";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person,
                                  BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "new_person";
        }
        personService.save(person);
        return "redirect:/lib/persons";
    }

    @GetMapping("/update/{id}")
    public String updatePersonForm(@PathVariable("id") String id, Model model){
        Person person = personService.findById(id).orElse(null);
        model.addAttribute("updatedPerson", person);
        return "update_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") String id, @ModelAttribute("updatedPerson") Person person){
        Person copyPerson = personService.findById(id).orElse(null);
        person.setRoles(copyPerson.getRoles());
        person.setUsername(copyPerson.getUsername());
        person.setPassword(copyPerson.getPassword());
        personService.save(id, person);
        return "redirect:/lib/persons";
    }


    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") String id){
        personService.delete(id);
        return "redirect:/lib/persons";
    }

    @GetMapping("/search")
    public String findBookByTitle(@ModelAttribute("textForPersonSearch") PersonInitialsHolder holder,
                                  Model model){

        List<Person> searchResult = personService.findByInitials(holder.getInitialsPart());
        model.addAttribute("allPersons", searchResult);
        model.addAttribute("personQuantity",searchResult.size());
        model.addAttribute("textForPersonSearch", new PersonInitialsHolder());

        return "all_persons";
    }

    @PostMapping("/change-role/{id}")
    public String changeRole(@PathVariable("id") String id) {
        Role role = new Role("ROLE_LIBRARIAN");
        personService.assignRoleToPerson(id, role);
        return "redirect:/lib/persons/" + id;
    }
}
