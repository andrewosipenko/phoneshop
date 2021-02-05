package com.es.core.populator.interfaces;

public interface Populator<S, T> {

    public void populate(S source, T target);

}
