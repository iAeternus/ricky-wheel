package com.ricky.stream.api.test;

import com.ricky.stream.api.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className TerminalOperationsTest
 * @desc 终端操作
 */
public class TerminalOperationsTest {

    private static List<Person> people;
    private static List<String> characters;

    @BeforeAll
    public static void init() {
        people = List.of(
                new Person("Neo", 45, "USA"),
                new Person("Stan", 10, "USA"),
                new Person("Grace", 16, "UK"),
                new Person("Alex", 20, "UK"),
                new Person("Sebastian", 40, "FR")
        );
        characters = List.of("A", "B", "C", "D", "E", "F");
    }

    @Test
    public void anyMatch() {
        // When
        // 有满足条件的就返回true
        boolean result1 = people.stream()
                .anyMatch(person -> person.getAge() > 18);
        boolean result2 = people.stream()
                .anyMatch(person -> person.getAge() > 50);

        // Then
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void noneMatch() {
        // When
        // 没有满足条件的才返回true
        boolean result1 = people.stream()
                .noneMatch(person -> person.getAge() > 18);
        boolean result2 = people.stream()
                .noneMatch(person -> person.getAge() > 50);

        // Then
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void allMatch() {
        // When
        // 全部满足条件的才返回true
        boolean result = people.stream()
                .allMatch(person -> person.getAge() > 18);

        // Then
        System.out.println(result);
    }

    @Test
    public void fineFirst() {
        // When
        Optional<Person> first = people.stream()
                .findFirst();

        // Then
        first.ifPresent(System.out::println);
    }

    @Test
    public void aggregations() {
        // When
        long count = people.size();
        Optional<Person> maxAge = people.stream()
                .max(Comparator.comparing(Person::getAge));
        Optional<Person> minAge = people.stream()
                .min(Comparator.comparing(Person::getAge));

        int sum = people.stream()
                .mapToInt(Person::getAge)
                .sum();
        OptionalDouble average = people.stream()
                .mapToInt(Person::getAge)
                .average();

        // Then
        System.out.println(count);
        maxAge.ifPresent(System.out::println);
        minAge.ifPresent(System.out::println);
        System.out.println(sum);
        average.ifPresent(System.out::println);
    }

    @Test
    public void reduce() {
        // When
        int sum = people.stream()
                .mapToInt(Person::getAge)
                .reduce(0, Integer::sum);

        String joinedName = people.stream()
                .map(Person::getName)
                .reduce("", (a, b) -> a + b + ",");

        // Then
        System.out.println(sum);
        System.out.println(joinedName);
    }

    @Test
    public void collect() {
        // When
        List<Person> adults1 = people.stream()
                .filter(person -> person.getAge() > 18)
                .collect(Collectors.toList());

        Map<String, Integer> adults2 = people.stream()
                .filter(person -> person.getAge() > 18)
                .collect(Collectors.toMap(
                        Person::getName,
                        Person::getAge
                ));

        // Then
        System.out.println(adults1);
        System.out.println(adults2);

    }

    @Test
    public void groupingBy() {
        // When
        // 按国籍分组
        Map<String, List<Person>> peopleByCountry = people.stream()
                .collect(Collectors.groupingBy(Person::getCountry));

        // Then
        peopleByCountry.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    public void partitioningBy() {
        // When
        Map<Boolean, List<Person>> agePartition = people.stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() > 18));

        // Then
        agePartition.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    public void joining() {
        // When
        String joinedName = people.stream()
                .map(Person::getName)
                .collect(Collectors.joining(","));

        // Then
        System.out.println(joinedName);
    }

    @Test
    public void summarizingInt() {
        // When
        IntSummaryStatistics ageSummary = people.stream()
                .collect(Collectors.summarizingInt(Person::getAge));

        // Then
        System.out.println(ageSummary.getAverage());
        System.out.println(ageSummary.getMax());
        System.out.println(ageSummary.getMin());
        System.out.println(ageSummary.getCount());
    }

    @Test
    public void collector1() {
        // When
        ArrayList<Object> result = people.stream()
                .parallel()
                .collect(Collector.of(
                        ArrayList::new,
                        (list, person) -> {
                            System.out.println("Accumulator : " + person);
                            list.add(person);
                        },
                        (left, right) -> {
                            System.out.println("Combiner : " + left);
                            left.addAll(right);
                            return left;
                        },
                        Collector.Characteristics.IDENTITY_FINISH
                ));

        // Then
        System.out.println(result);
    }

    @Test
    public void collector2() {
        // When
        HashMap<String, List<Person>> result = people.stream()
                .parallel()
                .collect(Collector.of(
                        HashMap::new,
                        (map, person) -> {
                            System.out.println("Accumulator : " + person);
                            map.computeIfAbsent(person.getCountry(), k -> new ArrayList<>()).add(person);
                        },
                        (left, right) -> {
                            System.out.println("Combiner : "
                                    + System.lineSeparator() + "left : " + left
                                    + System.lineSeparator() + "right : " + right);
                            right.forEach((key, value) -> left.merge(key, value, (list, newList) -> {
                                list.addAll(newList);
                                return list;
                            }));
                            return left;
                        }
                ));

        // Then
        result.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    public void parallelStream1() {
        // When
        characters.parallelStream()
                .map(String::toLowerCase)
                .forEach(item -> {
                    System.out.println("Item: " + item + " -> " + " Thread: " + Thread.currentThread().getName());
                    System.out.println(item);
                });
    }

    @Test
    public void parallelStream2() {
        // When
        List<String> collect = characters.parallelStream()
                .map(String::toLowerCase)
                .collect(Collector.of(
                        () -> {
                            printThreadInfo("Supplier: new ArrayList");
                            return new ArrayList<>();
                        },
                        (list, item) -> {
                            printThreadInfo("Accumulator: " + item);
                            list.add(item);
                        },
                        (left, right) -> {
                            printThreadInfo("Combiner: " + left + " + " + right);
                            left.addAll(right);
                            return left;
                        },
                        Collector.Characteristics.IDENTITY_FINISH
                ));

        // Then
        System.out.println(collect);
    }

    @Test
    public void parallelStream3() {
        // When
        ConcurrentHashMap<String, String> collect = characters.parallelStream()
                .map(String::toLowerCase)
                .collect(Collector.of(
                        () -> {
                            printThreadInfo("Supplier: new ConcurrentHashMap");
                            return new ConcurrentHashMap<>();
                        },
                        (map, item) -> {
                            printThreadInfo("Accumulator: " + item);
                            map.put(item.toUpperCase(), item);
                        },
                        (left, right) -> {
                            printThreadInfo("Combiner: " + left + " + " + right);
                            left.putAll(right);
                            return left;
                        },
                        Collector.Characteristics.IDENTITY_FINISH,
                        Collector.Characteristics.CONCURRENT
                        // ,Collector.Characteristics.UNORDERED // 注释这一行，查看变化
                ));

        // Then
        System.out.println(collect);
    }

    @Test
    @DisplayName("compare thr speed")
    public void parallelStream4() {
        // Given
        List<Integer> nums = new ArrayList<>();
        int size = 50000000;
        for (int i = 0; i < size; i++) {
            nums.add(i);
        }

        // When
        long streamTime = measuringTime(() -> {
            String str = nums.stream()
                    .filter(i -> i % 2 == 0)
                    .map(String::valueOf)
                    .sorted()
                    .filter(s -> s.length() > 3)
                    .map(s -> s.length() + ":" + s)
                    .filter(s -> s.startsWith("5"))
                    .collect(Collectors.groupingBy(String::length, Collectors.toList()))
                    .values().stream()
                    .max(Comparator.comparingInt(List::size))
                    .orElse(Collections.emptyList())
                    .stream()
                    .findFirst()
                    .map(s -> "Final Result: " + s)
                    .orElse("No result found");
        });
        long parallelStreamTime = measuringTime(() -> {
            String str = nums.parallelStream()
                    .filter(i -> i % 2 == 0)
                    .map(String::valueOf)
                    .sorted()
                    .filter(s -> s.length() > 3)
                    .map(s -> s.length() + ":" + s)
                    .filter(s -> s.startsWith("5"))
                    .collect(Collectors.groupingBy(String::length, Collectors.toList()))
                    .values().stream()
                    .max(Comparator.comparingInt(List::size))
                    .orElse(Collections.emptyList())
                    .stream()
                    .findFirst()
                    .map(s -> "Final Result: " + s)
                    .orElse("No result found");
        });

        // Then
        System.out.println("Stream : " + streamTime + "ms");
        System.out.println("Parallel Stream : " + parallelStreamTime + "ms");
    }

    private void printThreadInfo(String msg) {
        System.out.println(msg + " Thread: " + Thread.currentThread().getName());
    }

    /**
     * 测量给定Runnable的执行时间（毫秒）
     *
     * @param runnable 要测量的Runnable任务
     * @return 执行时间（毫秒）
     */
    public long measuringTime(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();

        // 将nanoTime转换为毫秒
        return TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
    }

}






















