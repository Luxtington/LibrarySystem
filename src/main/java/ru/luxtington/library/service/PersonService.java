package ru.luxtington.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luxtington.library.model.Book;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
}
