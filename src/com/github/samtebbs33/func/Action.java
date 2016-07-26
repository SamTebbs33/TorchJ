package com.github.samtebbs33.func;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by samtebbs on 25/07/2016.
 */
public class Action {

    private final Optional<Object> obj;
    private final Optional<Action> parent;
    private final Optional<Function> function;
    private final Optional<Consumer> consumer;

    public Action(Object obj) {
        this(Optional.of(obj), Optional.empty(), Optional.empty(), Optional.empty());
    }

    Action(Optional obj, Optional<Action> parent, Optional<Function> function, Optional<Consumer> consumer) {
        this.obj = obj;
        this.parent = parent;
        this.function = function;
        this.consumer = consumer;
    }

    Action(Action parent, Function function) {
        this(Optional.empty(), Optional.of(parent), Optional.of(function), Optional.empty());
    }

    Action(Action parent, Consumer consumer) {
        this(Optional.empty(), Optional.of(parent), Optional.empty(), Optional.of(consumer));
    }

    Action(Action parent, Object obj, Object bar) {
        this(Optional.of(obj), Optional.of(parent), Optional.empty(), Optional.empty());
    }

    /**
     * Passes value to function and sends returned value along chain.
     * Not lazily evaluated.
     * @param function
     * @return
     */
    public Action then(Function function) {
        Object val;
        if(!obj.isPresent()) val = parent.get().get();
        else val = obj.get();
        return new Action(this, function.apply(val), null);
    }

    /**
     * Same as then() but lazily evaluated
     * @param function
     * @return
     */
    public Action later(Function function) {
        return new Action(this, function);
    }

    /**
     * Pass values to consumer and sends same value along chain.
     * Lazily evaluated.
     * @param consumer
     * @return
     */
    public Action and(Consumer consumer) {
        return new Action(this, consumer);
    }

    /**
     * Creates an if-chain. Passes value to predicate and passes specified value along chain if predicate returns true.
     * Lazily evaluated.
     * @param pred
     * @param obj
     * @return
     */
    public ActionIf _if(Predicate pred, Object obj) {
        return new ActionIf(this, obj, pred);
    }

    /**
     * Returns the value passed along chain as an instance of the specified class and evaluates all lazily evaluated calls.
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getAs(Class<T> cls) {
        return cls.cast(get());
    }

    /**
     * Returns the value passed along chain and evaluates all lazily evaluated calls.
     * @return
     */
    public Object get() {
        if(!obj.isPresent()) {
            if(function.isPresent()) return function.get().apply(parent.get().get());
            else if(consumer.isPresent()) {
                Object obj = parent.get().get();
                consumer.get().accept(obj);
                return obj;
            }
        }
        return obj.get();
    }

}

class ActionIf {

    private final Predicate predicate;
    private final Action parent;
    private Optional<Object> parentVal = Optional.empty();
    private final Optional<ActionIf> parentIf;
    private final Object obj;

    <T> ActionIf(Action parent, T obj, Predicate pred) {
        this.parent = parent;
        this.predicate = pred;
        this.parentIf = Optional.empty();
        this.obj = obj;
    }

    <T> ActionIf(ActionIf actionIf, T obj, Predicate pred) {
        this.parent = actionIf.parent;
        this.predicate = pred;
        this.parentIf = Optional.of(actionIf);
        this.obj = obj;
    }

    /**
     * Creates an else-if and passes the given value along if no previous parts of the if-chain evaluated to true the predicate evaluates to true.
     * Lazily evaluated.
     * @param pred
     * @param obj
     * @param <T>
     * @return
     */
    public <T> ActionIf elseIf(Predicate<T> pred, Object obj) {
        return new ActionIf(this, obj, pred);
    }

    private Optional eval() {
        if(parentIf.isPresent()) {
            Optional opt = parentIf.get().eval();
            if(opt.isPresent()) return opt;
        }
        if(!parentVal.isPresent()) parentVal = Optional.of(parent.get());
        if(predicate.test(parentVal.get())) return Optional.of(obj);
        else return Optional.empty();
    }

    /**
     * Ends the if-chain and passes the value passed to the original if-statement when no other parts of the if-chain evaluated to true.
     * Not lazily evaluated.
     * @return
     */
    public Action _else() {
        Optional opt = this.eval();
        return new Action(parent, opt.isPresent() ? opt.get() : parentVal.isPresent() ? parentVal.get() : parent.get(), null);
    }

    /**
     * Ends the if-chain and passes the specified value when no other parts of the if-chain evaluated to true.
     * Not lazily evaluated.
     * @param obj
     * @return
     */
    public Action _else(Object obj) {
        Optional opt = this.eval();
        return new Action(parent, opt.isPresent() ? opt.get() : obj, null);
    }

}
