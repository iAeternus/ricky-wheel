package com.ricky.operate.log.converter.impl;

import com.ricky.operate.log.converter.Converter;
import com.ricky.operate.log.model.OperateLogDO;
import com.ricky.operate.log.model.SaveOrder;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className SaveOrderConverter
 * @desc
 */
public class SaveOrderConverter implements Converter<SaveOrder> {
    @Override
    public OperateLogDO convert(SaveOrder saveOrder) {
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOperateId(saveOrder.getId());
        return operateLogDO;
    }
}
