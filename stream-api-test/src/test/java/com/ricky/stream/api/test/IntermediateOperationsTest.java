package com.ricky.stream.api.test;

import com.ricky.stream.api.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className IntermediateOperationsTest
 * @desc 中间操作
 */
public class IntermediateOperationsTest {

    private static List<Person> people;

    @BeforeAll
    public static void init() {
        people = List.of(
                new Person("Neo", 45, "USA"),
                new Person("Stan", 10, "USA"),
                new Person("Grace", 16, "UK"),
                new Person("Alex", 20, "UK"),
                new Person("Alex", 20, "UK"),
                new Person("Sebastian", 40, "FR"),
                new Person("Sebastian", 40, "FR")
        );
    }

    @Test
    public void filter() {
        // When
        List<Person> list = people.stream()
                .filter(person -> person.getAge() > 18)
                .toList();

        // Then
        list.forEach(System.out::println);
    }

    @Test
    public void distinct() {
        // When
        List<Person> list = people.stream()
                .distinct() // 需要 equals 和 hashCode
                .toList();

        // Then
        list.forEach(System.out::println);
    }

    @Test
    public void limit() {
        // When
        List<Person> list = people.stream()
                .limit(2)
                .toList();

        // Then
        list.forEach(System.out::println);
    }

    @Test
    public void skip() {
        // When
        List<Person> list = people.stream()
                .skip(2)
                .toList();

        // Then
        list.forEach(System.out::println);
    }

    @Test
    public void map() {
        // When
        List<String> names = people.stream()
                .map(Person::getName)
                .toList();

        // Then
        names.forEach(System.out::println);
    }

    @Test
    public void flatMap() {
        // Given
        List<List<Person>> peopleGroups = List.of(
                List.of(
                        new Person("Neo", 45, "USA"),
                        new Person("Stan", 10, "USA")
                ),
                List.of(
                        new Person("Grace", 16, "UK"),
                        new Person("Alex", 20, "UK")
                ),
                List.of(
                        new Person("Sebastian", 40, "FR")
                )
        );

        // When
        // 嵌套变扁平，多层流变单层流
        List<String> names = peopleGroups.stream()
                .flatMap(people -> people.stream().map(Person::getName))
                .toList();

        // Then
        names.forEach(System.out::println);
    }

    @Test
    @DisplayName("Sort by age in reverse order")
    public void sort() {
        // When
        List<Person> list = people.stream()
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .toList();

        // Then
        list.forEach(System.out::println);
    }

}

