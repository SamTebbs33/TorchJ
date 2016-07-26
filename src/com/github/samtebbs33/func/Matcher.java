package com.github.samtebbs33.func;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by samtebbs on 01/02/2016.
 */
public class Matcher<M> {

    private M[] objs;
    private Runnable consumer;

    public Matcher(Runnable consumer, M... objs) {
        this.consumer = consumer;
        this.objs = objs;
    }

    public <E> boolean matches(E obj, BiPredicate<E, M> predicate) {
        for (M e : objs) if (predicate.test(obj, e)) return true;
        return false;
    }

    public void run() {
        consumer.run();
    }
}
