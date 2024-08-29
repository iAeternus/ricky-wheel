package com.ricky.test.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ricky.repositoryframework.persistence.po.BasePO;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className BookImagePO
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookImagePO extends BasePO {

    private Long id;

    private Long bookId;

    private String imageUrl;

}
