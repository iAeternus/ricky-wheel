package com.ricky.common.ddd.test;

import com.ricky.common.ddd.converter.BookDataConverter;
import com.ricky.common.ddd.model.Book;
import com.ricky.common.ddd.model.BookId;
import com.ricky.common.ddd.model.Image;
import com.ricky.common.ddd.model.ImageId;
import com.ricky.common.ddd.model.po.BookImagePO;
import com.ricky.common.ddd.model.po.BookPO;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookDataConverterTest
 * @desc
 */
class BookDataConverterTest {

    private final BookDataConverter bookDataConverter = BookDataConverter.INSTANCE;

    @Test
    public void convert1() {
        // Given
        Book book = new Book();
        book.setId(new BookId(1L));
        book.setBookName("百年孤独");
        book.setBookDesc("哥伦比亚作家加西亚·马尔克斯的代表作，讲述了布恩迪亚家族七代人的故事，以及他们所经历的爱情、战争、政治和神秘事件。");
        book.setWords(228015L);
        book.setImages(List.of(
                new Image(new ImageId(1L), "https://bilibili.com"),
                new Image(new ImageId(2L), "https://bilibili.com"),
                new Image(new ImageId(3L), "https://bilibili.com")
        ));

        // When
        BookPO bookPO = bookDataConverter.convert(book);

        // Then
        System.out.println(bookPO);
    }

    @Test
    public void convert2() {
        // Given
        BookPO bookPO = new BookPO();
        bookPO.setId(1L);
        bookPO.setBookName("百年孤独");
        bookPO.setBookDesc("哥伦比亚作家加西亚·马尔克斯的代表作，讲述了布恩迪亚家族七代人的故事，以及他们所经历的爱情、战争、政治和神秘事件。");
        bookPO.setWords(228015L);

        List<BookImagePO> bookImagePOS = List.of(
                new BookImagePO(1L, 1L, "https://bilibili.com"),
                new BookImagePO(2L, 1L, "https://bilibili.com"),
                new BookImagePO(3L, 1L, "https://bilibili.com"),
                new BookImagePO(4L, 1L, "https://bilibili.com")
        );

        // When
        Book book = bookDataConverter.convert(bookPO, bookImagePOS);

        // Then
        System.out.println(book);
    }

    @Test
    public void convert3() {
        // Given
        Image image = new Image(new ImageId(1L), "https://bilibili.com");
        Long bookId = 1L;

        // When
        BookImagePO bookImagePO = bookDataConverter.convert(image, bookId);

        // Then
        System.out.println(bookImagePO);
    }

    @Test
    public void convert4() {
        // Given
        BookImagePO bookImagePO = new BookImagePO(1L, 1L, "https://bilibili.com");

        // When
        Image image = bookDataConverter.convert(bookImagePO);

        // Then
        System.out.println(image);
    }

}