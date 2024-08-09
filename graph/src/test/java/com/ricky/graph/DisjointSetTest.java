package com.ricky.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/8
 * @className DisjointSetTest
 * @desc
 */
class DisjointSetTest {

    @Test
    public void test() {
        DisjointSet<Integer> disjointSet = new DisjointSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println("初始：");
        disjointSet.print();

        System.out.println("连接5 6：");
        disjointSet.union(5, 6);
        disjointSet.print();

        System.out.println("连接1 2：");
        disjointSet.union(1, 2);
        disjointSet.print();

        System.out.println("连接2 3：");
        disjointSet.union(2, 3);
        disjointSet.print();

        System.out.println("连接1 4：");
        disjointSet.union(1, 4);
        disjointSet.print();

        System.out.println("连接1 5：");
        disjointSet.union(1, 5);
        disjointSet.print();

        System.out.println("1  5 是否连接：" + disjointSet.isConnected(1, 5));
        System.out.println("1  6 是否连接：" + disjointSet.isConnected(1, 6));
        System.out.println("1  8 是否连接：" + disjointSet.isConnected(1, 8));

        System.out.print("秩: ");
        for (int i = 1; i <= 10; ++i) {
            System.out.print(disjointSet.getRank(i) + "　");
        }
    }

}