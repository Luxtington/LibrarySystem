package ru.luxtington.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.model.PersonDetails;
import ru.luxtington.library.repository.PersonRepository;

@Service
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        System.out.println("Сомнительный ретурн в новом сервисе деталей персона");
        return new PersonDetails(person);
    }
} 