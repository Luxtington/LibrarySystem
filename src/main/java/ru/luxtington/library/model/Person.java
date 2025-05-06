package ru.luxtington.library.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

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

    @NotEmpty(message = "Логин не может быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    private List<Book> allBooks;

    private Set<Role> roles = new HashSet<>();

    public Person() {
    }

    public Person(String surname, String name, String patronymic, int birthdayYear, String email, String username, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdayYear = birthdayYear;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public @NotEmpty(message = "Логин не может быть пустым") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Логин не может быть пустым") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Пароль не может быть пустым") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Пароль не может быть пустым") String password) {
        this.password = password;
    }

    public boolean isAdmin(){
        StringBuilder res = new StringBuilder();
        for (Role role : getRoles()){
            res.append(role.getName());
        }
        System.out.println("ALL ROLES = " + res);
        return res.toString().contains("ROLE_ADMIN");
    }

    public boolean isLibrarian(){
        StringBuilder res = new StringBuilder();
        for (Role role : getRoles()){
            res.append(role.getName());
        }
        System.out.println("ALL ROLES = " + res);
        return res.toString().contains("ROLE_LIBRARIAN");
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
