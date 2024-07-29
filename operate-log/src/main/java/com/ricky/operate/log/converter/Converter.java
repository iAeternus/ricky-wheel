package com.ricky.operate.log.converter;

import com.ricky.operate.log.model.OperateLogDO;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className Converter
 * @desc
 */
public interface Converter<PARAM> {

    /**
     * 将入参转换为标准的日志模型
     * @param param 入参
     * @return 操作日志模型
     */
    OperateLogDO convert(PARAM param);

}
