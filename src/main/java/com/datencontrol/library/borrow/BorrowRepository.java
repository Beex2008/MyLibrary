package com.datencontrol.library.borrow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Integer> {

    List<Borrow> findByBorrowId(Integer borrowerId);
    List<Borrow> findByBookId(Integer bookId); // search in the book entity by Id, if the element deleted

}
