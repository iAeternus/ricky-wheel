package com.ricky.common.ddd.model.entity;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.enums.DifferenceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregateDifference<T extends Aggregate<ID>, ID extends Identifier> {

    /**
     * 快照对象
     */
    private T snapshot;

    /**
     * 追踪对象
     */
    private T aggregate;

    /**
     * 差异类型
     */
    private DifferenceType differentType;

    /**
     * 字段差异
     */
    private Map<String, FieldDifference> fieldDifferences;

    public AggregateDifference(T snapshot, T aggregate, DifferenceType differentType) {
        this.snapshot = snapshot;
        this.aggregate = aggregate;
        this.differentType = differentType;
    }

    /**
     * 判断聚合是否被修改
     * @param clazz 聚合字节码
     * @return 若聚合被修改则返回true，否则返回false
     */
    public boolean isSelfModified(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldDifference fieldDifference = fieldDifferences.get(field.getName());
            if(fieldDifference != null && fieldDifference.getDifferenceType() != DifferenceType.UNTOUCHED) {
                return true;
            }
        }
        return false;
    }

}