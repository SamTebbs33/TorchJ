package com.github.samtebbs33.func;

import java.util.function.*;

/**
 * Created by samtebbs on 11/03/2016.
 */
public class Loop {

    public static void forLoop(int from, Predicate<Integer> condition, Function<Integer, Integer> step, Consumer<Integer> consumer) {
        for (int i = from; condition.test(i); i = step.apply(i)) consumer.accept(i);
    }

    public static <T> void forEach(Iterable<T> iterable, BiConsumer<T, Integer> consumer) {
        int i = 0;
        for (T t : iterable) consumer.accept(t, i++);
    }

    public static void whileLoop(Supplier<Boolean> condition, Runnable runnable) {
        while (condition.get()) runnable.run();
    }

    public static void whileLoop(Supplier<Boolean> condition, Consumer<Integer> consumer) {
        int i = 0;
        while (condition.get()) consumer.accept(i++);
    }

    public static void doWhile(Supplier<Boolean> condition, Runnable runnable) {
        runnable.run();
        whileLoop(condition, runnable);
    }

    public static void doWhile(Supplier<Boolean> condition, Consumer<Integer> consumer) {
        int i = 1;
        consumer.accept(i);
        while (condition.get()) consumer.accept(i++);
    }

}
