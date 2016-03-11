package com.github.samtebbs33.util;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by samtebbs on 11/03/2016.
 */
public class Range implements Iterable<Integer>, Iterator<Integer> {

    private final int start, step;
    private final Predicate<Integer> end;
    private int current;

    public Range(int start, Predicate<Integer> end, int step) {
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
    public Iterator<Integer> iterator() {
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
    public Integer next() {
        int temp = current;
        current += step;
        return temp;
    }
}
