package ru.luxtington.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.repository.PersonRepository;

@Component
public class PersonValidator implements Validator {
    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (!personRepository.findByEmail(person.getEmail()).isEmpty()){
            errors.rejectValue("email", "", "Читатель с таким адресом" +
                    " электронной почты уже зарегистрирован.");
        }


        if (person.getBirthdayYear() <= 1900){
            errors.rejectValue("birthdayYear", "", "Год рождения читателя" +
                    " не может быть меньше, чем 1900");
        }

    }
}
