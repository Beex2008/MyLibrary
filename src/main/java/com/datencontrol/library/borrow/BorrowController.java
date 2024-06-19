package com.datencontrol.library.borrow;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class BorrowController {

    @GetMapping(value="/borrows")
    public ResponseEntity getBorrows(){
        Borrow borrow = new Borrow();
        borrow.setAskDate(LocalDate.now());
        return new ResponseEntity(borrow, HttpStatus.OK);
    }

    @PostMapping(value="/borrows/{bookId}")
    public ResponseEntity createBorrow(@PathVariable("bookId") String bookId){
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/borrows/{borrowId}")
    public ResponseEntity deleteBorrow(@PathVariable("borrowId") String borrowId){
        return new ResponseEntity(HttpStatus.OK);
    }
}
