package com.ricky.common.ddd.utils;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.entity.AggregateDifference;
import com.ricky.common.ddd.model.entity.FieldDifference;
import com.ricky.common.ddd.model.entity.concrete.AggregateFieldDifference;
import com.ricky.common.ddd.model.entity.concrete.CollectionFieldDifference;
import com.ricky.common.ddd.model.entity.concrete.JavaTypeFieldDifference;
import com.ricky.common.ddd.model.enums.DifferenceType;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className DifferentUtils
 * @desc
 */
public class DifferentUtils {

    public static <T extends Aggregate<ID>, ID extends Identifier> AggregateDifference<T, ID> different(T snapshot, T aggregate) {
        DifferenceType basicDifferenceType = basicDifferenceType(snapshot, aggregate);
        if (basicDifferenceType != null) {
            return new AggregateDifference<>(snapshot, aggregate, basicDifferenceType);
        }

        Field[] fields = ReflectionUtils.getFields(aggregate);

        try {
            // 标记Aggregate
            DifferenceType aggregateDifferentType = aggregateDifferentType(fields, snapshot, aggregate);
            // 构建AggregateDifference对象
            AggregateDifference<T, ID> aggregateDifference = new AggregateDifference<>(snapshot, aggregate, aggregateDifferentType);
            Map<String, FieldDifference> fieldDifferences = aggregateDifference.getFieldDifferences();
            // 对比字段差异
            setDifferences(snapshot, aggregate, fields, fieldDifferences);
            return aggregateDifference;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static DifferenceType basicDifferenceType(Object snapshot, Object aggregate) {
        if (snapshot == null && aggregate == null) {
            return DifferenceType.UNTOUCHED;
        }
        if (snapshot == null) {
            return DifferenceType.ADDED;
        }
        if (aggregate == null) {
            return DifferenceType.REMOVED;
        }
        return DifferenceType.MODIFIED;
    }

    public static <T extends Aggregate<ID>, ID extends Identifier> DifferenceType aggregateDifferentType(Field[] fields, T snapshot, T aggregate) throws IllegalAccessException {
        DifferenceType differenceType = basicDifferenceType(snapshot, aggregate);
        if (differenceType != null) {
            return differenceType;
        }

        boolean unchanged = true;
        for (Field field : fields) {
            field.setAccessible(true);

            // 处理需要跳过的情形
            if (shouldSkipClass(field.getType())) {
                continue;
            }

            if (Collection.class.isAssignableFrom(field.getType())) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Class<?> parameterizedClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                if (Aggregate.class.isAssignableFrom(parameterizedClass) || Map.class.isAssignableFrom(parameterizedClass)) {
                    continue;
                }
            }

            // 对比字段差异
            Object aggregateValue = field.get(aggregate);
            Object snapshotValue = field.get(snapshot);
            if (snapshotValue == null && aggregateValue == null) {
                continue;
            } else if (snapshotValue == null) {
                unchanged = false;
                continue;
            }
            unchanged = snapshotValue.equals(aggregateValue) & unchanged;
        }
        return unchanged ? DifferenceType.UNTOUCHED : DifferenceType.MODIFIED;
    }

    private static boolean shouldSkipClass(Class<?> clazz) {
        return Identifier.class.isAssignableFrom(clazz) || Aggregate.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz);
    }

