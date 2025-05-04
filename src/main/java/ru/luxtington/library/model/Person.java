package ru.luxtington.library.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "person")
public class Person {
    @Id
    private String id;

    @Size(min = 2, max = 30, message = "Некорректная длина фамилии")
    @NotEmpty(message = "Фамилия не может быть пустой")
    private String surname;

    @Size(min = 2, max = 15, message = "Некорректная длина имени")
    @NotEmpty(message = "Имя не может быть пустым")
    private String name;

    @Size(min = 6, max = 20, message = "Некорректная длина отчества")
    private String patronymic;

    @Range(min = 1900, max = 2020, message = "Некорректная дата рождения, должна быть от 1900 до 2020 года")
    private int birthdayYear;

    @Email(message = "Некорректное написание адреса электронной почты")
    private String email;

    private List<Book> allBooks;

    public Person() {
    }

    public Person(String id, String surname, String name, String patronymic, int birthdayYear, String email) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdayYear = birthdayYear;
        this.email = email;
    }

    public void addBook(@NotNull Book book){
        if (allBooks == null){
            allBooks = new ArrayList<>();
        }
        allBooks.add(book);
        book.setOwner(this); //уже делается в thymleaf ???
    }

    public void removeBookById(String id){
        if (allBooks == null){
            return;
        }
        allBooks.size();
        Book neededBook = allBooks.stream()
                                  .filter(book -> book.getId().equals(id))
                                  .findFirst()
                                  .orElse(null);
        if (neededBook != null){
            allBooks.remove(neededBook);
            neededBook.setOwner(null);
        }
    }

    public String getInitials(){
        return String.format("%s %s %s", surname, name, patronymic);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @Size(min = 2, max = 30, message = "Некорректная длина фамилии") @NotEmpty(message = "Фамилия не может быть пустой") String getSurname() {
        return surname;
    }

    public void setSurname(@Size(min = 2, max = 30, message = "Некорректная длина фамилии") @NotEmpty(message = "Фамилия не может быть пустой") String surname) {
        this.surname = surname;
    }

    public @Size(min = 2, max = 15, message = "Некорректная длина имени") @NotEmpty(message = "Имя не может быть пустым") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 15, message = "Некорректная длина имени") @NotEmpty(message = "Имя не может быть пустым") String name) {
        this.name = name;
    }

    public @Size(min = 6, max = 20, message = "Некорректная длина отчества") String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(@Size(min = 6, max = 20, message = "Некорректная длина отчества") String patronymic) {
        this.patronymic = patronymic;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public @Email(message = "Некорректное написание адреса электронной почты") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Некорректное написание адреса электронной почты") String email) {
        this.email = email;
    }

    public List<Book> getAllBooks() {
        if (allBooks == null){
            return new ArrayList<>();
        }
        return new ArrayList<>(allBooks);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthdayYear=" + birthdayYear +
                ", email='" + email + '\'' +
                ", allBooks=" + allBooks +
                '}';
    }
}
