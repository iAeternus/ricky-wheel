package com.ricky.test.mapper;

import com.ricky.test.model.po.BookPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookMapper
 * @desc
 */
@Mapper
public interface BookMapper {

    @Select("select id, book_name, book_desc, words from book where id = #{id}")
    BookPO selectById(Long id);

    Long insert(BookPO bookPO);

    @Delete("delete from book where id = #{id}")
    void deleteById(Long id);

    void updateById(BookPO bookPO);

}
