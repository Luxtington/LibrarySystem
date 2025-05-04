package ru.luxtington.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luxtington.library.model.Book;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.repository.BookRepository;
import ru.luxtington.library.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void save(String id, Book book){
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(String id){
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById(String id){
        return bookRepository.findById(id);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Transactional
    public void addOwnerToBook(String bookId, Person owner){
        Book bookWithOwner = findById(bookId).orElse(null);
        if (owner == null){ // такое может случиться если читателей нет, сетнули пустоту
            return;
        }
        owner.addBook(bookWithOwner);
        bookRepository.save(bookWithOwner);
        personRepository.save(owner);
    }

    @Transactional
    public void releaseBookFromOwner(String bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        Person owner = book.getOwner();
        book.getOwner().removeBookById(bookId);
        book.setOwner(null);
        bookRepository.save(book);
        personRepository.save(owner);
    }

    public List<Book> findByPartOfBookTitle(String part){
        List<Book> resultSearch = new ArrayList<>();
        List<Book> allBooks = findAll();
        for (Book book : allBooks){
            if (book.getFullBookDescription().toLowerCase().contains(part.toLowerCase())){
                resultSearch.add(book);
            }
        }
        return resultSearch;
    }
}
