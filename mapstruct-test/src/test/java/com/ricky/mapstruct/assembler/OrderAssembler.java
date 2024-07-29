package com.ricky.mapstruct.assembler;

import com.ricky.mapstruct.test.aggregate.Order;
import com.ricky.mapstruct.test.dto.AddOrderCommand;
import com.ricky.mapstruct.test.dto.GetByIdResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderAssembler
 * @desc
 */
@Mapper/*(componentModel = "spring")*/
public interface OrderAssembler {

    OrderAssembler INSTANCE = Mappers.getMapper(OrderAssembler.class);

    @Mappings({
            @Mapping(source = "id", target = "id.value"),
    })
    Order toAggregate(AddOrderCommand command);

    @Mappings({
            @Mapping(source = "id.value", target = "id"),
    })
    GetByIdResponse toResponse(Order order);

}
