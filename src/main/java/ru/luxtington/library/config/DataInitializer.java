package ru.luxtington.library.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.model.Role;
import ru.luxtington.library.repository.PersonRepository;
import ru.luxtington.library.repository.RoleRepository;

import java.util.Collection;
import java.util.HashSet;

@Component
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            log.info("admin role was uploaded");
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("ROLE_LIBRARIAN").isEmpty()) {
            log.info("librarian role was uploaded");
            Role librarianRole = new Role();
            librarianRole.setName("ROLE_LIBRARIAN");
            roleRepository.save(librarianRole);
        }

        if (roleRepository.findByName("ROLE_READER").isEmpty()) {
            log.info("reader role was uploaded");
            Role readerRole = new Role();
            readerRole.setName("ROLE_READER");
            roleRepository.save(readerRole);
        }

        if (personRepository.findByUsername("admin1") == null){
            log.info("admin1 was inserted into DB");
            Person admin = new Person("admin1", "admin1", "admin1", 2025, "cats@mail.ru", "admin1", passwordEncoder.encode("admin1"));
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            admin.addRole(adminRole);
            personRepository.save(admin);
        }

    }
} 