package com.ricky.mapstruct.test.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderItemPO
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItemPO extends BasePO {

    private Long id;
    private Long orderId;
    private String name;
    private Integer count;

}
