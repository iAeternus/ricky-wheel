package com.ricky.test.mapper;

import com.ricky.test.model.po.BookImagePO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookImageMapper
 * @desc
 */
@Mapper
public interface BookImageMapper {
    void insertBatch(List<BookImagePO> bookImagePOS);

    @Delete("delete from book_image where book_id = #{bookId}")
    void deleteByBookId(Long bookId);

    void insert(BookImagePO bookImagePO);
}
