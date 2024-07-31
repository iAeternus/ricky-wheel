package com.ricky.stream.api.test;

import com.ricky.stream.api.test.utils.PrintUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className StreamCreationTest
 * @desc 创建流
 */
public class StreamCreationTest {

    @Test
    @DisplayName("basic method to construct a stream")
    public void basicConstruct() {
        // Given
        List<String> list = List.of("a", "b", "c");
        String[] array = {"a", "b", "c"};

        // When
        Stream<String> listStream = list.stream();
        Stream<String> arrayStream = Arrays.stream(array);

        // Then
        PrintUtils.printStreamElements("list", listStream);
        PrintUtils.printStreamElements("array", arrayStream);
    }

    @Test
    @DisplayName("Stream.concat")
    public void concat() {
        // Given
        Stream<String> stream1 = Stream.of("a", "b", "c");
        Stream<String> stream2 = Stream.of("d", "e", "f");

        // When
        Stream<String> result = Stream.concat(stream1, stream2);

        // Then=
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("Stream.Builder")
    public void streamBuilder() {
        // Given
        Stream.Builder<Object> streamBuilder = Stream.builder();

        // When
        streamBuilder.add("a");
        streamBuilder.add("b");
        if (Math.random() > 0.5) {
            streamBuilder.add("c");
        }

        // 调用build方法后就不能添加元素了，否则会抛IllegalStateException
        Stream<Object> stream = streamBuilder.build();

        // Then
        stream.forEach(System.out::println);
    }

    @Test
    @DisplayName("Files.lines")
    public void fileLineToStream() {
        // Given
        Path path = Paths.get("src/test/resources/file.txt");

        // Then
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("IntStream")
    public void intStream() {
        // Given
        IntStream stream1 = IntStream.of(1, 2, 3);
        IntStream stream2 = IntStream.range(1, 4); // [1, 4)
        IntStream stream3 = IntStream.rangeClosed(1, 4); // [1, 4]
        Stream<Integer> stream4 = IntStream.of(1, 2, 3).boxed();

        // Then
        PrintUtils.printStreamElements("stream1", stream1);
        PrintUtils.printStreamElements("stream2", stream2);
        PrintUtils.printStreamElements("stream3", stream3);
        PrintUtils.printStreamElements("stream4", stream4);
    }

    @Test
    public void generate() {
        // Given
        Stream<String> stream1 = Stream.generate(() -> "Ricky").limit(5);
        Stream<Double> stream2 = Stream.generate(Math::random).limit(5);

        // Then
        PrintUtils.printStreamElements("stream1", stream1);
        PrintUtils.printStreamElements("stream2", stream2);
    }

    @Test
    public void iterate() {
        // Given
        Stream<Integer> stream1 = Stream.iterate(0, n -> n + 2).limit(10);
        Stream<Integer> stream2 = Stream.iterate(0, n -> n <= 10, n -> n + 2);

        // Then
        PrintUtils.printStreamElements("stream1", stream1);
        PrintUtils.printStreamElements("stream2", stream2);
    }

    @Test
    public void parallelStream() {
        List.of("a, b, c").parallelStream().forEach(System.out::println);
    }

}
