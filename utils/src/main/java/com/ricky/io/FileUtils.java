package com.ricky.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/13
 * @className FileUtils
 * @desc
 */
@Slf4j
public class FileUtils {

    private FileUtils() {
    }

    /**
     * 重命名给定文件夹路径下的文件
     *
     * @param directoryPath 文件夹路径
     * @param function      重命名逻辑
     */
    public static int renameFiles(String directoryPath, Function<String, String> function) {
        File folder = new File(directoryPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new RuntimeException("给定的路径不是一个有效的文件夹");
        }

        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("文件夹为空");
            return 0;
        }

        int count = 0;
        for (File file : files) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                String newName = function.apply(fileName);
                if (newName.equals(fileName)) {
                    continue;
                }
                boolean success = file.renameTo(new File(directoryPath + File.separator + newName));
                ++count;
                if (success) {
                    System.out.println(file.getName() + " => " + newName);
                } else {
                    System.out.println("重命名 " + file.getName() + " 到 " + newName + " 失败");
                }
            }
        }
        return count;
    }

    /**
     * 递归解压指定目录下的所有.zip文件到它们的同级目录
     *
     * @param directoryPath 需要解压ZIP文件的目录路径
     * @throws IOException 如果在文件操作中发生错误
     */
    public static void unzipAllInDirectory(String directoryPath, boolean deleteZip) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是目录，则递归调用
                    unzipAllInDirectory(file.getAbsolutePath(), deleteZip);
                } else if (file.getName().endsWith(".zip")) {
                    // 如果是ZIP文件，则解压
                    unzip(file, file.getParent(), deleteZip);
                }
            }
        }
    }

    /**
     * 解压单个ZIP文件到指定目录
     *
     * @param zipFile ZIP文件
     * @param destDir 解压到的目标目录
     * @throws IOException 如果在文件操作中发生错误
     */
    private static void unzip(File zipFile, String destDir, boolean deleteZip) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();

        // 创建解压到的目录
        File destinationDir = new File(destDir + File.separator + zipFile.getName().substring(0, zipFile.getName().lastIndexOf('.')));
        if (!destinationDir.exists()) {
            destinationDir.mkdir();
        }

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String filePath = destinationDir.getAbsolutePath() + File.separator + entry.getName();

            if (!entry.isDirectory()) {
                // 如果不是目录，则提取文件
                extractFile(zip, entry, filePath);
            } else {
                // 如果是目录，则创建目录
                File dir = new File(filePath);
                dir.mkdir();
            }
        }

        zip.close();

        if (deleteZip) {
            zipFile.delete();
        }
    }

    /**
     * 从ZIP文件中提取文件
     *
     * @param zip      ZIP文件
     * @param zipEntry ZIP条目
     * @param filePath 提取到的文件路径
     * @throws IOException 如果在文件操作中发生错误
     */
    private static void extractFile(ZipFile zip, ZipEntry zipEntry, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            InputStream zis = zip.getInputStream(zipEntry);
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zis.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
