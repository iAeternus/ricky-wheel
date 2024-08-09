package com.ricky.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @param <ID> 节点的标识符类型
 * @param <V>  点权类型
 * @author Ricky
 * @version 1.0
 * @date 2024/8/7
 * @className Graph
 * @desc 图
 */
public class Graph<ID, V> {

    /**
     * 点集大小
     */
    private int vertexCount;

    /**
     * 边集大小
     */
    private int edgeCount;

    /**
     * 是否是有向图
     */
    private boolean isDirect;

    /**
     * 用来存储顶点的邻接表
     * ID做为标识，Vertex作为实际的顶点
     */
    private final Map<ID, Vertex> vertexMap;

    private Graph() {
        this.vertexMap = new LinkedHashMap<>();
    }

    public Graph(boolean isDirect) {
        this.isDirect = isDirect;
        this.vertexMap = new LinkedHashMap<>();
    }

    // 下面与图的顶点相关

    /**
     * 获取顶点个数
     *
     * @return 顶点个数
     */
    public int getVertexCount() {
        return this.vertexCount;
    }

    /**
     * 获取顶点的迭代器
     *
     * @return 顶点的迭代器
     */
    public Iterator<Vertex> getVertexIterator() {
        return this.vertexMap.values().iterator();
    }

    /**
     * 获取顶点集
     */
    public List<Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>();
        Iterator<Vertex> iterator = getVertexIterator();
        while (iterator.hasNext()) {
            vertices.add(iterator.next());
        }
        return vertices;
    }

    /**
     * 根据顶点的标识符获取顶点
     *
     * @param id 标识符
     * @return 若顶点存在则返回顶点<br>
     * 否则返回null
     */
    public Vertex getVertex(ID id) {
        return this.vertexMap.get(id);
    }

    /**
     * 获取随机的顶点id
     *
     * @return 返回随机的顶点id
     */
    public Vertex getRandomVertex() {
        Random random = new Random();
        ArrayList<ID> list = new ArrayList<>(this.vertexMap.keySet());
        return this.vertexMap.get(list.get(random.nextInt(list.size())));
    }

    /**
     * 在图中插入顶点
     *
     * @param id    顶点的标识
     * @param value 顶点的权值
     * @return 如果图中不存在该顶点，则插入，返回true<br>
     * 如果图中已经存在该顶点，则更新权值，返回false
     */
    public boolean addVertex(ID id, V value) {
        Vertex vertex = getVertex(id);
        if (vertex != null) {
            // 如果图中已经存在该顶点，则更新权值，返回false
            vertex.setValue(value);
            return false;
        }
        // 如果图中不存在该顶点，则插入，返回true
        this.vertexMap.put(id, new Vertex(id, value));
        ++this.vertexCount;
        return true;
    }

    // 下面与图的边相关

    /**
     * 设置有向性
     */
    public void setDirect(boolean direct) {
        this.isDirect = direct;
    }

    /**
     * 返回图中所有的边的个数
     *
     * @return 如果为有向图，则是所有的有向边的个数<br>
     * 如果为无向图，则视一条边为两条相反的有向边，相当于返回无向边的个数*2
     */
    public int getEdgeCount() {
        return this.edgeCount;
    }

    /**
     * 返回图中标识为id的顶点作为出发点的边的个数
     *
     * @param id 标识符
     * @return 如果为有向图，则返回标识为id的顶点作为出发点的边的个数<br>
     * 如果为无向图，则返回标识为id的顶点相连接的边的个数<br>
     * 如果图中没有这个顶点，返回-1
     */
    public int getEdgeCount(ID id) {
        Vertex vertex = getVertex(id);
        if (vertex == null) {
            // 如果图中没有这个顶点，返回-1
            return -1;
        }
        // 返回途中标识为label的顶点作为出发点的边的个数
        return vertex.getEdgeCount();
    }

    /**
     * 返回图中标识为id的顶点作为出发点的边的迭代器
     *
     * @param id 标识符
     * @return 如果没有这个顶点，返回null
     */
    public Iterator<Edge> getEdgeIterator(ID id) {
        Vertex vertex = getVertex(id);
        if (vertex == null) {
            // 如果没有这个顶点，返回null
            return null;
        }
        return vertex.getEdgeIterator();
    }

    /**
     * 获取边集
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        Iterator<Vertex> vertexIterator = getVertexIterator();
        while (vertexIterator.hasNext()) {
            Vertex vertex = vertexIterator.next();
            Iterator<Edge> edgeIterator = getEdgeIterator(vertex.getId());
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                edges.add(edge);
            }
        }
        return edges;
    }

    /**
     * 在图中加入一条边<br>
     * 如果isDirect为true，则为有向图，则建立一条以begin作为标识的节点开始的边，以end作为标识的节点结束，边的权值为weight<br>
     * 如果isDirect为false，则为无向图，则建立两条边，一条以begin开始，到end，一条以end开始，到begin
     *
     * @param begin  起始点的标识符
     * @param end    结束点的标识符
     * @param weight 如果不需要边的权值，可以设为0
     * @return 如果没有对应的边，则加入对应的边，返回true<br>
     * 如果有对应的边，则更新weight，返回false<br>
     * 如果没有以begin或者end标识的顶点，则直接返回false
     */
    public boolean addEdge(ID begin, ID end, double weight) {
        Vertex beginVertex = getVertex(begin);
        Vertex endVertex = getVertex(end);
        if (beginVertex == null || endVertex == null) {
            // 如果没有以begin或者end标识的顶点，则直接返回false
            return false;
        }
        // 有向图和无向图都要建立begin到end的边
        // 如果顶点已经与end连接，那么将会更新权值，result=false
        // 如果顶点没有与end相连，则互相连接，result=true
        boolean result = beginVertex.connect(endVertex, weight);
        if (result) {
            ++this.edgeCount;
        }
        if (!this.isDirect) {
            // 如果不是有向图，则建立两条边,一条以end开始，到begin
            endVertex.connect(beginVertex, weight);
            if (result) {
                ++this.edgeCount;
            }
        }
        return result;
    }

    /**
     * 在图中删除一条边<br>
     * 如果isDirect为true，则为有向图，则删除一条以begin作为标识的节点开始的边，以end作为标识的节点结束<br>
     * 如果isDirect为false，则为无向图，则删除两条边，一条以begin开始，到end，一条以end开始，到begin
     *
     * @param begin 起始点的标识符
     * @param end   结束点的标识符
     * @return 如果有对应的边，则删除对应的边，返回true<br>
     * 如果没有有对应的边，则直接返回false<br>
     * 如果没有以begin或者end标识的顶点，则直接返回false
     */
    public boolean removeEdge(ID begin, ID end) {
        Vertex beginVertex = getVertex(begin);
        Vertex endVertex = getVertex(end);
        if (beginVertex == null || endVertex == null) {
            // 如果没有以begin或者end标识的顶点，则直接返回false
            return false;
        }
        // 有向图和无向图都要删除begin到end的边
        // 如果顶点已经与end连接，那么将会删除这条边，返回true
        // 如果顶点没有与end连接，则啥都不做，返回false
        boolean result = beginVertex.disconnect(endVertex);
        if (result) {
            --this.edgeCount;
        }
        if (!this.isDirect) {
            // 如果不是有向图，则删除两条边,一条以end开始，到begin
            endVertex.disconnect(beginVertex);
            if (result) {
                --this.edgeCount;
            }
        }
        return result;
    }

    /**
     * 获取图的权重<br>
     *
     * @return 若为有向图，返回所有边权之和<br>
     * 若为无向图，对每条无向边仅计算一次边权，即边权之和/2
     */
    public double getWeight() {
        double weight = 0.0;
        Iterator<Vertex> vertexIterator = getVertexIterator();
        while (vertexIterator.hasNext()) {
            Vertex vertex = vertexIterator.next();
            Iterator<Edge> edgeIterator = getEdgeIterator(vertex.getId());
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                weight += edge.getWeight();
            }
        }
        return this.isDirect ? weight : weight / 2;
    }

    /**
     * 清空图<br>
     * 清空所有顶点和边
     */
    public void clear() {
        this.vertexMap.clear();
    }

    /**
     * 判断图是否为空图
     *
     * @return 如果为空图，则返回true，否则返回false
     */
    public boolean isEmpty() {
        return this.vertexMap.isEmpty();
    }

    // 下面与图算法相关

    /**
     * 深度优先搜索
     * O(T + E)
     *
     * @param id       搜索起始点
     * @param consumer 具体执行的搜索逻辑
     */
    public void dfs(ID id, Consumer<Vertex> consumer) {
        resetAllVisit();
        dfsRecursiveFunction(id, consumer);
    }

    /**
     * 重设所有顶点的访问状态
     */
    private void resetAllVisit() {
        Iterator<Vertex> iterator = getVertexIterator();
        Vertex vertex;
        while (iterator.hasNext()) {
            vertex = iterator.next();
            vertex.unVisit();
        }
    }

    /**
     * 深度优先搜索递归函数
     *
     * @param id       搜索起始点
     * @param consumer 具体执行的搜索逻辑
     */
    private void dfsRecursiveFunction(ID id, Consumer<Vertex> consumer) {
        Vertex cur = getVertex(id);
        consumer.accept(cur);
        cur.visit();
        cur.forEachNeighborVertex(neighbor -> {
            if (!neighbor.isVisited()) {
                dfsRecursiveFunction(neighbor.getId(), consumer);
            }
        });
    }

    /**
     * 广度优先搜索
     * O(T + E)
     *
     * @param id       搜索起始点
     * @param consumer 具体执行的搜索逻辑
     */
    public void bfs(ID id, Consumer<Vertex> consumer) {
        resetAllVisit();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex cur = getVertex(id);
        queue.add(cur);
        cur.visit();
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            consumer.accept(vertex);
            vertex.forEachNeighborVertex(neighbor -> {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                    neighbor.visit();
                }
            });
        }
    }

    /**
     * 单源最短路径-Dijkstra算法
     * O((T+E)logV)
     * 从源点开始，不断找到当前未处理节点中距离源点最近的节点，更新其相邻节点的距离，直到所有节点都被处理过<br>
     * 不能处理带有负权的图
     *
     * @param begin 起点
     * @return 返回从起点到所有顶点的简单路径，保证每条路径权值最小
     */
    public List<SimplePath> shortestPathWithDijkstra(ID begin) {
        if (getVertex(begin) == null) {
            return null;
        }

        resetAllVisit();
        resetAllPrevious();

        Map<ID, Double> dist = new HashMap<>();
        initDist(dist);

        // end-顶点索引 weight-连接边权
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Edge(begin, 0.0));

        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            Vertex cur = edge.getEndVertex();
            if (cur.isVisited()) {
                continue;
            }
            cur.visit();
            cur.forEachNeighborVertex(neighbor -> {
                if (!neighbor.isVisited()) {
                    double newDist = cur.isConnected(neighbor).getWeight() + edge.getWeight();
                    if (newDist < dist.get(neighbor.getId())) {
                        dist.put(neighbor.getId(), newDist); // 替换原值
                        neighbor.setPrevious(cur); // 设置前驱
                        priorityQueue.offer(new Edge(neighbor.getId(), newDist));
                    }
                }
            });
        }

        return buildSimplePaths(dist);
    }

    /**
     * 计算起点到终点的最短路径-Dijkstra算法
     *
     * @param begin 起点
     * @param end   终点
     * @return 返回一条从起点到终点的简单路径，保证路径权值最小<br>
     * 若不存在则返回null
     */
    public SimplePath shortestPathWithDijkstra(ID begin, ID end) {
        List<SimplePath> simplePaths = endWith(shortestPathWithDijkstra(begin), end);
        return simplePaths.size() == 1 ? simplePaths.get(0) : null;
    }

    /**
     * 单源最短路径-SPFA算法
     * O(E)
     * 从源点开始，将源点加入队列，并不断从队列中取出节点进行松弛操作，更新相邻节点的距离，直到队列为空<br>
     * 使用SLF优化：使用双端队列优化，设从 u 拓展出了 v、队首元素 k，如果 dist[v] < dist[k]，则将 v 加入队首，否则加入队尾
     *
     * @param begin 起点
     * @return 返回从起点到所有顶点的简单路径，保证每条路径权值最小
     */
    public List<SimplePath> shortestPathWithSPFA(ID begin) {
        if (getVertex(begin) == null) {
            return null;
        }

        resetAllVisit();
        resetAllPrevious();

        Map<ID, Double> dist = new HashMap<>();
        initDist(dist);
        dist.put(begin, 0.0);

        Deque<ID> deque = new LinkedList<>();
        deque.addLast(begin);
        getVertex(begin).visit(); // 标记起点为已访问

        while (!deque.isEmpty()) {
            ID curId = deque.pollFirst();
            Vertex cur = getVertex(curId);

            cur.forEachNeighborVertex(neighbor -> {
                double weight = cur.isConnected(neighbor).getWeight();
                if (dist.get(neighbor.getId()) > dist.get(curId) + weight) {
                    dist.put(neighbor.getId(), dist.get(curId) + weight);

                    if (!neighbor.isVisited()) {
                        neighbor.visit(); // 标记为已访问
                        neighbor.setPrevious(cur); // 设置前驱

                        // SLF优化：如果邻居节点的距离小于队首元素的距离，则插入队首
                        if (deque.isEmpty() || dist.get(neighbor.getId()) < dist.get(deque.peekFirst())) {
                            deque.addFirst(neighbor.getId());
                        } else {
                            deque.addLast(neighbor.getId());
                        }
                    }
                }
            });
        }
        return buildSimplePaths(dist);
    }

    /**
     * 计算起点到终点的最短路径-Dijkstra算法
     *
     * @param begin 起点
     * @param end   终点
     * @return 返回一条从起点到终点的简单路径，保证路径权值最小<br>
     * 若不存在则返回null
     */
    public SimplePath shortestPathWithSPFA(ID begin, ID end) {
        List<SimplePath> simplePaths = endWith(shortestPathWithSPFA(begin), end);
        return simplePaths.size() == 1 ? simplePaths.get(0) : null;
    }

    /**
     * 初始化距离数组，长度为顶点数，每个值被初始化为INF
     *
     * @param dist 距离数组
     */
    private void initDist(Map<ID, Double> dist) {
        Iterator<Vertex> iterator = getVertexIterator();
        Vertex vertex;
        while (iterator.hasNext()) {
            vertex = iterator.next();
            dist.put(vertex.id, Edge.INF);
        }
    }

    /**
     * 根据距离数组构建路径组
     *
     * @param dist 距离数组
     * @return 简单路径组
     */
    private List<SimplePath> buildSimplePaths(Map<ID, Double> dist) {
        Iterator<Vertex> iterator = getVertexIterator();
        List<SimplePath> simplePaths = new ArrayList<>();
        Vertex vertex, cur;
        List<Vertex> vertices;
        // 遍历所有顶点
        while (iterator.hasNext()) {
            vertex = iterator.next();
            vertices = new ArrayList<>();
            vertices.add(vertex);
            cur = vertex.getPrevious();
            // 查找所有前驱
            while (cur != null) {
                vertices.add(cur);
                cur = cur.getPrevious();
            }
            Collections.reverse(vertices); // 反转列表
            simplePaths.add(new SimplePath(dist.get(vertex.getId()), vertices));
        }
        return simplePaths;
    }

    /**
     * 重设所有顶点的前驱顶点
     */
    private void resetAllPrevious() {
        Iterator<Vertex> iterator = getVertexIterator();
        Vertex vertex;
        while (iterator.hasNext()) {
            vertex = iterator.next();
            vertex.setPrevious(null);
        }
    }

    /**
     * 负环判定-SPFA算法
     * 基于 SPFA 的负环判定，使用 inCount[v] 记录节点 v 的入队次数；在松弛的时候有状态转移 inCount[u] =
     * inCount[v] + 1, 如果有一个点的 inCount[v] ≥n，说明存在负环
     *
     * @param begin 起点
     * @return 图中存在负环返回true，否则返回false
     */
    public boolean hasNegativeCycle(ID begin) {
        Vertex beginVertex = getVertex(begin);
        if (beginVertex == null) {
            return false;
        }
        resetAllVisit();
        beginVertex.visit();

        Map<ID, Double> dist = new HashMap<>();
        initDist(dist);
        dist.put(begin, 0.0);

        Map<ID, Integer> inCount = new HashMap<>();
        initInCount(inCount);

        Queue<ID> queue = new LinkedList<>();
        queue.add(begin);

        while (!queue.isEmpty()) {
            ID curId = queue.poll();
            Vertex cur = getVertex(curId);
            cur.unVisit();
            // 遍历cur的邻接结点
            Iterator<Edge> iterator = cur.getEdgeIterator();
            while (iterator.hasNext()) {
                Edge edge = iterator.next();
                Vertex neighbor = edge.getEndVertex();
                double weight = cur.isConnected(neighbor).getWeight();
                if (dist.get(neighbor.getId()) > dist.get(curId) + weight) {
                    dist.put(neighbor.getId(), dist.get(curId) + weight);
                    inCount.put(neighbor.getId(), inCount.get(curId) + 1);
                    if (inCount.get(neighbor.getId()) >= this.vertexCount) {
                        return true;
                    }
                    if (!neighbor.isVisited()) {
                        neighbor.visit();
                        queue.add(neighbor.getId());
                    }
                }
            }
        }
        return false;
    }

    /**
     * 初始化inCount，大小为顶点数，全部初始化为0
     *
     * @param inCount inCount数组
     */
    private void initInCount(Map<ID, Integer> inCount) {
        Iterator<Vertex> iterator = getVertexIterator();
        Vertex vertex;
        while (iterator.hasNext()) {
            vertex = iterator.next();
            inCount.put(vertex.id, 0);
        }
    }

    /**
     * 获取反向图，反转所有边
     *
     * @return 若为有向图，返回反向图<br>
     * 若为无向图，则返回自身
     */
    public Graph<ID, V> reverse() {
        if (!this.isDirect) {
            return this;
        }

        Graph<ID, V> reverseGraph = new Graph<>(true);
        Iterator<Vertex> iterator = getVertexIterator();
        while (iterator.hasNext()) {
            Vertex vertex = iterator.next();
            reverseGraph.addVertex(vertex.getId(), vertex.getValue());
        }

        List<Edge> edges = getEdges();
        for (Edge edge : edges) {
            reverseGraph.addEdge(edge.getEnd(), edge.getBegin(), edge.getWeight());
        }

        return reverseGraph;
    }

    /**
     * 判断是否为强连通图<br>
     * 强连通图要求图中任意两个顶点之间都存在双向路径<br>
     * 这里使用Kosaraju基于DFS的简单算法<br>
     *
     * @return 若为强连通图返回true，否则返回false
     */
    public boolean isStronglyConnected() {
        // 将所有顶点初始化为未访问
        resetAllVisit();

        // 从任意顶点v开始进行图的DFS遍历。如果DFS遍历没有访问完所有顶点，则返回false
        ID beginId = getRandomVertex().getId();
        AtomicInteger count = new AtomicInteger();
        this.dfs(beginId, id -> count.incrementAndGet());
        if (count.get() != vertexCount) {
            return false;
        }

        // 处理无向图的情况
        if (!this.isDirect) {
            return true;
        }

        // 获取反向图
        Graph<ID, V> reverseGraph = reverse();

        // 从相同的顶点v开始进行反向图的DFS遍历。如果DFS遍历不访问所有顶点，则返回false。否则返回true
        count.set(0);
        reverseGraph.dfs(beginId, id -> count.incrementAndGet());
        return count.get() == vertexCount;
    }

    /**
     * 最小生成树 MST<br>
     * 根据是否为稠密图选择算法<br>
     * 若是稠密图，选择Prim算法<br>
     * 否则选择Kruskal算法
     *
     * @return 返回最小生成树，以一张新图表示
     */
    public Graph<ID, V> mst() {
        if (isDenseGraph()) {
            return isStronglyConnected() ? minSpanningTreeWithPrim() : minSpanningTreeWithKruskal();
        } else {
            return minSpanningTreeWithKruskal();
        }
    }

    /**
     * 稠密图阈值<br>
     * 如果你希望图更加“稠密”才能被认为是稠密图，你可以增加这个阈值<br>
     * 反之，如果你希望图的“稠密”程度较低也能被认为是稠密图，你可以减小这个阈值
     */
    public static final double DENSE_EPS = 0.8;

    /**
     * 判断图是否为稠密图<br>
     * 具体的<br>
     * 对于无向图，边数 >= DENSE_EPS * T(T−1)/2
     * 对于有向图，边数 >= DENSE_EPS * T(T−1)
     *
     * @return 若图为稠密图则返回true，否则返回false
     */
    public boolean isDenseGraph() {
        // 完全图的边数
        int maxEdgesInSparseGraph = isDirect ? vertexCount * (vertexCount - 1) : vertexCount * (vertexCount - 1) >> 1;
        return edgeCount >= DENSE_EPS * maxEdgesInSparseGraph;
    }

    /**
     * 最小生成树-Kruskal算法
     * O(E logE)
     *
     * @return 返回最小生成树，以一张新图表示
     */
    private Graph<ID, V> minSpanningTreeWithKruskal() {
        // 创建并初始化最小生成树
        Graph<ID, V> minSpanningTree = createMinSpanningTree();

        // 构造并查集
        DisjointSet<ID> disjointSet = new DisjointSet<>(getVertices().stream()
                .map(Vertex::getId)
                .toList());

        // 获取边集
        List<Edge> edges = getEdges();
        // 按照边权升序排序
        edges.sort(Comparator.comparingDouble((Edge o) -> o.weight));

        for (Edge edge : edges) {
            if (isCross(disjointSet, edge)) {
                minSpanningTree.addEdge(edge.getBegin(), edge.getEnd(), edge.getWeight());
                disjointSet.union(edge.getBegin(), edge.getEnd());
            }
        }

        return minSpanningTree;
    }

    /**
     * 判断一条边是否为“横跨”
     *
     * @param disjointSet 并查集
     * @param edge        边
     * @return 如果为“横跨”返回true，否则返回false
     */
    private boolean isCross(DisjointSet<ID> disjointSet, Edge edge) {
        return disjointSet.find(edge.getBegin()) != disjointSet.find(edge.getEnd());
    }

    /**
     * 最小生成树-Prim算法
     * 算法只能运行在强连通图上，否则每次运行结果会受随机起点的影响而不一样
     * O(E logV)
     *
     * @return 返回最小生成树，以一张新图表示
     */
    private Graph<ID, V> minSpanningTreeWithPrim() {
        resetAllVisit();
        resetAllPrevious();

        Graph<ID, V> minSpanningTree = createMinSpanningTree();

        Map<ID, Double> dist = new HashMap<>();
        initDist(dist);

        // 随机选取起点
        ID beginId = getRandomVertex().getId();
        dist.put(beginId, 0.0);

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Edge(beginId, 0.0));
        while (!priorityQueue.isEmpty()) {
            Edge lightEdge = priorityQueue.poll();
            ID curId = lightEdge.getEnd();
            Vertex cur = getVertex(curId);

            if (cur.isVisited()) {
                continue;
            }

            cur.visit();

            // 如果有前驱顶点，则添加边到MST
            Vertex previous = cur.getPrevious();
            if (previous != null) {
                minSpanningTree.addEdge(previous.getId(), curId, lightEdge.getWeight());
            }

            // 遍历当前顶点的所有邻接边，更新距离和前驱，并加入优先队列
            cur.forEachNeighborEdge(edge -> {
                Vertex neighbor = edge.getEndVertex();
                if (!neighbor.isVisited() && edge.getWeight() < dist.getOrDefault(neighbor.getId(), Edge.INF)) {
                    dist.put(neighbor.getId(), edge.getWeight());
                    neighbor.setPrevious(cur);
                    priorityQueue.offer(new Edge(curId, neighbor.getId(), edge.getWeight()));
                }
            });
        }

        return minSpanningTree;
    }

    /**
     * 创建并初始化最小生成树
     */
    private Graph<ID, V> createMinSpanningTree() {
        Graph<ID, V> minSpanningTree = new Graph<>(this.isDirect);
        Iterator<Vertex> iterator = getVertexIterator();
        while (iterator.hasNext()) {
            Vertex vertex = iterator.next();
            minSpanningTree.addVertex(vertex.getId(), vertex.getValue());
        }
        return minSpanningTree;
    }

    /**
     * 拓扑排序-dfs实现<br>
     * 算法只适用于有向无环图
     *
     * @return 返回一个拓扑序
     */
    public List<Vertex> topologicalSort() {
        if (!this.isDirect) {
            throw new RuntimeException("拓扑排序只适用于有向无环图, 但你的图是无向图");
        }

        resetAllVisit();
        Stack<ID> stack = new Stack<>();
        Iterator<Vertex> iterator = getVertexIterator();
        while (iterator.hasNext()) {
            Vertex vertex = iterator.next();
            if (!vertex.isVisited()) {
                topologicalSortRecursiveFunction(vertex.getId(), stack);
            }
        }

        List<Vertex> topologicalOrder = new ArrayList<>();
        while (!stack.isEmpty()) {
            topologicalOrder.add(getVertex(stack.pop()));
        }
        return topologicalOrder;
    }

    /**
     * dfs实现的拓扑排序递归函数
     *
     * @param vertexId 当前顶点ID
     * @param stack    存储拓扑序的栈
     */
    private void topologicalSortRecursiveFunction(ID vertexId, Stack<ID> stack) {
        Vertex vertex = getVertex(vertexId);
        vertex.visit();
        vertex.forEachNeighborVertex(neighbor -> {
            if (!neighbor.isVisited()) {
                topologicalSortRecursiveFunction(neighbor.getId(), stack);
            }
        });
        stack.push(vertex.getId());
    }

    /**
     * low[x]表示节点x通过DFS树中的边或回边（back edge）能够回溯到的最早的节点的时间戳
     * 换句话说，它是节点x所在子树中所有节点（包括通过非树边能够到达的节点）的dfn值中的最小值
     */
    private Map<ID, Integer> low;

    /**
     * dfn[x]表示节点x在DFS过程中被首次访问的时间戳（或称为DFS序）。
     * 时间戳是在DFS遍历过程中，按照节点被访问的先后顺序赋予的一个唯一标识
     */
    private Map<ID, Integer> dfn;

    /**
     * 寻找图中所有的桥（割边）
     * 割边是连接图中两个不同连通分量的唯一路径上的边
     *
     * @return 返回桥的集合，如果不存在桥，则返回空集合
     */
    public List<Edge> findBridge() {
        if (this.isDirect) {
            throw new RuntimeException("求割边只适用于无向图, 但你的图是有向图");
        }

        low = new HashMap<>(this.vertexCount);
        dfn = new HashMap<>(this.vertexCount);
        List<Edge> bridges = new ArrayList<>();
        resetAllVisit();

        // 初始化 DFN 值
        int time = 0;

        Iterator<Vertex> iterator = getVertexIterator();
        while (iterator.hasNext()) {
            Vertex vertex = iterator.next();
            if (!vertex.isVisited()) {
                tarjan(bridges, vertex, null, time); // 初始调用时，没有父节点
            }
        }
        return bridges;
    }

    private void tarjan(List<Edge> bridges, Vertex cur, Vertex parent, int time) {
        cur.visit();
        ++time;
        low.put(cur.getId(), time);
        dfn.put(cur.getId(), time);

        int finalTime = time;
        cur.forEachNeighborEdge(edge -> {
            Vertex neighbor = edge.getEndVertex();
            if (!neighbor.isVisited()) {
                // 发现未访问的邻居，递归访问
                tarjan(bridges, neighbor, cur, finalTime); // 传递当前节点作为父节点
                low.put(cur.getId(), Math.min(low.get(cur.getId()), low.get(neighbor.getId())));

                // 如果邻居的 low 值大于当前节点的 DFN，则当前边是割边
                if (low.get(neighbor.getId()) > dfn.get(cur.getId())) {
                    bridges.add(edge);
                }
            } else if (neighbor != parent) {
                // 如果邻居节点已访问但不是父节点，则更新 low 值
                low.put(cur.getId(), Math.min(low.get(cur.getId()), dfn.get(neighbor.getId())));
            }
        });
    }

    // TODO 最小瓶颈路/LCA/Tarjan强连通分量/欧拉回路/哈密尔顿路径/最大流/最小费用最大流/KM算法二分图最大带权匹配

    /**
     * 顶点
     */
    public class Vertex {

        /**
         * 顶点的标识符
         */
        private final ID id;

        /**
         * 点权
         */
        private V value;

        /**
         * 这个定点对应的边<br>
         * 如果为有向图，则代表以这个定点为起点的边
         */
        private final List<Edge> edgeList;

        /**
         * 可以判断是否已被访问<br>
         * 在dfs和bfs中会被用到
         */
        private boolean visited;

        /**
         * 该顶点的前驱<br>
         * 在求图中某两个顶点之间的最短路径时，在从起始顶点遍历过程中，需要记录下遍历到某个顶点时的前驱顶点
         */
        private Vertex previous;

        public Vertex(ID id, V value) {
            this.id = id;
            this.value = value;
            this.edgeList = new LinkedList<>();
            this.visited = false;
            this.previous = null;
        }

        // 下面与顶点本身相关

        /**
         * 获取顶点标识符
         *
         * @return 返回id
         */
        public ID getId() {
            return this.id;
        }

        /**
         * 遍历该顶点的所有邻接顶点，并执行逻辑
         *
         * @param consumer 具体执行的逻辑
         */
        public void forEachNeighborVertex(Consumer<Vertex> consumer) {
            Iterator<Edge> iterator = getEdgeIterator();
            Edge edge;
            Vertex vertex;
            while (iterator.hasNext()) {
                edge = iterator.next();
                vertex = edge.getEndVertex();
                consumer.accept(vertex);
            }
        }

        /**
         * 遍历该顶点所有相邻的边，并执行逻辑
         *
         * @param consumer 具体执行的逻辑
         */
        public void forEachNeighborEdge(Consumer<Edge> consumer) {
            Iterator<Edge> iterator = getEdgeIterator();
            Edge edge;
            while (iterator.hasNext()) {
                edge = iterator.next();
                consumer.accept(edge);
            }
        }

        // 下面与顶点的边相关

        /**
         * 返回边的迭代器
         */
        public Iterator<Edge> getEdgeIterator() {
            return edgeList.iterator();
        }

        /**
         * 返回以这个顶点为出发点的边数
         */
        public int getEdgeCount() {
            return edgeList.size();
        }

        /**
         * 将这个顶点与end顶点连接，边的权值为weight
         *
         * @param end    end顶点
         * @param weight 边权
         * @return 如果顶点已经与end连接，那么将会更新权值，返回false<br>
         * 如果顶点没有与endVertex相连，则互相连接，返回true
         */
        public boolean connect(Vertex end, double weight) {
            Iterator<Edge> iterator = getEdgeIterator();
            Edge edge;
            Vertex vertex;
            while (iterator.hasNext()) {
                edge = iterator.next();
                vertex = edge.getEndVertex();
                if (vertex.equals(end)) {
                    // 如果顶点已经与end连接，那么将会更新权值，返回false
                    edge.setWeight(weight);
                    return false;
                }
            }
            // 如果顶点没有与end相连，则互相连接，返回true
            edgeList.add(new Edge(this.getId(), end.getId(), weight));
            return true;
        }

        /**
         * 将这个顶点与end连接的边删除
         *
         * @param end end顶点
         * @return 如果顶点已经与end连接，那么将会删除这条边，返回true<br>
         * 如果顶点没有与endVertex连接，则什么都不做，返回false
         */
        public boolean disconnect(Vertex end) {
            Iterator<Edge> iterator = getEdgeIterator();
            Edge edge;
            Vertex vertex;
            while (iterator.hasNext()) {
                edge = iterator.next();
                vertex = edge.getEndVertex();
                if (vertex.equals(end)) {
                    // 如果顶点已经与end连接，那么将会删除这条边，返回true
                    iterator.remove();
                    return true;
                }
            }
            // 如果顶点没有与end连接，则什么都不做，返回false
            return false;
        }

        /**
         * 返回是否有以这个顶点为出发点,以end为结束点的边
         *
         * @param end end顶点
         * @return 如果有，返回那条边<br>
         * 如果没有，返回null
         */
        public Edge isConnected(Vertex end) {
            Iterator<Edge> iterator = getEdgeIterator();
            Edge edge;
            Vertex vertex;
            while (iterator.hasNext()) {
                edge = iterator.next();
                vertex = edge.getEndVertex();
                if (vertex.equals(end)) {
                    // 如果顶点已经与end连接，那么将返回这个边
                    return edge;
                }
            }
            // 没有则返回null
            return null;
        }

        // 下面是与顶点是否被访问相关

        /**
         * 判断顶点是否被访问
         *
         * @return 若顶点已被访问，返回true<br>
         * 否则返回false
         */
        public boolean isVisited() {
            return this.visited;
        }

        /**
         * 访问这个顶点
         */
        public void visit() {
            this.visited = true;
        }

        /**
         * 清除访问状态
         */
        public void unVisit() {
            this.visited = false;
        }

        /**
         * 获得以这个顶点为出发点，相邻的第一个没有被访问的顶点
         *
         * @return 如果没有，返回null<br>
         * 如果有，返回对应的顶点
         */
        public Vertex getUnVisitedVertex() {
            Iterator<Edge> iterator = getEdgeIterator();
            Edge edge;
            Vertex vertex;
            while (iterator.hasNext()) {
                edge = iterator.next();
                vertex = edge.getEndVertex();
                if (!vertex.isVisited()) {
                    return vertex;
                }
            }
            // 没有则返回null
            return null;
        }

        // 下面与前驱顶点相关

        /**
         * 返回顶点的前驱顶点
         *
         * @return 前驱顶点
         */
        public Vertex getPrevious() {
            return this.previous;
        }

        /**
         * 设置顶点的前驱顶点
         *
         * @param previous 前驱顶点
         */
        public void setPrevious(Vertex previous) {
            this.previous = previous;
        }

        // 下面与顶点的权值相关

        /**
         * 获取点权
         *
         * @return 点权
         */
        public V getValue() {
            return this.value;
        }

        /**
         * 设置点权
         *
         * @param value 点权
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return visited == vertex.visited &&
                    Objects.equals(id, vertex.id) &&
                    Objects.equals(value, vertex.value) &&
                    Objects.equals(edgeList, vertex.edgeList) &&
                    Objects.equals(previous, vertex.previous);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, value, edgeList, visited, previous);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "id=" + id +
                    '}';
        }
    }

    /**
     * 边
     */
    public final class Edge implements Comparable<Edge> {

        /**
         * 起始点
         */
        private final ID begin;

        /**
         * 结束点
         */
        private final ID end;

        /**
         * 边权
         */
        private double weight;

        /**
         * 边权最大值
         */
        public static final double INF = Double.MAX_VALUE;

        public Edge(ID end, double weight) {
            this.begin = null;
            this.end = end;
            this.weight = weight;
        }

        public Edge(ID begin, ID end, double weight) {
            this.begin = begin;
            this.end = end;
            this.weight = weight;
        }

        public ID getBegin() {
            return begin;
        }

        public ID getEnd() {
            return end;
        }

        public Vertex getBeginVertex() {
            return getVertex(begin);
        }

        public Vertex getEndVertex() {
            return getVertex(end);
        }

        public double getWeight() {
            return this.weight;
        }

        /**
         * 设置边权
         *
         * @param weight 边权
         */
        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "begin=" + begin +
                    ", end=" + end +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**
     * 简单路径
     */
    public class SimplePath {

        /**
         * 路径权重=路径上的边权之和
         */
        private final double weight;

        /**
         * 路径所包含的顶点集合
         */
        private final List<Vertex> vertices;

        public SimplePath(double weight, List<Vertex> vertices) {
            this.weight = weight;
            this.vertices = vertices;
        }

        public double getWeight() {
            return weight;
        }

        public List<Vertex> getVertices() {
            return vertices;
        }

        /**
         * 返回路径起点
         *
         * @return 若存在起点，返回起点<br>
         * 否则返回null
         */
        public Vertex getSource() {
            if (this.vertices == null || this.vertices.isEmpty()) {
                return null;
            }
            return this.vertices.get(0);
        }

        /**
         * 返回路径终点
         *
         * @return 若存在终点，返回终点<br>
         * 否则返回null
         */
        public Vertex getTarget() {
            if (this.vertices == null || this.vertices.isEmpty()) {
                return null;
            }
            return this.vertices.get(this.vertices.size() - 1);
        }

        @Override
        public String toString() {
            return "SimplePath{" +
                    "weight=" + (weight == Edge.INF ? "INF" : weight) +
                    ", vertices=" + vertices.toString() +
                    '}';
        }
    }

    /**
     * 过滤出所有同一起点的路径
     *
     * @param paths 路径集合
     * @param begin 起点
     * @return 不存在返回空集合
     */
    private List<SimplePath> beginWith(List<SimplePath> paths, ID begin) {
        if (paths == null || paths.isEmpty()) {
            return Collections.emptyList();
        }
        return paths.stream()
                .filter(path -> path.getSource().getId().equals(begin))
                .toList();
    }

    /**
     * 过滤出所有同一终点的路径
     *
     * @param paths 路径集合
     * @param end   终点
     * @return 不存在返回空集合
     */
    private List<SimplePath> endWith(List<SimplePath> paths, ID end) {
        if (paths == null || paths.isEmpty()) {
            return Collections.emptyList();
        }
        return paths.stream()
                .filter(path -> path.getTarget().getId().equals(end))
                .toList();
    }

    // 下面与重写Object方法相关

    /**
     * 打印图的概况，所有顶点，所有边
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("图概况:\n");
        sb.append("  有向性: ").append(this.isDirect ? "有向图" : "无向图").append("\n");
        sb.append("  顶点个数: ").append(this.vertexCount).append("\n");
        sb.append("  边个数: ").append(this.edgeCount).append("\n");
        sb.append("邻接表:\n");

        Iterator<Vertex> iterator = getVertexIterator();
        while (iterator.hasNext()) {
            Vertex cur = iterator.next();
            sb.append("  顶点 ").append(cur.getId()).append(this.isDirect ? " -> " : " <-> ");
            StringBuilder neighborsSb = new StringBuilder();
            cur.forEachNeighborVertex(neighbor -> {
                if (!neighborsSb.isEmpty()) {
                    neighborsSb.append(", ");
                }
                neighborsSb.append(neighbor.getId());
            });

            sb.append(neighborsSb).append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph<?, ?> graph = (Graph<?, ?>) o;
        return vertexCount == graph.vertexCount &&
                edgeCount == graph.edgeCount &&
                isDirect == graph.isDirect &&
                Objects.equals(vertexMap, graph.vertexMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexCount, edgeCount, isDirect, vertexMap);
    }

    // 下面与建造者模式相关

    public static <ID, V> GraphBuilder<ID, V> builder() {
        return new GraphBuilder<>();
    }

    public static class GraphBuilder<ID, V> {

        private final Graph<ID, V> workpiece;

        /**
         * 是否设置了有向性
         */
        private boolean setDirected;

        public GraphBuilder() {
            this.workpiece = new Graph<>();
            this.setDirected = false;
        }

        @Deprecated
        public GraphBuilder<ID, V> isDirect(boolean isDirect) {
            if (this.setDirected) {
                throw new RuntimeException("有向性只能设置一次");
            }
            this.workpiece.setDirect(isDirect);
            this.setDirected = true;
            return this;
        }

        public GraphBuilder<ID, V> directed() {
            if (this.setDirected) {
                throw new RuntimeException("有向性只能设置一次");
            }
            this.workpiece.setDirect(true);
            this.setDirected = true;
            return this;
        }

        public GraphBuilder<ID, V> unDirected() {
            if (this.setDirected) {
                throw new RuntimeException("有向性只能设置一次");
            }
            this.workpiece.setDirect(false);
            this.setDirected = true;
            return this;
        }

        public GraphBuilder<ID, V> addVertex(ID id, V value) {
            this.workpiece.addVertex(id, value);
            return this;
        }

        public GraphBuilder<ID, V> addEdge(ID begin, ID end, double weight) {
            this.workpiece.addEdge(begin, end, weight);
            return this;
        }

        public GraphBuilder<ID, V> addEdge(ID begin, ID end) {
            this.workpiece.addEdge(begin, end, 0.0);
            return this;
        }

        public Graph<ID, V> build() {
            return this.workpiece;
        }
    }

}
