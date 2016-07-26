package com.github.samtebbs33;

import com.github.samtebbs33.util.Util;
import com.github.samtebbs33.util.Pair;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Created by samtebbs on 01/02/2016.
 */
public class Application {

    protected PrintStream out = System.out, err = System.err;
    protected Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName(args[0]);
        Constructor[] constructors = cls.getConstructors();
        Constructor constructor = null;
        Pair<List<Class>, List<Object>> convertedArgs = getArgClasses(args, 1);
        List<Class> argClasses = convertedArgs.l;
        for(Constructor c : constructors) {
            Parameter[] params = c.getParameters();
            if (params.length == argClasses.size() && equal(params, argClasses)) {
                constructor = c;
                break;
            }
        }
        if(constructor == null) System.err.println("No constructor found for arguments");
        else constructor.newInstance(convertedArgs.r.toArray());
    }

    private static boolean equal(Parameter[] params, List<Class> argClasses) {
        return Util.equals(params, argClasses.toArray(), (param, cls) -> param.getType() == cls);
    }

    private static Pair<List<Class>, List<Object>> getArgClasses(String[] args, int start) {
        Pair<List<Class>, List<Object>> result = new Pair<>(new LinkedList<>(), new LinkedList<>());
        for(int i = start; i < args.length; i++) {
            Pair<Class, Object> pair = getArgClass(args[i]);
            result.l.add(pair.l);
            result.r.add(pair.r);
        }
        return result;
    }

    private static Pair<Class, Object> getArgClass(String arg) {
        try {
            int i = Integer.parseInt(arg);
            return new Pair(Integer.class, i);
        } catch (NumberFormatException e){}
        try {
            double d = Double.parseDouble(arg);
            return new Pair<>(Double.class, d);
        } catch (NumberFormatException e) {}
        try {
            float f = Float.parseFloat(arg);
            return new Pair(Float.class, f);
        } catch (NumberFormatException e) {}
        return new Pair(String.class, arg);
    }

    protected void exit(int status) {
        System.exit(status);
    }

    public void print(Object obj) {
        out.print(obj);
    }

    public void println(Object obj) {
        out.println(obj);
    }

    public int inInt() {
        return in.nextInt();
    }

    public long inLong() {
        return in.nextLong();
    }

    public short inShort() {
        return in.nextShort();
    }

    public String inLine() {
        return in.nextLine();
    }

    public String inStr() {
        return in.next();
    }

    public byte inByte() {
        return in.nextByte();
    }

    public boolean hasInt() {
        return in.hasNextInt();
    }

    public boolean hasLong() {
        return in.hasNextLong();
    }

    public boolean hasShort() {
        return in.hasNextShort();
    }

    public boolean hasLine() {
        return in.hasNextLine();
    }

    public boolean hasStr() {
        return in.hasNext();
    }

    public boolean hasByte() {
        return in.hasNextByte();
    }

}
