package com.ricky.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/8
 * @className DisjointSet
 * @desc 并查集
 */
public class DisjointSet<T> {

    /**
     * 节点集
     * 键-元素
     * 值-元素对应的节点
     */
    private final Map<T, Node> nodeMap;

    public DisjointSet(List<T> elements) {
        this.nodeMap = new HashMap<>(elements.size());
        for (T element : elements) {
            this.nodeMap.put(element, new Node(element));
        }
    }

    public void add(T element) {
        if (!this.nodeMap.containsKey(element)) {
            this.nodeMap.put(element, new Node(element));
        }
    }

    /**
     * 查询组长
     *
     * @param element 待查元素
     * @return 返回该元素对应的组长，可能返回本身
     */
    public T find(T element) {
        Node cur = this.nodeMap.get(element);
        while (cur != cur.parent) {
            cur.parent = cur.parent.parent;
            cur = cur.parent;
        }
        return cur.value;
    }

    /**
     * 判断两个元素是否属于同一组下
     *
     * @param e1 元素1
     * @param e2 元素2
     * @return 属于同一组返回true，否则返回false
     */
    public boolean isConnected(T e1, T e2) {
        return find(e1).equals(find(e2));
    }

    /**
     * 合并，将两个元素按秩合并到一个组
     *
     * @param e1 元素1
     * @param e2 元素2
     */
    public void union(T e1, T e2) {
        T t1 = find(e1);
        T t2 = find(e2);

        // 如果已经属于同一个集合了，就不用再合并了
        if (t1.equals(t2)) {
            return;
        }

        Node n1 = nodeMap.get(t1);
        Node n2 = nodeMap.get(t2);
        if (n1.rank > n2.rank) {
            n2.parent = n1;
            n1.rank += n2.rank;
        } else {
            n1.parent = n2;
            n2.rank += n1.rank;
        }
    }

    /**
     * 获取秩
     *
     * @param element 元素
     * @return 返回秩，如果不存在返回-1
     */
    public int getRank(T element) {
        Node node = this.nodeMap.get(element);
        if (node == null) {
            return -1;
        }
        return node.rank;
    }

    public void print() {
        Map<T, List<T>> sets = new HashMap<>();

        // 遍历所有元素，并将它们分配到相应的集合中
        for (Map.Entry<T, Node> entry : nodeMap.entrySet()) {
            sets.computeIfAbsent(find(entry.getKey()), k -> new ArrayList<>()).add(entry.getKey());
        }

        // 打印每个集合
        for (List<T> set : sets.values()) {
            System.out.print("[");
            for (int i = 0; i < set.size(); i++) {
                System.out.print(set.get(i));
                if (i < set.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("] ");
        }
        System.out.println();
    }

    /**
     * 并查集节点
     */
    private class Node {

        /**
         * 节点值
         */
        T value;

        /**
         * 节点的秩
         */
        int rank;

        /**
         * 父节点
         */
        Node parent;

        Node(T value) {
            this.value = value;
            this.rank = 1;
            this.parent = this;
        }

        @Override
        public String toString() {
            return "([" + value + "] " + rank + " " + parent.value + ")";
        }
    }

}
