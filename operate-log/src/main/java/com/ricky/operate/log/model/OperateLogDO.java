package com.ricky.operate.log.model;

import java.io.Serializable;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OperateLogDO
 * @desc 操作日志
 */
public class OperateLogDO {

    /**
     * 操作ID
     */
    private Serializable operateId;

    /**
     * 操作描述
     */
    private String desc;

    /**
     * 执行结果
     */
    private String result;

    public Serializable getOperateId() {
        return operateId;
    }

    public void setOperateId(Serializable operateId) {
        this.operateId = operateId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
