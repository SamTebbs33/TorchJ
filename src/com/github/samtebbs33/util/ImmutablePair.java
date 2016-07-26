package com.github.samtebbs33.util;

/**
 * Created by samtebbs on 25/03/2016.
 */
public class ImmutablePair<L, R> {
    public final L l;
    public final R r;

    public ImmutablePair(L l, R r) {
        this.l = l;
        this.r = r;
    }
}
