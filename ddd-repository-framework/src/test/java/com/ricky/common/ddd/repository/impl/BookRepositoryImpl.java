package com.ricky.common.ddd.repository.impl;

import com.ricky.common.ddd.converter.BookDataConverter;
import com.ricky.common.ddd.mapper.BookImageMapper;
import com.ricky.common.ddd.mapper.BookMapper;
import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.Book;
import com.ricky.common.ddd.model.BookId;
import com.ricky.common.ddd.model.DataBase;
import com.ricky.common.ddd.model.Image;
import com.ricky.common.ddd.model.entity.AggregateDifference;
import com.ricky.common.ddd.model.entity.FieldDifference;
import com.ricky.common.ddd.model.enums.DifferenceType;
import com.ricky.common.ddd.model.po.BookImagePO;
import com.ricky.common.ddd.model.po.BookPO;
import com.ricky.common.ddd.repository.BookRepository;
import com.ricky.common.ddd.support.RepositorySupport;
import com.ricky.common.ddd.utils.CollUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookRepositoryImpl
 * @desc
 */
@Repository
public class BookRepositoryImpl extends RepositorySupport<Book, BookId> implements BookRepository {

    private final BookDataConverter bookDataConverter = BookDataConverter.INSTANCE;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookImageMapper bookImageMapper;

    @Override
    protected Book doSelect(BookId bookId) {
        BookPO bookPO = bookMapper.selectById(bookId.getValue());
        if(bookPO == null) {
            throw new RuntimeException("book不存在, id=" + bookId.getValue());
        }
        return bookDataConverter.convert(bookPO);
    }

    @Override
    @Transactional
    protected void doInsert(Book aggregate) {
        BookPO bookPO = bookDataConverter.convert(aggregate);
        bookMapper.insert(bookPO);

        // handle association object
        List<Image> images = aggregate.getImages();
        List<BookImagePO> bookImagePOS = CollUtils.listConvert(images, image -> bookDataConverter.convert(image, aggregate.getId().getValue()));
        bookImageMapper.insertBatch(bookImagePOS);
    }

    @Override
    protected void doUpdate(Book aggregate, AggregateDifference<Book, BookId> aggregateDifference) {
        if(aggregateDifference.isSelfModified(Book.class)) {
            BookPO bookPO = bookDataConverter.convert(aggregate);
            bookMapper.updateById(bookPO);
        }

        // handle association object
        Map<String, FieldDifference> fieldDifferences = aggregateDifference.getFieldDifferences();
        fieldDifferences.forEach((fieldName, fieldDifference) -> {
            DifferenceType differenceType = fieldDifference.getDifferenceType();

            if(differenceType == DifferenceType.ADDED) {
                List<Image> images = (List<Image>) fieldDifference.getTracValue();
                List<BookImagePO> bookImagePOS = CollUtils.listConvert(images, image -> bookDataConverter.convert(image, aggregate.getId().getValue()));
                bookImageMapper.insertBatch(bookImagePOS);
            } else if(differenceType == DifferenceType.REMOVED) {
                // fieldDifference.getSnapshotValue()
            } else if(differenceType == DifferenceType.MODIFIED) {

            } else {

            }

        });
    }

    @Override
    protected void doDelete(Book aggregate) {
        bookMapper.deleteById(aggregate.getId().getValue());

        // handle association object
        bookImageMapper.deleteByBookId(aggregate.getId().getValue());
    }

}
