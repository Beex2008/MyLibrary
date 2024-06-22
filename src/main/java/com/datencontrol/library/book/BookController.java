package com.datencontrol.library.book;

import com.datencontrol.library.borrow.Borrow;
import com.datencontrol.library.borrow.BorrowRepository;
import com.datencontrol.library.user.User;
import com.datencontrol.library.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    // getUserConnectedId should be accessible everywhere in program
    public static Integer getUserConnectedId(){
        return 1;
    }
    @GetMapping(value = "/books")
    public ResponseEntity listBooks(@RequestParam(required = false) BookStatus status){
        //Book book = new Book();
        //book.setTitle("MyBook");
        //book.setCategorie(new Category("Animate"));
        Integer userConectedId = this.getUserConnectedId();
        List<Book> books;

        if(status != null  && status == BookStatus.FREE)
            // for free books
            books = bookRepository.findBySatusAndUserIdNotAndDeleteFalse(status, userConectedId);
        else
            // for book
            books = bookRepository.findByUserIdAndDeletedFalse(userConectedId);

        return new ResponseEntity(Arrays.asList(books), HttpStatus.OK);
    }


    @PostMapping(value = "/books")
    public ResponseEntity addBook(@RequestBody @Valid Book book){

        Integer userConectedId = this.getUserConnectedId();
        Optional<User> user = userRepository.findById(userConectedId);
        Optional<Category> category = categoryRepository.findById(book.getCategoryId());

        if(category.isPresent())
            book.setCategorie(category.get());
        else
            return new ResponseEntity("You must provide a valid category", HttpStatus.BAD_REQUEST);

        if(user.isPresent())
            book.setUser(user.get());
        else
            return new ResponseEntity("You must provide a valid user", HttpStatus.BAD_REQUEST);

        book.setBookAvailable(false); // check if the book deleted
        book.setBookStatus(BookStatus.FREE);
        bookRepository.save(book);

        return new ResponseEntity(book, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/books/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") String bookId){

        Optional<Book> bookDeleted = bookRepository.findById(Integer.valueOf(bookId));
        if(!bookDeleted.isPresent()){
            return new ResponseEntity("Book not found", HttpStatus.BAD_REQUEST);
        }
        Book book = bookDeleted.get();
        List<Borrow> borrows = borrowRepository.findByBookId(book.getId());

        for(Borrow borrow: borrows){
            if(borrow.getCloseDate() == null){
                User borrower = borrow.getBorrower();
                return new ResponseEntity(borrower, HttpStatus.CONFLICT);
            }
        }
        book.setBookAvailable(true);
        bookRepository.save(book);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId") String bookId, @RequestBody @Valid Book book){

        Optional<Book> bookUpdate = bookRepository.findById(Integer.valueOf(bookId));
        if(!bookUpdate.isPresent()){
            return new ResponseEntity("Book not existing",HttpStatus.BAD_REQUEST);
        }

        // call the category using @Transcient cotegoryId
        Book bookTosave = bookUpdate.get();
        Optional<Category> newCategory = categoryRepository.findById(book.getCategoryId());
        bookTosave.setCategorie(newCategory.get());
        bookTosave.setTitle(book.getTitle());
        bookRepository.save(bookTosave); // Id will be showed here fron DB

        return new ResponseEntity(bookTosave, HttpStatus.OK);
    }

    @GetMapping(value="/categories")
    public ResponseEntity getCategory(){
        Category category = new Category("BD");
        Category categoryRoman = new Category("Roman");
        Category categoryAction = new Category("Action");
        Category categoryEconomic = new Category("Economic");

        return new ResponseEntity(Arrays.asList(category, categoryAction, categoryRoman, categoryEconomic),HttpStatus.OK);
    }


    @GetMapping("/books/{bookId}")
    public ResponseEntity loadBook(@PathVariable("bookId") String bookId){

    Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));
    // due of Optional fonction it's necessary to prouf if book exists
    if(!book.isPresent())
        return new ResponseEntity("Book no found", HttpStatus.BAD_REQUEST);

    return new ResponseEntity(book.get(), HttpStatus.OK);
    }
}
