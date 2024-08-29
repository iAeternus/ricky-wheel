package org.ricky.repositoryframework.model.diff.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ricky.repositoryframework.model.diff.enums.DifferenceType;

import java.lang.reflect.Type;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/27
 * @className FieldDifference
 * @desc 聚合根字段差异
 */
@Data
@Builder
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
