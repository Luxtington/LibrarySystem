package ru.luxtington.library.dto;

import ru.luxtington.library.model.Person;

import java.util.HashMap;
import java.util.Map;

public class PersonHolderUtils {

    private final static Map<String, Person> PERSONS = new HashMap<>();

    public static void addPerson(Person person){
        PERSONS.put(person.getId(), person);
    }

    public static Person getPerson(String id) {
        return PERSONS.get(id);
    }
}


