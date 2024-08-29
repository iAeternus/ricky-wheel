package com.ricky.test.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ricky.repositoryframework.persistence.po.BasePO;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookPO
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BookPO extends BasePO {

    private Long id;

    private String bookName;

    private String bookDesc;

    private Long words;

}
