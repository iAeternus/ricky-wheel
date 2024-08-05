package com.ricky.common.ddd.support;

import com.ricky.common.ddd.manager.AggregateTracingManager;
import com.ricky.common.ddd.manager.ThreadLocalTracingManager;
import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.entity.AggregateDifference;
import com.ricky.common.ddd.model.enums.DifferenceType;
import com.ricky.common.ddd.repository.Repository;
import lombok.NonNull;

public abstract class RepositorySupport<T extends Aggregate<ID>, ID extends Identifier> implements Repository<T, ID> {

    private final AggregateTracingManager<T, ID> aggregateTracingManager;

    public RepositorySupport() {
        this.aggregateTracingManager = new ThreadLocalTracingManager<>();
    }

    /**
     * 由继承RepositorySupport的子类实现
     */
    protected abstract T doSelect(ID id);

    protected abstract void doInsert(T aggregate);

    /**
     * 实际执行修改，无需判空
     * @param aggregate 聚合
     * @param aggregateDifference 聚合差异
     */
    protected abstract void doUpdate(T aggregate, AggregateDifference<T, ID> aggregateDifference);

    protected abstract void doDelete(T aggregate);

    /**
     * 主动追踪
     *
     * @param aggregate 聚合根
     */
    public void attach(@NonNull T aggregate) {
        this.aggregateTracingManager.attach(aggregate);
    }

    /**
     * 差异对比
     *
     * @param aggregate 聚合根
     * @return 聚合根差异
     */
    protected AggregateDifference<T, ID> different(T aggregate) {
        return this.aggregateTracingManager.different(aggregate);
    }

    /**
     * 解除追踪
     *
     * @param aggregate 聚合根
     */
    public void detach(@NonNull T aggregate) {
        this.aggregateTracingManager.detach(aggregate);
    }

    @Override
    public T find(@NonNull ID identifier) {
        T aggregate = this.doSelect(identifier);
        if (aggregate != null) {
            this.aggregateTracingManager.attach(aggregate);
        }
        return aggregate;
    }

    @Override
    public void save(@NonNull T aggregate) {
        AggregateDifference<T, ID> aggregateDifference = this.aggregateTracingManager.different(aggregate);
        if(aggregateDifference == null || aggregateDifference.getDifferentType() == DifferenceType.UNTOUCHED) {
            return;
        }

        if (DifferenceType.ADDED.equals(aggregateDifference.getDifferentType())) {
            this.doInsert(aggregate);
        } else {
            this.doUpdate(aggregate, aggregateDifference);
        }
        this.aggregateTracingManager.merge(aggregate);
    }

    @Override
    public void remove(@NonNull T aggregate) {
        this.doDelete(aggregate);
        this.aggregateTracingManager.detach(aggregate);
    }
}