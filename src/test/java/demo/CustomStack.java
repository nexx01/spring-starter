package demo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

public class CustomStack<T> {

    LinkedList<T> queue;
    public void push(T x) {
        queue.push(x);
    }

    public T pop() {
        return queue.pollLast();
    }

    public T top() {
        return queue.peekLast();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

}
