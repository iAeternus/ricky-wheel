package com.ricky.ac;

import java.util.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/5
 * @className ACTrie
 * @desc AC自动机字典树
 */
public class ACTrie {

    /**
     * 是否建立的failure表
     */
    private boolean failureStatesConstructed = false;

    /**
     * 是否忽略大小写
     */
    private boolean ignoreCase = false;

    /**
     * 禁止出现交叉 TODO
     */
    private boolean ignoreOverlaps = false;

    /**
     * 英文全字匹配 TODO
     */
    private boolean onlyWholeWords = false;

    /**
     * Trie树根节点
     */
    private final Node root;

    public ACTrie() {
        this.root = new Node(true);
    }

    public void ignoreCase() {
        this.ignoreCase = true;
    }

    public void ignoreOverlaps() {
        this.ignoreOverlaps = true;
    }

    public void onlyWholeWords() {
        this.onlyWholeWords = true;
    }

    /**
     * Trie树节点
     */
    private static class Node {

        /**
         * 当前节点的所有子节点
         */
        private final Map<Character, Node> children;

        /**
         * 当前节点包含的所有模式串
         */
        private final List<String> pattenStrings;

        /**
         * fail指针指向的Node
         */
        private Node failure;

        /**
         * 是否是根节点
         */
        private boolean isRoot = false;

        public Node() {
            this.children = new HashMap<>();
            this.pattenStrings = new ArrayList<>();
        }

        public Node(boolean isRoot) {
            this();
            this.isRoot = isRoot;
        }

        /**
         * 若给定字符存在于子节点中，不做任何操作
         * 否则新建一个Node
         *
         * @param character 给定字符
         * @return 返回这个字符对应的节点
         */
        public Node insert(Character character) {
            Node node = this.find(character);
            if (node == null) {
                node = new Node();
                this.children.put(character, node);
            }
            return node;
        }

        /**
         * 添加模式串
         *
         * @param keyword 模式串
         */
        public void addPattenString(String keyword) {
            this.pattenStrings.add(keyword);
        }

        /**
         * 添加模式串
         *
         * @param keywords 模式串集合
         */
        public void addPattenString(Collection<String> keywords) {
            this.pattenStrings.addAll(keywords);
        }

        /**
         * 查找当前节点的子节点是否存在给定字符
         *
         * @param character 给定字符
         * @return 返回子节点。
         */
        public Node find(Character character) {
            return this.children.get(character);
        }

        /**
         * 根据给定的transition，确定当前节点在AC自动机中的下一个状态
         * 如果没有直接的子节点匹配给定的转移字符，则利用fail node来递归查找
         *
         * @param transition 转移字符
         * @return 返回给定转移字符对应的下一个状态节点 <br>
         * 如果当前节点或其失败链上的节点有匹配的子节点，则返回该子节点 <br>
         * 如果找不到匹配的子节点且当前节点是根节点，则返回根节点自身 <br>
         * 如果上述情况都不满足，则递归地在失败链上继续查找，直到找到匹配的子节点或达到根节点 <br>
         */
        private Node nextState(Character transition) {
            // 用于构建fail node时，这里的this是父节点的fail node
            // 首先从父节点的fail node的子节点里找有没有值和当前失败节点的char值相同的
            Node state = this.find(transition);

            // 如果找到了这样的节点，那么该节点就是当前失败位置节点的fail node
            if (state != null) {
                return state;
            }

            // 如果没有找到这样的节点，而父节点的fail node又是root，那么返回root作为当前失败位置节点的fail node
            if (this.isRoot) {
                return this;
            }

            // 如果上述两种情况都不满足，那么就对父节点的fail node的fail node再重复上述过程，直到找到为止
            // 这个地方借鉴了KMP算法里面求解next列表的思想
            return this.failure.nextState(transition);
        }

        public Collection<Node> children() {
            return this.children.values();
        }

        public void setFailure(Node node) {
            this.failure = node;
        }

        public Node getFailure() {
            return this.failure;
        }

        /**
         * 返回所有子节点的键值，也就是每个子节点上存储的char
         *
         * @return set
         */
        public Set<Character> getTransitions() {
            return this.children.keySet();
        }

