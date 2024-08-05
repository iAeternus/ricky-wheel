package com.ricky.common.ddd.repository;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.entity.AggregateDifference;
import com.ricky.common.ddd.persistence.mapper.Mapper;
import com.ricky.common.ddd.persistence.po.BasePO;
import com.ricky.common.ddd.support.RepositorySupport;
import jakarta.annotation.Resource;

import java.io.Serializable;
import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className RepositoryImpl
 * @desc
 */
@org.springframework.stereotype.Repository
public class RepositoryImpl<T extends Aggregate<ID>, ID extends Identifier, PO extends BasePO> extends RepositorySupport<T, ID> implements Repository<T, ID> {

    @Resource
    private Mapper<PO> aggregateMapper;

    private Map<Class<?>, Mapper<PO>> associationMappers;

    @Override
    protected T doSelect(ID id) {
        Serializable identifier = id.getValue();
        PO po = aggregateMapper.selectById(identifier);
        if(po == null) {
            throw new RuntimeException("Query result not found, id=" + identifier);
        }

        // select association objects
        associationMappers.forEach((clazz, associationMapper) -> {

        });

        return null;
    }

    @Override
    protected void doInsert(T aggregate) {

    }

    @Override
    protected void doUpdate(T aggregate, AggregateDifference<T, ID> aggregateDifference) {

    }

    @Override
    protected void doDelete(T aggregate) {

    }

}
