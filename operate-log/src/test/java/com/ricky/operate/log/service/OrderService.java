package com.ricky.operate.log.service;

import com.ricky.operate.log.annotation.RecordOperate;
import com.ricky.operate.log.converter.impl.SaveOrderConverter;
import com.ricky.operate.log.converter.impl.UpdateOrderConverter;
import com.ricky.operate.log.model.SaveOrder;
import com.ricky.operate.log.model.UpdateOrder;
import org.springframework.stereotype.Service;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderService
 * @desc
 */
@Service
public class OrderService {

    @RecordOperate(desc = "保存订单", converter = SaveOrderConverter.class)
    public Boolean saveOrder(SaveOrder saveOrder) {
        System.out.println("save order , orderId : " + saveOrder.getId());
        return true;
    }

    @RecordOperate(desc = "更新订单", converter = UpdateOrderConverter.class)
    public Boolean updateOrder(UpdateOrder updateOrder) {
        System.out.println("update order , orderId : " + updateOrder.getOrderId());
        return true;
    }

}
