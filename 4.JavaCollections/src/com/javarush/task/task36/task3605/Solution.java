package com.javarush.task.task36.task3605;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.stream.IntStream;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        TreeSet<Character> letters = new TreeSet<>();

        Files.readAllLines(Paths.get(args[0])).stream()
                .map(String::toLowerCase)
                .map(String::chars)
                .flatMap(IntStream::boxed)
                .map(c -> (char) c.byteValue())
                .filter(Character::isLetter)
                .forEach(letters::add);

        letters.stream()
                .limit(5)
                .forEach(System.out::print);
    }
}