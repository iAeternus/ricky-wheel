package com.ricky.common.ddd.converter;

import com.ricky.common.ddd.model.Book;
import com.ricky.common.ddd.model.Image;
import com.ricky.common.ddd.model.po.BookImagePO;
import com.ricky.common.ddd.model.po.BookPO;
import com.ricky.common.ddd.utils.CollUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookDataConverter
 * @desc
 */
@Mapper
public abstract class BookDataConverter {

    public static final BookDataConverter INSTANCE = Mappers.getMapper(BookDataConverter.class);

    @Mappings({
            @Mapping(source = "id.value", target = "id"),
    })
    public abstract BookPO convert(Book book);

    @Mappings({
            @Mapping(source = "id", target = "id.value"),
    })
    public abstract Book convert(BookPO bookPO);

    public Book convert(BookPO bookPO, List<BookImagePO> bookImagePOS) {
        Book book = convert(bookPO);
        book.setImages(CollUtils.listConvert(bookImagePOS, this::convert));
        return book;
    }

    @Mappings({
            @Mapping(source = "image.id.value", target = "id"),
    })
    public abstract BookImagePO convert(Image image, Long bookId);

    @Mappings({
            @Mapping(source = "id", target = "id.value"),
    })
    public abstract Image convert(BookImagePO bookImagePO);

}
