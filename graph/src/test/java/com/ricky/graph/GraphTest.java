package com.ricky.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/7
 * @className GraphTest
 * @desc
 */
class GraphTest {

    private Graph<String, Integer> graph;

    @BeforeEach
    public void setUp() {
        graph = Graph.<String, Integer>builder()
                .directed()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addVertex("d", 0)
                .addVertex("e", 0)
                .addVertex("f", 0)
                .addEdge("a", "b", 1)
                .addEdge("a", "c", 7)
                .addEdge("b", "a", 15)
                .addEdge("b", "c", 2)
                .addEdge("b", "d", 5)
                .addEdge("c", "d", 1)
                .addEdge("c", "e", -5)
                .addEdge("d", "f", 4)
                .addEdge("e", "d", 15)
                .addEdge("e", "f", 20)
                .build();
    }

    @Test
    public void print() {
        System.out.println(graph);
    }

    @Test
    public void testOperates() {
        // Given
        Graph<String, Integer> graph = Graph.<String, Integer>builder()
                .directed()
                .addVertex("first", 0)
                .addVertex("second", 0)
                .addVertex("third", 1)
                .addEdge("first", "second", 1)
                .addEdge("first", "third", 2)
                .build();

        // Then
        System.out.println(graph);
        System.out.println(graph.isEmpty() ? "空" : "不空");

        // When
        graph.removeEdge("first", "second");

        // Then
        System.out.println(graph);

        // When
        graph.clear();
        System.out.println(graph.isEmpty() ? "空" : "不空");
    }

    @Test
    public void dfs() {
        // Given
        List<String> list = new ArrayList<>();
        String begin = "a";

        // When
        graph.dfs(begin, vertex -> list.add(vertex.getId()));

        // Then
        assertThat(list).isEqualTo(List.of("a", "b", "c", "d", "f", "e"));
    }

    @Test
    public void bfs() {
        // Given
        List<String> list = new ArrayList<>();
        String begin = "a";

        // When
        graph.bfs(begin, vertex -> list.add(vertex.getId()));

        // Then
        assertThat(list).isEqualTo(List.of("a", "b", "c", "d", "e", "f"));
    }

    @Test
    public void shortestPath1() {
        // Given
        List<Graph<String, Integer>.SimplePath> paths;
        Graph<String, Integer>.SimplePath path;
        String begin = "a", end = "f";

        // When
        paths = graph.shortestPathWithDijkstra(begin);
        path = graph.shortestPathWithDijkstra(begin, end);

        // Then
        paths.forEach(System.out::println);
        System.out.println();
        System.out.println(path);

        assertThat(path.getWeight()).isEqualTo(8);
    }

    @Test
    public void shortestPath2() {
        // Given
        List<Graph<String, Integer>.SimplePath> paths;
        Graph<String, Integer>.SimplePath path;
        String begin = "a", end = "f";

        // When
        paths = graph.shortestPathWithSPFA(begin);
        path = graph.shortestPathWithSPFA(begin, end);

        // Then
        paths.forEach(System.out::println);
        System.out.println();
        System.out.println(path);

        assertThat(path.getWeight()).isEqualTo(8);
    }

    @Test
    public void hasNegativeCycle() {
        // Given
        // 存在负环
        Graph<String, Integer> graph = Graph.<String, Integer>builder()
                .directed()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addVertex("d", 0)
                .addEdge("a", "b", 10)
                .addEdge("b", "c", 2)
                .addEdge("c", "d", 3)
                .addEdge("d", "b", -6)
                .build();
        String begin = "a";

        // When
        boolean result = graph.hasNegativeCycle(begin);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void getRandomVertex() {
        // Given
        int n = 100;
        List<Graph<String, Integer>.Vertex> vertices = new ArrayList<>(n);

        // When
        for (int i = 0; i < n; i++) {
            vertices.add(graph.getRandomVertex());
        }

        // Then
        vertices.forEach(System.out::println);
    }

    @Test
    public void reverse() {
        // When
        Graph<String, Integer> reverseGraph = graph.reverse();

        // Then
        System.out.println(reverseGraph);
    }

    @Test
    public void isStronglyConnected() {
        // Given
        Graph<String, Integer> graph1 = Graph.<String, Integer>builder()
                .directed()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addVertex("d", 0)
                .addEdge("a", "b")
                .addEdge("a", "c")
                .addEdge("b", "d")
                .addEdge("c", "d")
                .build();

        Graph<String, Integer> graph2 = Graph.<String, Integer>builder()
                .directed()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addEdge("a", "b")
                .addEdge("b", "c")
                .addEdge("c", "b")
                .addEdge("c", "a")
                .build();

        Graph<String, Integer> graph3 = Graph.<String, Integer>builder()
                .unDirected()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addEdge("a", "b")
                .addEdge("a", "c")
                .addEdge("b", "c")
                .build();

        Graph<String, Integer> graph4 = Graph.<String, Integer>builder()
                .unDirected()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addEdge("a", "b")
                .build();

        // When
        boolean result1 = graph1.isStronglyConnected();
        boolean result2 = graph2.isStronglyConnected();
        boolean result3 = graph3.isStronglyConnected();
        boolean result4 = graph4.isStronglyConnected();

        // Then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isFalse();
    }

    @Test
    public void minSpanningTree1() {
        // When
        Graph<String, Integer> minSpanningTree = graph.mst();

        // Then
        System.out.println(minSpanningTree);
        assertThat(minSpanningTree.getWeight()).isEqualTo(3);
    }

    @Test
    public void minSpanningTree2() {
        // Given
        Graph<String, Integer> graph = Graph.<String, Integer>builder()
                .unDirected()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addVertex("d", 0)
                .addEdge("a", "b", 2)
                .addEdge("a", "c", 1)
                .addEdge("a", "d", 8)
                .addEdge("b", "c", 7)
                .addEdge("b", "d", 1)
                .addEdge("c", "d", 4)
                .build();

        // When
        Graph<String, Integer> minSpanningTree = graph.mst();

        // When
        System.out.println(minSpanningTree);
        assertThat(minSpanningTree.getWeight()).isEqualTo(4);
    }

    @Test
    public void topologicalSortWithDfs() {
        // When
        List<Graph<String, Integer>.Vertex> vertices = graph.topologicalSort();

        // Then
        vertices.forEach(System.out::println);
        assertThat(vertices.stream().map(Graph.Vertex::getId)).isEqualTo(List.of("a", "b", "c", "e", "d", "f"));
    }

    @Test
    public void findBridge() {
        // Given
        Graph<String, Integer> graph = Graph.<String, Integer>builder()
                .unDirected()
                .addVertex("a", 0)
                .addVertex("b", 0)
                .addVertex("c", 0)
                .addVertex("d", 0)
                .addVertex("e", 0)
                .addVertex("f", 0)
                .addVertex("g", 0)
                .addVertex("h", 0)
                .addVertex("i", 0)
                .addEdge("a", "b")
                .addEdge("a", "c")
                .addEdge("a", "d")
                .addEdge("b", "c")
                .addEdge("b", "g")
                .addEdge("d", "e")
                .addEdge("d", "f")
                .addEdge("e", "f")
                .addEdge("g", "h")
                .addEdge("g", "i")
                .addEdge("h", "i")
                .build();

        // When
        List<Graph<String, Integer>.Edge> bridges = graph.findBridge();

        // Then
        bridges.forEach(System.out::println);
        assertThat(bridges.stream().map(Graph.Edge::getBegin)).isEqualTo(List.of("b", "a"));
        assertThat(bridges.stream().map(Graph.Edge::getEnd)).isEqualTo(List.of("g", "d"));
    }

}