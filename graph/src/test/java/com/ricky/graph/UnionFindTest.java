package com.ricky.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/8
 * @className UnionFindTest
 * @desc
 */
class UnionFindTest {

    @Test
    public void test() {
        UnionFind<Integer> unionFind = new UnionFind<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println("初始：");
        unionFind.print();

        System.out.println("连接5 6：");
        unionFind.union(5, 6);
        unionFind.print();

        System.out.println("连接1 2：");
        unionFind.union(1, 2);
        unionFind.print();

        System.out.println("连接2 3：");
        unionFind.union(2, 3);
        unionFind.print();

        System.out.println("连接1 4：");
        unionFind.union(1, 4);
        unionFind.print();

        System.out.println("连接1 5：");
        unionFind.union(1, 5);
        unionFind.print();

        System.out.println("1  5 是否连接：" + unionFind.isConnected(1, 5));
        System.out.println("1  6 是否连接：" + unionFind.isConnected(1, 6));
        System.out.println("1  8 是否连接：" + unionFind.isConnected(1, 8));

        System.out.print("秩: ");
        for(int i = 1; i <= 10; ++i) {
            System.out.print(unionFind.getRank(i) + "　");
        }
    }

}