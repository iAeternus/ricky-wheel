package com.ricky.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/13
 * @className FileUtilsTest
 * @desc
 */
class FileUtilsTest {

    @Test
    public void renameFiles() {
        // Given
        String folderPath = "";

        // When
        int count = FileUtils.renameFiles(folderPath, fileName -> {
            if (fileName.contains("(1)")) {
                String s = fileName.split("_")[1];
                String begin = s.substring(0, 2);
                String end = s.substring(s.length() - 4);
                return begin + end;
            } else {
                return fileName.substring(fileName.length() - 6);
            }
        });
        System.out.println("成功修改了" + count + "个文件");
    }

    @Test
    public void unzipAllInDirectory() throws IOException {
        // Given
        String directoryPath = "F:\\develop\\ricky-wheel\\utils\\src\\test\\resources\\a";

        // When
        FileUtils.unzipAllInDirectory(directoryPath, true);
    }

}