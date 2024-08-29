package com.ricky.test.model;

import lombok.Data;
import org.ricky.repositoryframework.marker.Aggregate;
import org.ricky.repositoryframework.marker.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className DataBase
 * @desc 模拟数据库的功能
 */
@Data
public class DataBase {

    private final List<Aggregate<Identifier>> aggregates = new ArrayList<>();

    private DataBase() {
    }

    public static DataBase getInstance() {
        return Holder.INSTANCE;
    }

    public void insert(Aggregate<Identifier> aggregate) {
        aggregates.add(aggregate);
    }

    public void remove(Aggregate<Identifier> aggregate) {
        aggregates.remove(aggregate);
    }

    public void update(Aggregate<Identifier> aggregate) {
        if (aggregates.remove(aggregate)) {
            aggregates.add(aggregate);
        }
    }

    public Aggregate<Identifier> find(Identifier id) {
        for (Aggregate<Identifier> aggregate : aggregates) {
            if (aggregate.getId() == id) {
                return aggregate;
            }
        }
        return null;
    }

    private static class Holder {
        private static final DataBase INSTANCE = new DataBase();
    }

}
