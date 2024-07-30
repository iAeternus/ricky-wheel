package com.ricky.mapstruct.converter;

import com.ricky.mapstruct.test.marker.Entity;
import com.ricky.mapstruct.test.marker.Identifier;
import com.ricky.mapstruct.test.po.BasePO;
import lombok.NonNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className DataConverter
 * @desc
 */
public interface DataConverter<E extends Entity<ID>, ID extends Identifier, PO extends BasePO> {

    /**
     * 转换领域对象DO为持久化对象PO
     *
     * @param entity 领域对象DO
     * @return 持久化对象PO
     */
    PO toPO(@NonNull E entity);

    /**
     * 转换持久化对象PO为领域对象DO
     *
     * @param po 持久化对象PO
     * @return 领域对象DO
     */
    E toEntity(@NonNull PO po);

}
