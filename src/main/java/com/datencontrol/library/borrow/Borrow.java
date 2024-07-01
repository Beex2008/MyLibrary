package com.datencontrol.library.borrow;

import com.datencontrol.library.book.Book;
import com.datencontrol.library.user.UserInfo;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserInfo borrower;

    @ManyToOne
    private UserInfo lender;

    @ManyToOne
    private Book book;

    private LocalDate askDate;
    private LocalDate closeDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getAskDate() {
        return askDate;
    }

    public void setAskDate(LocalDate askDate) {
        this.askDate = askDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public UserInfo getBorrower() {
        return borrower;
    }

    public void setBorrower(UserInfo borrower) {
        this.borrower = borrower;
    }

    public UserInfo getLender() {
        return lender;
    }

    public void setLender(UserInfo lender) {
        this.lender = lender;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
