package com.ricky.mapstruct.test.dto;

import com.ricky.mapstruct.test.entity.OrderItem;
import com.ricky.mapstruct.test.marker.Response;
import com.ricky.mapstruct.test.types.Money;
import lombok.Data;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className GetByIdResponse
 * @desc
 */
@Data
public class GetByIdResponse implements Response {

    private Long id;
    private Money amount;
    private List<OrderItem> orderItems;

}
