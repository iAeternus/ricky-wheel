package com.ricky.common.ddd.context;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;

public interface TraceContext<T extends Aggregate<ID>, ID extends Identifier> {

    void insert(ID id, T aggregate);

    T find(ID id);

    void remove(ID id);
}