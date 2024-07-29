package com.ricky.mapstruct.converter;

import java.util.Currency;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderDataConverterDecorator
 * @desc
 */
public class OrderDataConverterDecorator {

    public Currency toCurrency(String currencyCode) {
        return Currency.getInstance(currencyCode);
    }

    public String toCurrencyCode(Currency currency) {
        return currency.getCurrencyCode();
    }

}
