package com.datencontrol.library.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository  extends CrudRepository<Book,Integer> {

    List<Book> findByBookStatusAndUserIdNotAndBookAvailableFalse(BookStatus status, Integer userId);
    List<Book> findByUserIdAndBookAvailableFalse(Integer id);
}
