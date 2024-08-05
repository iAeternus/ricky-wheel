package com.ricky.common.ddd.model.entity.concrete;

import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.entity.FieldDifference;
import com.ricky.common.ddd.model.enums.DifferenceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/27
 * @className AggregateFieldDifference
 * @desc 聚合根字段差异
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AggregateFieldDifference extends FieldDifference {

    private Map<String, FieldDifference> fieldDifferences;

    private Identifier identifier;

    public AggregateFieldDifference(String name, Type type, Object snapshotValue, Object tracValue, DifferenceType differenceType, Identifier identifier) {
        super(name, type, snapshotValue, tracValue, differenceType);
        this.identifier = identifier;
        this.fieldDifferences = new HashMap<>();
    }

}
