package com.ricky.ioc.config;

import com.ricky.ioc.annotation.Bean;
import com.ricky.ioc.model.Address;
import com.ricky.ioc.model.Customer;
import com.ricky.ioc.model.Message;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Config
 * @desc
 */
public class Config {

    @Bean
    public Customer customer() {
        return new Customer("Ricky", "1049469060@qq.com");
    }

    @Bean
    public Address address() {
        return new Address("345 Spear Street", "94105");
    }

    public Message message() {
        return new Message("Hi there!");
    }

}
