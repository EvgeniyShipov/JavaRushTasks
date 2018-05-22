package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        HashSet<Animal> animals = new HashSet<>();
        FileClassLoader fileClassLoader = new FileClassLoader();

        Arrays.stream(Objects.requireNonNull(new File(pathToAnimals).listFiles()))
                .filter(file -> file.getName().endsWith(".class"))
                .forEach(file -> {
                    try {
                        Class<?> clazz = fileClassLoader.loadClass(file.toPath());
                        if (Arrays.stream(clazz.getInterfaces()).anyMatch(cl -> cl.equals(Animal.class))) {
                            Arrays.stream(clazz.getConstructors())
                                    .filter(constructor -> constructor.getParameterCount() == 0)
                                    .findFirst()
                                    .ifPresent(constructor -> {
                                        try {
                                            animals.add((Animal) constructor.newInstance());
                                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return animals;
    }

    static class FileClassLoader extends ClassLoader {
        protected Class<?> loadClass(Path path) throws IOException {
            byte[] bytes = Files.readAllBytes(path);
            Class<?> clazz = defineClass(null, bytes, 0, bytes.length);
            resolveClass(clazz);
            return clazz;
        }
    }
}
