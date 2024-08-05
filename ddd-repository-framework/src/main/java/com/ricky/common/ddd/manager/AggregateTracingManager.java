package com.ricky.common.ddd.manager;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.entity.AggregateDifference;

public interface AggregateTracingManager<T extends Aggregate<ID>, ID extends Identifier> {

    /**
     * 变更追踪
     *
     * @param aggregate 聚合
     */
    void attach(T aggregate);

    /**
     * 解除追踪
     *
     * @param aggregate 聚合
     */
    void detach(T aggregate);

    /**
     * 对比差异
     *
     * @param aggregate 聚合
     * @return
     */
    AggregateDifference<T, ID> different(T aggregate);

    /**
     * 合并变更
     *
     * @param aggregate 聚合
     */
    void merge(T aggregate);
}