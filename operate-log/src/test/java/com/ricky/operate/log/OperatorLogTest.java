package com.ricky.operate.log;

import com.ricky.operate.log.model.SaveOrder;
import com.ricky.operate.log.model.UpdateOrder;
import com.ricky.operate.log.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OperatorLogTest
 * @desc
 */
@SpringBootApplication
public class OperatorLogTest implements CommandLineRunner {

    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(OperatorLogTest.class, args);
    }

    @Override
    public void run(String... args) {
        // Given
        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(1L);

        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);

        // When
        orderService.saveOrder(saveOrder);
        orderService.updateOrder(updateOrder);
    }

}
