package ru.luxtington.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.luxtington.library.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String title);
    List<Book> findByTitleAndAuthorSurnameAndYearOfProduce(String title, String authorSurname, int yearOfProduce);
    List<Book> findTopByOrderByYearOfProduceDesc();
}
