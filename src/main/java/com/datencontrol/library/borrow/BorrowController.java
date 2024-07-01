package com.datencontrol.library.borrow;


import com.datencontrol.library.book.Book;
import com.datencontrol.library.book.BookController;
import com.datencontrol.library.book.BookRepository;
import com.datencontrol.library.book.BookStatus;
import com.datencontrol.library.user.UserInfo;
import com.datencontrol.library.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class BorrowController {

    @Autowired
    BorrowRepository borrowRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value="/borrows")
    public ResponseEntity getBorrows(){
       // Borrow borrow = new Borrow();
       // borrow.setAskDate(LocalDate.now());
        List<Borrow> borrows = borrowRepository.findByBorrowerId(BookController.getUserConnectedId());
        return new ResponseEntity(borrows, HttpStatus.OK);
    }

    @PostMapping(value="/borrows/{bookId}")
    public ResponseEntity createBorrow(@PathVariable("bookId") String bookId){

        Integer userConnectedId = BookController.getUserConnectedId();
        Optional<UserInfo> borrower = userRepository.findById(userConnectedId);
        Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));

        if(borrower.isPresent() && book.isPresent() && book.get().getBookStatus().equals(BookStatus.FREE)){
            Borrow borrow = new Borrow();
            Book bookEntity = book.get(); // Refactoring (CMD+OPTION+V)
            borrow.setBook(bookEntity);
            borrow.setBorrower(borrower.get());
            borrow.setLender(bookEntity.getUser()); // owner of this book
            borrow.setAskDate(LocalDate.now());
            borrowRepository.save(borrow);

            // book has already been borrowed. that means, we must move the book from the Lend Booklist
            bookEntity.setBookStatus(BookStatus.BORROWED);
            bookRepository.save(bookEntity);
            return new ResponseEntity(HttpStatus.CREATED);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value="/borrows/{borrowId}")
    public ResponseEntity deleteBorrow(@PathVariable("borrowId") String borrowId){

        Optional<Borrow> borrow = borrowRepository.findById(Integer.valueOf(borrowId));
        if(borrow.isPresent()){

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Borrow borrowEntity = borrow.get();
        // we just do a logical deleting and not physically deleting
        borrowEntity.setCloseDate(LocalDate.now());
        borrowRepository.save(borrowEntity);

        Book book = borrowEntity.getBook();
        book.setBookStatus(BookStatus.FREE);
        bookRepository.save(book);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
