package com.ricky.stream.api.test.utils;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className PrintUtils
 * @desc
 */
public class PrintUtils {

    private PrintUtils() {
    }

    /**
     * 按指定格式打印流
     *
     * @param msg     提示信息
     * @param stream  要打印的流
     * @param gap     间隔符
     * @param newLine 是否换行
     */
    public static <T> void printStreamElements(String msg, Stream<T> stream, String gap, boolean newLine) {
        StringBuilder sb = new StringBuilder();

        stream.forEach(element -> {
            if (!sb.isEmpty()) {
                sb.append(gap);
            }
            sb.append(element.toString());
        });

        System.out.print(msg + " : " + sb);

        if (newLine) {
            System.out.println();
        }
    }

    /**
     * 打印流
     *
     * @param msg    提示信息
     * @param stream 要打印的流
     */
    public static <T> void printStreamElements(String msg, Stream<T> stream) {
        printStreamElements(msg, stream, " ", true);
    }

    /**
     * 打印流
     *
     * @param msg    提示信息
     * @param stream 要打印的流
     */
    public static void printStreamElements(String msg, IntStream stream) {
        StringBuilder sb = new StringBuilder();

        stream.forEach(element -> {
            if (!sb.isEmpty()) {
                sb.append(" ");
            }
            sb.append(element);
        });

        System.out.println(msg + " : " + sb);
    }

    /**
     * 打印列表
     *
     * @param msg     提示信息
     * @param list    列表
     * @param gap     间隔符
     * @param newLine 是否换行
     */
    public static <T> void printListElements(String msg, List<T> list, String gap, boolean newLine) {
        printStreamElements(msg, list.stream(), gap, newLine);
    }

    /**
     * 打印列表
     *
     * @param msg  提示信息
     * @param list 列表
     */
    public static <T> void printListElements(String msg, List<T> list) {
        printListElements(msg, list, " ", true);
    }

}
