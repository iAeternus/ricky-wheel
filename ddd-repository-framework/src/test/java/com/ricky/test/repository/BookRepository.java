package com.ricky.test.repository;


import com.ricky.test.model.Book;
import com.ricky.test.model.BookId;
import org.ricky.repositoryframework.repository.IRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookRepository
 * @desc
 */
@Repository
public interface BookRepository extends IRepository<Book, BookId> {
}
