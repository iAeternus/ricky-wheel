package com.ricky.mapstruct.test;

import com.ricky.mapstruct.converter.impl.OrderItemDataConverter;
import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.po.OrderItemPO;
import com.ricky.mapstruct.test.types.OrderItemId;
import org.junit.jupiter.api.Test;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className OrderItemDataConverterTest
 * @desc
 */
public class OrderItemDataConverterTest {

    private final OrderItemDataConverter orderItemDataConverter = OrderItemDataConverter.INSTANCE;

    @Test
    public void toPO() {
        // Given
        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId(1L));
        orderItem.setName("aaa");
        orderItem.setCount(10);

        // When
        OrderItemPO po = orderItemDataConverter.toPO(orderItem, 1L);

        // Then
        System.out.println(po);
    }

    @Test
    public void toEntity() {
        // Given
        OrderItemPO orderItemPO = new OrderItemPO();
        orderItemPO.setId(1L);
        orderItemPO.setOrderId(1L);
        orderItemPO.setName("aaa");
        orderItemPO.setCount(10);

        // When
        OrderItem entity = orderItemDataConverter.toEntity(orderItemPO);

        // Then
        System.out.println(entity);
    }

}