        public Collection<String> pattenStrings() {
            return this.pattenStrings == null ? Collections.emptyList() : this.pattenStrings;
        }
    }

    /**
     * 模式串
     */
    public static class PattenString {

        /**
         * 匹配到的模式串
         */
        private final String keyword;

        /**
         * 起点
         */
        private final int start;

        /**
         * 终点
         */
        private final int end;

        public PattenString(final String keyword, final int start, final int end) {
            this.keyword = keyword;
            this.start = start;
            this.end = end;
        }

        public String getKeyword() {
            return keyword;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PattenString that = (PattenString) o;
            return start == that.start && end == that.end && Objects.equals(keyword, that.keyword);
        }

        @Override
        public int hashCode() {
            return Objects.hash(keyword, start, end);
        }

        @Override
        public String toString() {
            return super.toString() + "=" + this.keyword;
        }
    }

    /**
     * 添加一个模式串
     *
     * @param keyword 模式串
     */
    public void addKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return;
        }

        Node currentState = this.root;
        // 添加模式串中每个字符到Trie数
        for (Character character : keyword.toCharArray()) {
            currentState = currentState.insert(handleCase(character));
        }
        // 添加模式串到叶子节点
        currentState.addPattenString(keyword);
    }

    /**
     * 添加若干模式串
     *
     * @param keywords 模式串列表
     */
    public void addKeywords(Collection<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return;
        }

        for (String keyword : keywords) {
            this.addKeyword(keyword);
        }
    }

    /**
     * 处理大小写
     *
     * @param character 字符
     * @return 如果设置了忽略大小写并且传入的是字母，则统一转为小写 <br>
     * 否则什么都不做
     */
    private Character handleCase(Character character) {
        if (ignoreCase && Character.isLetter(character)) {
            return Character.toLowerCase(character);
        }
        return character;
    }

    /**
     * 使用AC自动机做匹配
     *
     * @param text 文本串
     * @return 返回text中匹配到的所有patten以及在text中的起始位置
     */
    public Collection<PattenString> parseText(String text) {
        // 构建fail表，若已构建则跳过
        checkForConstructedFailureStates();
        // 处理文本串的大小写
        text = handleCase(text);

        Node currentState = this.root;
        List<PattenString> pattenStringList = new ArrayList<>();
        for (int position = 0; position < text.length(); ++position) {
            Character character = text.charAt(position);
            // 依次从子节点里找char，如果子节点没找到，就到子节点的fail node找，并返回最后找到的node；如果找不到就会返回root
            // 这一步同时也在更新currentState，如果找到了就更新currentState为找到的node，没找到currentState就更新为root，相当于又从头开始找
            currentState = currentState.nextState(character);
            Collection<String> pattenStrings = currentState.pattenStrings();
            if (pattenStrings == null || pattenStrings.isEmpty()) {
                continue;
            }
            // 如果找到的node的PattenString非空，说明有pattern被匹配到了
            for (String pattenString : pattenStrings) {
                pattenStringList.add(new PattenString(pattenString, position - pattenString.length() + 1, position));
            }
        }
        return pattenStringList;
    }

    /**
     * 检查是否建立了Fail表(若没建立，则建立)
     */
    private void checkForConstructedFailureStates() {
        if (!this.failureStatesConstructed) {
            constructFailureStates();
        }
    }

    /**
     * 建立fail表，BFS
     */
    private void constructFailureStates() {
        Queue<Node> queue = new LinkedList<>();

        // 首先从把root的子节点的fail node全设为root
        // 然后将root的所有子节点加到queue里面
        for (Node child : this.root.children()) {
            child.setFailure(this.root);
            queue.add(child);
        }

        this.failureStatesConstructed = true;

        while (!queue.isEmpty()) {
            Node parent = queue.poll();
            for (Character transition : parent.getTransitions()) {
                Node child = parent.find(transition);
                queue.add(child);
                Node failNode = parent.getFailure().nextState(transition);
                child.setFailure(failNode);

                // 每个节点处的PattenString要加上它的fail node处的PattenString
                // 因为能匹配到这个位置的话，那么fail node处的PattenString一定是匹配的pattern
                child.addPattenString(failNode.pattenStrings());
            }
        }
    }

    /**
     * 处理大小写
     *
     * @param text 文本串
     * @return 如果设置了忽略大小写，则转为小写字符串 <br>
     * 否则什么都不做
     */
    private String handleCase(String text) {
        return ignoreCase ? text.toLowerCase() : text;
    }

    /**
     * 将文本串中所有匹配的地方替换为与模式串等长的占位符
     *
     * @param text        文本串
     * @param placeholder 占位符
     * @return 返回处理后的文本串
     */
    public String modifyText(String text, Character placeholder) {
        Collection<PattenString> pattenStrings = parseText(text);
        for (PattenString pattenString : pattenStrings) {
            text = replaceSubstring(text, pattenString.start, pattenString.end, placeholder);
        }
        return text;
    }

    /**
     * 将文本串中所有匹配的模式串前面插入start，末尾插入end
     * 效果为 start + 模式串 + end
     * 注意本方法只有在设置了禁止出现交叉后才可以使用，否则抛出异常
     *
     * @param text  文本串
     * @param start 起始串
     * @param end   结尾串
     * @return 返回处理后的文本串
     */
    public String modifyText(String text, String start, String end) {
        if (!ignoreOverlaps) {
            throw new RuntimeException("IgnoreOverlaps is not configured.");
        }

        Collection<PattenString> pattenStrings = parseText(text);
        for (PattenString pattenString : pattenStrings) {
            text = aroundSubString(text, pattenString.start, start, pattenString.end, end);
        }
        return text;
    }

    /**
     * 替换文本串中从特定起始索引到结束索引之间 闭区间 的子串为与模式串等长的占位符
     *
     * @param text        原始字符串
     * @param startIndex  起始索引
     * @param endIndex    结束索引
     * @param placeholder 用来替换的子串
     * @return 返回处理后的文本串
     */
    private String replaceSubstring(String text, int startIndex, int endIndex, Character placeholder) {
        if (startIndex < 0 || endIndex > text.length() || startIndex > endIndex) {
            throw new IllegalArgumentException("Invalid start and/or end index.");
        }

        // 构造与模式串登长的替换串
        int patternLength = endIndex - startIndex + 1;
        String mask = String.valueOf(placeholder).repeat(Math.max(0, patternLength));

        String beforePart = text.substring(0, startIndex);
        String afterPart = text.substring(endIndex + 1);
        return beforePart + mask + afterPart;
    }

    /**
     * 在文本串的startIndex之前插入start，endIndex之后插入end
     * TODO 没有考虑文本串长度的变化 ！！！！！！！！！！！！！！！！！！！！！！！！！
     *
     * @param text       文本串
     * @param startIndex 起始索引
     * @param start      起始串
     * @param endIndex   结束索引
     * @param end        结尾串
     * @return 返回处理后的文本串
     */
    private String aroundSubString(String text, int startIndex, String start, int endIndex, String end) {
        if (startIndex < 0 || endIndex >= text.length() || startIndex > endIndex) {
            throw new IllegalArgumentException("Invalid start and/or end index.");
        }

        String beforeStart = text.substring(0, startIndex);
        String afterEnd = text.substring(endIndex + 1);
        return beforeStart + start + text.substring(startIndex, endIndex + 1) + end + afterEnd;
    }

    public static ACTrieBuilder builder() {
        return new ACTrieBuilder();
    }

    /**
     * 建造者模式
     */
    public static class ACTrieBuilder {

        private final ACTrie trie;

        public ACTrieBuilder() {
            trie = new ACTrie();
        }

        public ACTrieBuilder ignoreCase() {
            trie.ignoreCase();
            return this;
        }

        public ACTrieBuilder ignoreOverlaps() {
            trie.ignoreOverlaps();
            return this;
        }

        public ACTrieBuilder onlyWholeWords() {
            trie.onlyWholeWords();
            return this;
        }

        public ACTrieBuilder addKeyword(String keyword) {
            trie.addKeyword(keyword);
            return this;
        }

        public ACTrieBuilder addKeywords(Collection<String> keywords) {
            trie.addKeywords(keywords);
            return this;
        }

        public ACTrie build() {
            return this.trie;
        }

    }

}
