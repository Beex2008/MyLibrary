package com.datencontrol.library.book;

import com.datencontrol.library.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @ManyToOne
    private Category categorie;

    @ManyToOne
    private User user;

    private BookStatus bookStatus;
    private boolean bookAvailable; // deleting wont be on the real DB, rather than logical delete

    @Transient
    private int categoryId;


    public int getId() {
        return id;
    }

    public boolean isBookAvailable() {
        return bookAvailable;
    }

    public void setBookAvailable(boolean bookAvailable) {
        this.bookAvailable = bookAvailable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategorie() {
        return categorie;
    }

    public void setCategorie(Category categorie) {
        this.categorie = categorie;
    }
}
