package com.datencontrol.library.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class BookController {

    @GetMapping(value = "/books")
    public ResponseEntity listBooks(){
        Book book = new Book();

        book.setTitle("MyBook");
        book.setCategorie(new Category("Animate"));

        return new ResponseEntity(Arrays.asList(book), HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{bookId}")
    public ResponseEntity addBook(@PathVariable("bookId") String bookId){
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/books/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") String bookId){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId") String bookId, Book book){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value="/categories")
    public ResponseEntity getCategory(){
        Category category = new Category("BD");
        Category categoryRoman = new Category("Roman");
        Category categoryAction = new Category("Action");
        Category categoryEconomic = new Category("Economic");

        return new ResponseEntity(Arrays.asList(category, categoryAction, categoryRoman, categoryEconomic),HttpStatus.OK);
    }
}
