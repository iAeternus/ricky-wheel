package com.ricky.common.ddd.model.entity;

import com.ricky.common.ddd.model.enums.DifferenceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDifference {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     */
    private Type type;

    /**
     * 快照值
     */
    private Object snapshotValue;

    /**
     * 当前值
     */
    private Object tracValue;

    /**
     * 差异类型
     */
    private DifferenceType differenceType;

    public FieldDifference(String name, Type type, Object snapshotValue, Object tracValue) {
        this.name = name;
        this.type = type;
        this.snapshotValue = snapshotValue;
        this.tracValue = tracValue;
    }

}