package com.ricky.mapstruct.test;

import com.ricky.mapstruct.assembler.OrderAssembler;
import com.ricky.mapstruct.converter.OrderDataConverter;
import com.ricky.mapstruct.test.aggregate.Order;
import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.po.OrderPO;
import com.ricky.mapstruct.test.types.Money;
import com.ricky.mapstruct.test.types.OrderId;
import com.ricky.mapstruct.test.types.OrderItemId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderDataConverterTest
 * @desc
 */
public class OrderDataConverterTest {

    private final OrderDataConverter orderDataConverter = OrderDataConverter.INSTANCE;

    private static final List<OrderItem> ORDER_ITEMS = new ArrayList<>();

    @BeforeAll
    public static void init() {
        ORDER_ITEMS.add(new OrderItem(new OrderItemId(1L), "aaa", 10));
        ORDER_ITEMS.add(new OrderItem(new OrderItemId(2L), "bbb", 11));
        ORDER_ITEMS.add(new OrderItem(new OrderItemId(3L), "ccc", 12));
        ORDER_ITEMS.add(new OrderItem(new OrderItemId(4L), "ddd", 13));
        ORDER_ITEMS.add(new OrderItem(new OrderItemId(5L), "eee", 14));
    }

    @Test
    public void toPO() {
        // Given
        Order order = new Order();
        order.setId(new OrderId(1L));
        order.setAmount(Money.ONE_HUNDRED_YUAN);
        order.setOrderItems(ORDER_ITEMS);

        // When
        OrderPO po = orderDataConverter.toPO(order);

        // Then
        System.out.println(po);
    }

    @Test
    public void toAggregate() {
        // Given
        OrderPO orderPO = new OrderPO();
        orderPO.setId(1L);
        orderPO.setAmount(BigDecimal.valueOf(100));
        orderPO.setCurrencyCode("CNY");
        LocalDateTime now = LocalDateTime.now();
        orderPO.setCreateTime(now);
        orderPO.setUpdateTime(now);

        // When
        Order aggregate = orderDataConverter.toAggregate(orderPO, ORDER_ITEMS);

        // Then
        System.out.println(aggregate);
    }

}
