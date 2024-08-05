package com.ricky.common.ddd.manager;

import com.ricky.common.ddd.context.MapContext;
import com.ricky.common.ddd.context.TraceContext;
import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.model.entity.AggregateDifference;
import com.ricky.common.ddd.utils.DifferentUtils;

public class ThreadLocalTracingManager<T extends Aggregate<ID>, ID extends Identifier> implements AggregateTracingManager<T, ID> {

    private final ThreadLocal<TraceContext<T, ID>> context;

    public ThreadLocalTracingManager() {
        this.context = ThreadLocal.withInitial(MapContext::new);
    }

    @Override
    public void attach(T aggregate) {
        this.context.get().insert(aggregate.getId(), aggregate);
    }

    @Override
    public void detach(T aggregate) {
        this.context.get().remove(aggregate.getId());
    }

    @Override
    public AggregateDifference<T, ID> different(T aggregate) {
        T snapshot = this.context.get().find(aggregate.getId());
        return DifferentUtils.different(snapshot, aggregate);
    }

    @Override
    public void merge(T aggregate) {
        attach(aggregate);
    }
}