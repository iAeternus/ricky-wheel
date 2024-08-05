package com.ricky.common.ddd.test;

import com.ricky.common.ddd.model.Book;
import com.ricky.common.ddd.model.BookId;
import com.ricky.common.ddd.model.ImageId;
import com.ricky.common.ddd.repository.BookRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className RepositorySupportTest
 * @desc
 */
@SpringBootTest
class RepositorySupportTest {

    @Resource
    private BookRepository bookRepository;

    @Test
    public void multiInsert() {
        // Given
        Book book = new Book();
        book.setId(new BookId(1L));
        book.setBookName("百年孤独");
        book.setBookDesc("哥伦比亚作家加西亚·马尔克斯的代表作，讲述了布恩迪亚家族七代人的故事，以及他们所经历的爱情、战争、政治和神秘事件。");
        book.setWords(228015L);
        book.addImage(new ImageId(1L), "https://bilibili.com");
        book.addImage(new ImageId(2L), "https://bilibili.com");

        // When
        System.out.println("第一次保存前");
        System.out.println(book);

        bookRepository.save(book);
        System.out.println("第一次保存后");
        System.out.println(book);

        book.getImages().get(0).setImageUrl("https://baidu.com");
        book.setBookName("百年孤独译本");
        bookRepository.save(book);

        System.out.println("第二次保存后");
        System.out.println(book);
    }

}