package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        FileClassLoader fileClassLoader = new FileClassLoader();
        Arrays.stream(new File(packageName).listFiles())
                .filter(file -> file.getName().endsWith(".class"))
                .forEach(file -> {
                    Class<?> clazz = fileClassLoader.loadClass(file.toPath());
                    if (clazz != null)
                        hiddenClasses.add(clazz);
                });
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        return hiddenClasses.stream()
                .filter(clazz -> clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase()))
                .findFirst()
                .map(this::getInstance)
                .orElse(null);
    }

    private HiddenClass getInstance(Class clazz) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (HiddenClass) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class FileClassLoader extends ClassLoader {
        protected Class<?> loadClass(Path path) {
            try {
                byte[] bytes = Files.readAllBytes(path);
                return defineClass(null, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}

