package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public final class Rule<T> {
    public final Function<Layers, T> fn;
    public final Layer layer;
}
