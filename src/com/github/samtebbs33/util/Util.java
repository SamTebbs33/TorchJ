package com.github.samtebbs33.util;

import java.util.function.BiFunction;

public class Util {

    /**
    * Returns the var-args as an array. Useful for methods that require an array but the conciseness of var-args is desired.
    * @param T... objs - the var-args to return
    * @return T[] - the var-args
    */
    public static <T> T[] array(T... objs) {
    	return objs;
    }

}
