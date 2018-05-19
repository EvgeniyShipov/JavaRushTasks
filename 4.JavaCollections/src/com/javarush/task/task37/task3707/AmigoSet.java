package com.javarush.task.task37.task3707;

import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        map = new HashMap<>(Math.max(16, (int) (collection.size() / .75f) + 1));
        addAll(collection);
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public Object clone() {
        try {
            AmigoSet<E> set = (AmigoSet<E>) super.clone();
            map = (HashMap<E, Object>) map.clone();
            return set;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        s.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        s.writeInt(map.size());

        for (E e : map.keySet())
            s.writeObject(e);
    }

    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        int capacity = s.readInt();
        float loadFactor = s.readFloat();
        int size = s.readInt();

        map = new HashMap<>(capacity, loadFactor);

        for (int i = 0; i < size; i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }
}
