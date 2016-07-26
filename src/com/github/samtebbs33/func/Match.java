package com.github.samtebbs33.func;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Created by samtebbs on 01/02/2016.
 */
public class Match<E, M> {

    public final Matcher<M>[] matchers;
    public final BiPredicate<E, M> predicate;

    public static final BiPredicate<Object, Class> CLASS = (obj, cls) -> obj.getClass() == cls,
            INSTANCE_OF = (obj, cls) -> cls.isInstance(obj);
    public static final BiPredicate<Object, Object> EQUALS = Objects::equals;


    public Match( BiPredicate<E, M> predicate, Matcher<M>... matchers) {
        this.matchers = matchers;
        this.predicate = predicate;
    }

    public boolean match(E obj) {
        for(Matcher m : matchers) if(m.matches(obj, predicate)) {
            m.run();
            return true;
        }
        return false;
    }

    public void match(E obj, Runnable def) {
        if(!match(obj)) def.run();
    }

    public static void main(String[] args) {
        Match<Object, Object> match = new Match<>(EQUALS,
                new Matcher<>(() -> System.out.println("is hello"), "hello"),
                new Matcher<>(() -> System.out.println("is hi"), "hi")
        );
        match.match("greetings", () -> System.out.println("None of them"));
    }

}