    private static <T extends Aggregate<ID>, ID extends Identifier> void setDifferences(T snapshot, T aggregate, Field[] fields, Map<String, FieldDifference> fieldDifferences) throws IllegalAccessException {
        for (Field field : fields) {
            if (Identifier.class.isAssignableFrom(field.getType())) {
                continue;
            }

            String filedName = ReflectionUtils.getFieldName(field);
            field.setAccessible(true);

            Object snapshotValue = snapshot == null ? null : field.get(snapshot);
            Object aggregateValue = aggregate == null ? null : field.get(aggregate);
            if (snapshotValue == null && aggregateValue == null) {
                continue;
            }
            // 对比每个字段的差异
            FieldDifference fieldDifference = compareFiled(field, snapshotValue, aggregateValue);
            fieldDifferences.put(filedName, fieldDifference);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Aggregate<ID>, ID extends Identifier> FieldDifference compareFiled(Field field, Object snapshotValue, Object aggregateValue) throws IllegalAccessException {
        ComparableType comparableType = ComparableType.comparableType(aggregateValue == null ? snapshotValue : aggregateValue);
        if (ComparableType.AGGREGATE_TYPE.equals(comparableType)) {
            return compareAggregateType(field, (T) snapshotValue, (T) aggregateValue);
        } else if (ComparableType.COLLECTION_TYPE.equals(comparableType)) {
            return compareCollectionType(field, snapshotValue, aggregateValue);
        } else if (ComparableType.JAVA_TYPE.equals(comparableType)) {
            return compareJavaType(field, snapshotValue, aggregateValue);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 可比较的字段类型
     */
    enum ComparableType {
        AGGREGATE_TYPE(),
        COLLECTION_TYPE(),
        JAVA_TYPE(),
        OTHER_TYPE();

        public static ComparableType comparableType(@NonNull Object obj) {
            if (obj instanceof Aggregate) {
                return AGGREGATE_TYPE;
            } else if (obj instanceof Collection) {
                return COLLECTION_TYPE;
            } else if (obj instanceof Map) {
                return OTHER_TYPE;
            } else {
                return JAVA_TYPE;
            }
        }
    }

    private static FieldDifference compareJavaType(Field field, Object snapshotValue, Object aggregateValue) {
        String filedName = ReflectionUtils.getFieldName(field);
        Type type = field.getGenericType();
        DifferenceType differenceType = javaDifferentType(snapshotValue, aggregateValue);
        return new JavaTypeFieldDifference(filedName, type, snapshotValue, aggregateValue, differenceType);
    }

    public static DifferenceType javaDifferentType(Object snapshot, Object aggregate) {
        DifferenceType differenceType = basicDifferenceType(snapshot, aggregate);
        if (differenceType != null) {
            return differenceType;
        }

        if (snapshot.equals(aggregate)) {
            return DifferenceType.UNTOUCHED;
        } else {
            return DifferenceType.MODIFIED;
        }
    }

    private static <T extends Aggregate<ID>, ID extends Identifier> FieldDifference compareAggregateType(Field field, T snapshotValue, T aggregateValue) throws IllegalAccessException {
        String filedName = ReflectionUtils.getFieldName(field);
        Type type = field.getGenericType();

        Aggregate<?> notNullValue = snapshotValue == null ? aggregateValue : snapshotValue;
        Field[] entityFields = ReflectionUtils.getFields(notNullValue);
        Identifier id = notNullValue.getId();

        DifferenceType differenceType = aggregateDifferentType(entityFields, snapshotValue, aggregateValue);
        AggregateFieldDifference aggregateFieldDifference = new AggregateFieldDifference(filedName, type, snapshotValue, aggregateValue, differenceType, id);
        Map<String, FieldDifference> fieldDifferences = aggregateFieldDifference.getFieldDifferences();
        setDifferences(snapshotValue, aggregateValue, entityFields, fieldDifferences);
        return aggregateFieldDifference;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Aggregate<ID>, ID extends Identifier> FieldDifference compareCollectionType(Field field, Object snapshotValue, Object aggregateValue) throws IllegalAccessException {
        String filedName = ReflectionUtils.getFieldName(field);
        Type type = field.getGenericType();

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Class<?> genericityClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];

        // 处理泛型为Java类型的集合
        if (!Aggregate.class.isAssignableFrom(genericityClass) && !Map.class.isAssignableFrom(genericityClass)) {
            Collection<?> snapshotValues = (Collection<?>) snapshotValue;
            Collection<?> aggregateValues = (Collection<?>) aggregateValue;
            DifferenceType differenceType = collectionDifferentType(genericityClass, snapshotValues, aggregateValues);
            return new CollectionFieldDifference(filedName, type, snapshotValue, aggregateValue, differenceType);
        }

        // 处理泛型为实现Aggregate接口的类型的集合
        Collection<T> snapshotValues = (Collection<T>) snapshotValue;
        Collection<T> aggregateValues = (Collection<T>) aggregateValue;

        Map<Serializable, T> snapshotMap = snapshotValues.stream().collect(Collectors.toMap(snapshot -> snapshot.getId().getValue(), snapshot -> snapshot));
        Map<Serializable, T> aggregateMap = aggregateValues.stream().collect(Collectors.toMap(aggregate -> aggregate.getId().getValue(), aggregate -> aggregate));

        CollectionFieldDifference collectionFieldDifference = new CollectionFieldDifference(filedName, type, snapshotValue, aggregateValue);

        boolean unchanged = true;
        // snapshotMap与aggregateMap的交集，snapshotMap对aggregateMap的补集
        for (Serializable key : snapshotMap.keySet()) {
            T snapshotElement = snapshotMap.get(key);
            T aggregateElement = aggregateMap.get(key);
            FieldDifference fieldDifferent = compareFiled(field, snapshotElement, aggregateElement);
            unchanged = DifferenceType.UNTOUCHED.equals(fieldDifferent.getDifferenceType()) & unchanged;
            collectionFieldDifference.getElementDifference().add(fieldDifferent);
        }
        // aggregateMap对snapshotMap的补集
        for (Serializable key : aggregateMap.keySet()) {
            if (snapshotMap.get(key) != null) {
                continue;
            }
            T aggregateElement = aggregateMap.get(key);
            FieldDifference fieldDifferent = compareFiled(field, null, aggregateElement);
            unchanged = DifferenceType.UNTOUCHED.equals(fieldDifferent.getDifferenceType()) & unchanged;
            collectionFieldDifference.getElementDifference().add(fieldDifferent);
        }
        if (unchanged) {
            collectionFieldDifference.setDifferenceType(DifferenceType.UNTOUCHED);
        } else {
            collectionFieldDifference.setDifferenceType(DifferenceType.MODIFIED);
        }
        return collectionFieldDifference;
    }

    public static DifferenceType collectionDifferentType(Class<?> typeArguments, Collection<?> snapshot, Collection<?> aggregate) {
        if (CollectionUtils.isEmpty(snapshot) && CollectionUtils.isEmpty(aggregate)) {
            return DifferenceType.UNTOUCHED;
        }
        if (CollectionUtils.isEmpty(snapshot)) {
            return DifferenceType.ADDED;
        }
        if (CollectionUtils.isEmpty(aggregate)) {
            return DifferenceType.REMOVED;
        }
        if (specialHandingClass(typeArguments)) {
            return snapshot.size() == aggregate.size() ? DifferenceType.UNTOUCHED : DifferenceType.MODIFIED;
        }
        return snapshot.equals(aggregate) ? DifferenceType.UNTOUCHED : DifferenceType.MODIFIED;
    }

    private static boolean specialHandingClass(Class<?> clazz) {
        return shouldSkipClass(clazz) || Collection.class.isAssignableFrom(clazz);
    }

}
