package com.ricky.mapstruct.converter.impl;

import com.ricky.mapstruct.converter.DataConverter;
import com.ricky.mapstruct.converter.decorator.OrderDataConverterDecorator;
import com.ricky.mapstruct.test.aggregate.Order;
import com.ricky.mapstruct.test.po.OrderItemPO;
import com.ricky.mapstruct.test.po.OrderPO;
import com.ricky.mapstruct.test.types.OrderId;
import com.ricky.mapstruct.test.utils.CollUtils;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderDataConverter
 * @desc
 */
@Mapper(uses = OrderDataConverterDecorator.class)
public abstract class OrderDataConverter implements DataConverter<Order, OrderId, OrderPO> {

    public static final OrderDataConverter INSTANCE = Mappers.getMapper(OrderDataConverter.class);

    // 可以改成注入
    private final OrderItemDataConverter orderItemDataConverter = OrderItemDataConverter.INSTANCE;

    @Override
    @Mappings({
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "amount.amount", target = "amount"),
            @Mapping(source = "amount.currency", target = "currencyCode"),
    })
    public abstract OrderPO toPO(@NonNull Order entity);

    @Override
    @Mappings({
            @Mapping(source = "id", target = "id.value"),
            @Mapping(source = "amount", target = "amount.amount"),
            @Mapping(source = "currencyCode", target = "amount.currency"),
    })
    public abstract Order toEntity(@NonNull OrderPO po);


    public Order toEntity(OrderPO orderPO, List<OrderItemPO> orderItemPOS) {
        Order entity = toEntity(orderPO);
        entity.setOrderItems(CollUtils.listConvert(orderItemPOS, orderItemDataConverter::toEntity));
        return entity;
    }

}
