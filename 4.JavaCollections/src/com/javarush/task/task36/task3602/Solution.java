package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        return Arrays.stream(Collections.class.getDeclaredClasses())
                .filter(clazz -> Modifier.isPrivate(clazz.getModifiers()))
                .filter(clazz -> Modifier.isStatic(clazz.getModifiers()))
                .filter(Solution::isImplementList)
                .filter(Solution::isThrowIOOBException)
                .findAny()
                .orElse(null);
    }

    private static boolean isImplementList(Class clazz) {
        return Arrays.stream(clazz.getInterfaces()).anyMatch(cl -> cl.equals(List.class) ||
                Arrays.stream(clazz.getSuperclass().getInterfaces()).anyMatch(cl2 -> cl2.equals(List.class)));
    }

    private static boolean isThrowIOOBException(Class clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            List list = (List) constructor.newInstance();
            list.get(0);
            return false;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }
}
