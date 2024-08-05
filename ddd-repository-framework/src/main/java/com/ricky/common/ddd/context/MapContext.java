package com.ricky.common.ddd.context;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import com.ricky.common.ddd.utils.SnapshotUtils;

import java.util.HashMap;
import java.util.Map;

public class MapContext<T extends Aggregate<ID>, ID extends Identifier> implements TraceContext<T, ID> {

    private final Map<ID, T> snapshots;

    public MapContext() {
        this.snapshots = new HashMap<>();
    }

    @Override
    public void insert(ID id, T aggregate) {
        T snapshot = SnapshotUtils.snapshot(aggregate);
        this.snapshots.put(aggregate.getId(), snapshot);
    }

    @Override
    public T find(ID id) {
        for (Map.Entry<ID, T> entry : this.snapshots.entrySet()) {
            ID entryId = entry.getKey();
            if (id.getClass().equals(entryId.getClass()) && entryId.getValue().equals(id.getValue())) {
                return entry.getValue();
            }
        }
        return snapshots.get(id);
    }

    @Override
    public void remove(ID id) {
        this.snapshots.remove(id);
    }

}