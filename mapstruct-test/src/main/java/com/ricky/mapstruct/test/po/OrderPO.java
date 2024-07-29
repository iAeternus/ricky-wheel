package com.ricky.mapstruct.test.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderPO
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderPO extends BasePO {

    private Long id;
    private BigDecimal amount;
    private String currencyCode;

}
