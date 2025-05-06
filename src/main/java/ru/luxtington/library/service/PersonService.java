package ru.luxtington.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.model.Role;
import ru.luxtington.library.repository.PersonRepository;
import ru.luxtington.library.repository.RoleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void save(String id, Person person){
        person.setId(id);
        personRepository.save(person);
    }

    public Optional<Person> findById(String id){
        return personRepository.findById(id);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Transactional
    public void delete(String id){
        personRepository.deleteById(id);
    }

    public List<Person> findByInitials(String part){
        List<Person> resultSearch = new ArrayList<>();
        List<Person> allPersons = findAll();
        for (Person pers : allPersons){
            if (pers.getInitials().toLowerCase().contains(part.toLowerCase())){
                resultSearch.add(pers);
            }
        }
        return resultSearch;
    }

    @Transactional
    public void assignRoleToPerson(String personId, Role role) {
        Person person = personRepository.findById(personId).orElse(null);
        person.addRole(role);
        personRepository.save(person);
//        Person person = findById(personId)
//                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
//
//        Role newRole = roleRepository.findByName("ROLE_" + roleName.toUpperCase())
//                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
//
//        Set<Role> roles = new HashSet<>();
//        roles.add(newRole);
//        person.setRoles(roles);
//
//        personRepository.save(person);
    }
}
