package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Serializable, Cloneable {
    Entry<String> root;
    private Queue<Entry<String>> queue = new LinkedList<>();

    public CustomTree() {
        this.root = new Entry<>("root");
        root.lineNumber = 0;
        queue.add(root);
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String s) {
        Optional<Entry<String>> entry = queue.stream()
                .filter(Entry::isAvailableToAddChildren)
                .findFirst();

        if (entry.isPresent()) {
            Entry<String> parent = entry.get();
            Entry<String> child = new Entry<>(s);
            queue.add(child);
            if (parent.leftChild == null)
                parent.leftChild = child;
            else
                parent.rightChild = child;
            child.parent = parent;
            child.lineNumber = parent.lineNumber + 1;
            parent.checkChildren();
            return true;
        } else {
            queue.forEach(Entry::checkChildren);
            return add(s);
        }
    }

    public String getParent(String s) {
        return queue.stream()
                .filter(entry -> s.equals(entry.elementName))
                .map(entry -> entry.parent)
                .map(entry -> entry.elementName)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String))
            throw new UnsupportedOperationException();

        Optional<Entry<String>> entry = queue.stream()
                .filter(ent -> o.equals(ent.elementName))
                .findFirst();

        if (entry.isPresent()) {
            Entry<String> ent = entry.get();
            queue.remove(ent);
            removeChildren(ent);
            if (ent.parent.leftChild == ent)
                ent.parent.leftChild = null;
            else
                ent.parent.rightChild = null;
            return true;
        }
        return false;
    }

    private void removeChildren(Entry<String> entry) {
        if (entry.leftChild != null) {
            removeChildren(entry.leftChild);
            queue.remove(entry.leftChild);
        }
        if (entry.rightChild != null) {
            removeChildren(entry.rightChild);
            queue.remove(entry.rightChild);
        }
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return queue.size() - 1;
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        void checkChildren() {
            availableToAddLeftChildren = leftChild == null;
            availableToAddRightChildren = rightChild == null;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
