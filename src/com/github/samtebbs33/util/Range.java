package com.github.samtebbs33.util;

import java.util.Iterator;
import java.util.function.*;

/**
 * Created by samtebbs on 11/03/2016.
 */
public class Range<T> implements Iterable<T>, Iterator<T> {

    private T start, current;
    private final Predicate<T> end;
    private final Function<T, T> step;

    public Range(T start, Predicate<T> end, Function<T, T> step) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.current = start;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return this;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return !end.test(current);
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     */
    @Override
    public T next() {
        T temp = current;
        current = step.apply(current);
        return temp;
    }
}
