package com.ricky.ioc.model;

import com.ricky.ioc.annotation.Autowired;
import lombok.Data;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Order
 * @desc
 */
@Data
public class Order {

    private Customer customer;
    private Address address;

    public Order() {
    }

    @Autowired
    public Order(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }

}
