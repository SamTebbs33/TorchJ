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

    public static <T, E> boolean equals(T[] obj1, E[] obj2, BiFunction<T, E, Boolean> equalityFunc) {
        if(obj1.length != obj2.length) return false;
        for(int i = 0; i < obj1.length; i++) {
            if(!equalityFunc.apply(obj1[i], obj2[i])) return false;
        }
        return true;
    }

}
