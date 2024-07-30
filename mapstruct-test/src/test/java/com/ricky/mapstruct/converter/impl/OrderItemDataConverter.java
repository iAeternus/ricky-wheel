package com.ricky.mapstruct.converter.impl;

import com.ricky.mapstruct.converter.DataConverter;
import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.po.OrderItemPO;
import com.ricky.mapstruct.test.types.OrderItemId;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className OrderItemDataConverter
 * @desc
 */
@Mapper
public abstract class OrderItemDataConverter implements DataConverter<OrderItem, OrderItemId, OrderItemPO> {

    public static final OrderItemDataConverter INSTANCE = Mappers.getMapper(OrderItemDataConverter.class);

    @Override
    @Mappings({
            @Mapping(source = "id.value", target = "id"),
    })
    public abstract OrderItemPO toPO(@NonNull OrderItem entity);

    public OrderItemPO toPO(OrderItem entity, Long orderId) {
        OrderItemPO po = toPO(entity);
        po.setOrderId(orderId);
        return po;
    }

    @Override
    @Mappings({
            @Mapping(source = "id", target = "id.value"),
    })
    public abstract OrderItem toEntity(@NonNull OrderItemPO po);

}
