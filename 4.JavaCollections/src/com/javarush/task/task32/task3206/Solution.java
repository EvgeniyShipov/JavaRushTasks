package com.javarush.task.task32.task3206;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/* 
Дженерики для создания прокси-объекта
*/
public class Solution<T extends Item> {

    public static void main(String[] args) {
        Solution solution = new Solution();
        test(solution.getProxy(Item.class));                        //true false false
        test(solution.getProxy(Item.class, Small.class));           //true false true
        test(solution.getProxy(Item.class, Big.class, Small.class));//true true true
        test(solution.getProxy(Big.class, Small.class));            //true true true т.к. Big наследуется от Item
        test(solution.getProxy(Big.class));                         //true true false т.к. Big наследуется от Item
    }

    public T getProxy(Class<T> item, Class<?>... itemClasses) {
        Class<?>[] classes = Arrays.copyOf(itemClasses, itemClasses.length + 1);
        classes[classes.length - 1] = item;
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), classes, new ItemInvocationHandler());
    }


    private static void test(Object proxy) {
        boolean isItem = proxy instanceof Item;
        boolean isBig = proxy instanceof Big;
        boolean isSmall = proxy instanceof Small;

        System.out.format("%b %b %b\n", isItem, isBig, isSmall);
    }
}