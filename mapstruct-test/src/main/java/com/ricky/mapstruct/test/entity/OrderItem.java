package com.ricky.mapstruct.test.entity;

import com.ricky.mapstruct.test.marker.Entity;
import com.ricky.mapstruct.test.types.OrderItemId;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderItem
 * @desc
 */
@Data
@AllArgsConstructor
public class OrderItem implements Entity<OrderItemId> {

    private OrderItemId id;
    private String name;
    private Integer count;

}
