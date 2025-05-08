package ru.luxtington.library.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.luxtington.library.model.Book;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.service.BookService;
import ru.luxtington.library.service.PersonService;
import ru.luxtington.library.utils.BookPartTitleHolder;
import ru.luxtington.library.utils.BookValidator;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lib/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookValidator bookValidator;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, PersonService personService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
    }

    @GetMapping()
    public String bookIndex(Model model){
        model.addAttribute("allBooks", bookService.findAll());
        model.addAttribute("booksQuantity", bookService.findAll().size());
        model.addAttribute("textForBookSearch", new BookPartTitleHolder());
        model.addAttribute("newestBook", bookService.findByNewestBook());
        return "all_books";
    }

    @GetMapping("/{id}")
    public String bookConcrete(@PathVariable("id") String id, Model model){
        Book book = bookService.findById(id).orElse(null);
        model.addAttribute("book",book);

        Person person = book.hasOwner() ? book.getOwner() : new Person();
        model.addAttribute("owner", person);
        List<Person> readers = new ArrayList<>();
        for (Person p : personService.findAll()){
            if (!p.isAdmin() && !p.isLibrarian())
                readers.add(p);
        }
        model.addAttribute("allPersons", readers);
        return "concrete_book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("newBook") Book book){
        return "new_book";
    }

    @PostMapping()
    public String createNewBook(@ModelAttribute("newBook") @Valid Book book,
                                BindingResult bindingResult){

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new_book";
        }
        bookService.save(book);
        return "redirect:/lib/books";
    }

    @GetMapping("/update/{id}")
    public String updateBookForm(@PathVariable("id") String id, Model model){
        model.addAttribute("updatedBook", bookService.findById(id).orElse(null));
        return "update_book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") String id, @ModelAttribute("updatedBook") Book book){
        bookService.save(id, book);
        return "redirect:/lib/books";
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") String id){
        bookService.delete(id);
        return "redirect:/lib/books";
    }

    @PostMapping("/appoint/{id}")
    public String setOwnerToBook(@PathVariable("id") String id,
                                 @ModelAttribute("book") Book book){
        bookService.addOwnerToBook(id, book.getOwner());
        return "redirect:/lib/books/" + id;
    }

    @DeleteMapping("/release/{id}")
    public String releaseBookFromReader(@PathVariable("id") String id){
        bookService.releaseBookFromOwner(id);
        return "redirect:/lib/books/" + id;
    }

    @GetMapping("/search")
    public String findBookByTitle(@ModelAttribute("textForBookSearch") BookPartTitleHolder holder,
                                  Model model){

        List<Book> searchResult = bookService.findByPartOfBookTitle(holder.getPartOfBookTitle());
        model.addAttribute("allBooks", searchResult);
        model.addAttribute("booksQuantity",searchResult.size());
        model.addAttribute("textForBookSearch", new BookPartTitleHolder());

        return "all_books";
    }
}
