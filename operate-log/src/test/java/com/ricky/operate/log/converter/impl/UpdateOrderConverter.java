package com.ricky.operate.log.converter.impl;

import com.ricky.operate.log.converter.Converter;
import com.ricky.operate.log.model.OperateLogDO;
import com.ricky.operate.log.model.UpdateOrder;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className UpdateOrderConverter
 * @desc
 */
public class UpdateOrderConverter implements Converter<UpdateOrder> {
    @Override
    public OperateLogDO convert(UpdateOrder updateOrder) {
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOperateId(updateOrder.getOrderId());
        return operateLogDO;
    }
}
