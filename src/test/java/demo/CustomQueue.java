package demo;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;

public class CustomQueue<T> extends AbstractQueue<T> {
    private LinkedList<T> elements;

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean offer(T t){
        if(t==null) return false;
        elements.add(t);
        return true;
    }

    @Override
    public T poll() {
        var iterator = elements.iterator();
        var next = iterator.next();
        if (next != null) {
            iterator.remove();
            return next;
        }
        return null;
    }

    @Override
    public T peek() {
        return elements.getFirst();
    }
}
