package ru.luxtington.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.luxtington.library.model.Role;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
} 