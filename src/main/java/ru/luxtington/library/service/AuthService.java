package ru.luxtington.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.model.PersonDetails;
import ru.luxtington.library.model.Role;
import ru.luxtington.library.repository.PersonRepository;
import ru.luxtington.library.repository.RoleRepository;
import ru.luxtington.library.security.JwtUtil;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken((PersonDetails) authentication.getPrincipal());
    }

    public void register(Person person) {
        if (personRepository.findByUsername(person.getUsername()) != null) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        
        Role readerRole = roleRepository.findByName("ROLE_READER")
                .orElseThrow(() -> new RuntimeException("Роль READER не найдена"));
        
        Set<Role> roles = new HashSet<>();
        roles.add(readerRole);
        person.setRoles(roles);

        personRepository.save(person);
    }
} 