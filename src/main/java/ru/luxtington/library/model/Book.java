package ru.luxtington.library.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.luxtington.library.dto.PersonHolderUtils;

@Document(collection= "book")
public class Book {
    @Id
    private String id;

    @Size(min = 2, max = 30, message = "Некорректная длина названия книги.")
    @NotEmpty(message = "Название книги не может быть пустым")
    private String title;

    @Size(min = 2, max = 30, message = "Некорректная длина фамилии")
    @NotEmpty(message = "Фамилия автора не может быть пустой")
    private String authorSurname;

    @Range(min = 1, max = 2025, message = "Некорректная дата издания, должна быть от 1 до 2025 года")
    private int yearOfProduce;

    private boolean hasOwner;

    private String ownerID;

    public Book() {
    }

    public Book(String id, String title, String authorSurname, int yearOfProduce) {
        this.id = id;
        this.title = title;
        this.authorSurname = authorSurname;
        this.yearOfProduce = yearOfProduce;
    }

    public String getFullBookDescription(){
        return String.format("%s %s %d", title, authorSurname, yearOfProduce);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @Size(min = 2, max = 30, message = "Некорректная длина названия книги.") @NotEmpty(message = "Название книги не может быть пустым") String getTitle() {
        return title;
    }

    public void setTitle(@Size(min = 2, max = 30, message = "Некорректная длина названия книги.") @NotEmpty(message = "Название книги не может быть пустым") String title) {
        this.title = title;
    }

    public @Size(min = 2, max = 30, message = "Некорректная длина фамилии") @NotEmpty(message = "Фамилия автора не может быть пустой") String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(@Size(min = 2, max = 30, message = "Некорректная длина фамилии") @NotEmpty(message = "Фамилия автора не может быть пустой") String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public int getYearOfProduce() {
        return yearOfProduce;
    }

    public void setYearOfProduce(int yearOfProduce) {
        this.yearOfProduce = yearOfProduce;
    }

    public boolean hasOwner() {
        return hasOwner;
    }

    public void setBookStatus(boolean bookStatus) {
        this.hasOwner = bookStatus;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public Person getOwner() {
        if (!hasOwner()){
            return null;
        }
        return PersonHolderUtils.getPerson(ownerID);
    }

    public void setOwner(Person owner) {
        if (owner == null){
            hasOwner = false;
            ownerID = null;
        } else {
            hasOwner = true;
            ownerID = owner.getId();
            PersonHolderUtils.addPerson(owner);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authorSurname='" + authorSurname + '\'' +
                ", yearOfProduce=" + yearOfProduce +
                ", hasOwner=" + hasOwner +
                '}';
    }
}
