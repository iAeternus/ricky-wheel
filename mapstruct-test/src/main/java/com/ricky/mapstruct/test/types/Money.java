package com.ricky.mapstruct.test.types;

import com.ricky.mapstruct.test.marker.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className Money
 * @desc
 */
@Value
@AllArgsConstructor
public class Money implements ValueObject {

    BigDecimal amount;
    Currency currency;

    public static final Money ONE_HUNDRED_YUAN = new Money(BigDecimal.valueOf(1000), Currency.getInstance("CNY"));

}
