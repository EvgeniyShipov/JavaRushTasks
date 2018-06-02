package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.util.ArrayList;

public class VeryComplexClass {
    public void methodThrowsClassCastException() throws ClassCastException {
        ArrayList arrayList = new ArrayList();
        arrayList.add("String");
        Integer o = (Integer) arrayList.get(0);
    }

    public void methodThrowsNullPointerException() throws NullPointerException {
        String a = null;
        a.isEmpty();
    }

    public static void main(String[] args) {

    }
}
