package org.ricky.repositoryframework.persistence.converter;

import org.ricky.repositoryframework.marker.Entity;
import org.ricky.repositoryframework.marker.Identifier;
import org.ricky.repositoryframework.persistence.po.BasePO;
import org.springframework.stereotype.Service;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/8
 * @className DataConverter
 * @desc 数据转换器接口
 */
@Service
public interface DataConverter<E extends Entity<ID>, ID extends Identifier, PO extends BasePO> {

    /**
     * 转换领域对象DO为持久化对象PO
     *
     * @param entity 领域对象DO
     * @return 持久化对象PO
     */
    PO convert(E entity);

    /**
     * 转换持久化对象PO为领域对象DO
     *
     * @param po 持久化对象PO
     * @return 领域对象DO
     */
    E convert(PO po);

}
