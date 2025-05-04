package ru.luxtington.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.luxtington.library.model.Book;
import ru.luxtington.library.repository.BookRepository;

@Component
public class BookValidator implements Validator {

    private final BookRepository bookRepository;

    @Autowired
    public BookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getYearOfProduce() <= 0){
            errors.rejectValue("yearOfProduce", "", "Дата издания книги" +
                    " не может быть меньше нулевого года");
        }

        if (!bookRepository.findByTitleAndAuthorSurnameAndYearOfProduce(
                book.getTitle(),
                book.getAuthorSurname(),
                book.getYearOfProduce()).isEmpty()){
            errors.rejectValue("title", "", "Более чем у одной книги не может быть одинакового названия, автора и даты написания");
        }

    }
}
