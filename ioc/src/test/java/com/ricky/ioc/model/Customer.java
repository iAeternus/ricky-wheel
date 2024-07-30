package com.ricky.ioc.model;

import com.ricky.ioc.annotation.Printable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Customer
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String name;
    private String email;

    @Printable
    public void printName() {
        System.out.println("Customer name: " + name);
    }

    @Printable
    public void printEmail() {
        System.out.println("Customer email: " + email);
    }

}
