package com.ricky.mapstruct.converter;

import com.ricky.mapstruct.test.aggregate.Order;
import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.po.OrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.json.GsonTester;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderDataConverter
 * @desc
 */
@Mapper(uses = OrderDataConverterDecorator.class)
public interface OrderDataConverter {

    OrderDataConverter INSTANCE = Mappers.getMapper(OrderDataConverter.class);

    @Mappings({
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "amount.amount", target = "amount"),
            @Mapping(source = "amount.currency", target = "currencyCode"),
    })
    OrderPO toPO(Order order);

    @Mappings({
            @Mapping(source = "orderPO.id", target = "id.value"),
            @Mapping(source = "orderPO.amount", target = "amount.amount"),
            @Mapping(source = "orderPO.currencyCode", target = "amount.currency"),
            @Mapping(source = "orderItems", target = "orderItems") // 可以省略
    })
    Order toAggregate(OrderPO orderPO, List<OrderItem> orderItems);

}
