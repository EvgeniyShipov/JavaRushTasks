package com.javarush.task.task31.task3113;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/* 
Что внутри папки?
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Path path = Paths.get(scanner.nextLine());
        if (!Files.isDirectory(path))
            System.out.println(path.toAbsolutePath() + " - не папка");
        else {
            long countDirectories = Files.walk(path).filter(p -> Files.isDirectory(p)).count() - 1;
            long countFiles = Files.walk(path).filter(p -> Files.isRegularFile(p)).count();
            long size = Files.walk(path).filter(p -> Files.isRegularFile(p)).mapToLong(p -> {
                try {
                    return Files.size(p);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }).reduce((a, b) -> a + b)
                    .orElse(0);
            System.out.println("Всего папок - " + countDirectories);
            System.out.println("Всего файлов - " + countFiles);
            System.out.println("Общий размер - " + size);
        }
    }
}
