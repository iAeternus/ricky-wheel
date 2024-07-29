package com.ricky.mapstruct.test.aggregate;

import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.marker.Aggregate;
import com.ricky.mapstruct.test.types.Money;
import com.ricky.mapstruct.test.types.OrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className Order
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Aggregate<OrderId> {

    private OrderId id;
    private Money amount;
    private List<OrderItem> orderItems;

}
