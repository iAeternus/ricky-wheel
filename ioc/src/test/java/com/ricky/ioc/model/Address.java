package com.ricky.ioc.model;

import com.ricky.ioc.annotation.Printable;
import lombok.Value;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Address
 * @desc
 */
@Value
public class Address {

    String street;
    String postCode;

    @Printable
    public void printStreet() {
        System.out.println("Address street: " + street);
    }

    @Printable
    public void printPostCode() {
        System.out.println("Address postcode: " + postCode);
    }

}
