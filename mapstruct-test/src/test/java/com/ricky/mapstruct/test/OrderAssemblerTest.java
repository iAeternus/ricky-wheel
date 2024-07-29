package com.ricky.mapstruct.test;

import com.ricky.mapstruct.assembler.OrderAssembler;
import com.ricky.mapstruct.test.aggregate.Order;
import com.ricky.mapstruct.test.dto.AddOrderCommand;
import com.ricky.mapstruct.test.dto.GetByIdResponse;
import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.types.Money;
import com.ricky.mapstruct.test.types.OrderId;
import com.ricky.mapstruct.test.types.OrderItemId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderAssemblerTest
 * @desc
 */
// @SpringBootTest
public class OrderAssemblerTest {

    // @Autowired
    // private OrderAssembler orderAssembler;

    private final OrderAssembler orderAssembler = OrderAssembler.INSTANCE;

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
    public void toAggregate() {
        // Given
        AddOrderCommand command = new AddOrderCommand();
        command.setId(1L);
        command.setAmount(Money.ONE_HUNDRED_YUAN);
        command.setOrderItems(ORDER_ITEMS);

        // When
        Order aggregate = orderAssembler.toAggregate(command);

        // Then
        System.out.println(aggregate);
    }

    @Test
    public void toResponse() {
        // Given
        Order order = new Order();
        order.setId(new OrderId(1L));
        order.setAmount(Money.ONE_HUNDRED_YUAN);
        order.setOrderItems(ORDER_ITEMS);

        // When
        GetByIdResponse response = orderAssembler.toResponse(order);

        // Then
        System.out.println(response);
    }

}
