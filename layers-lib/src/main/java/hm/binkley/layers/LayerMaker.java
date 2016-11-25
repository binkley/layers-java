package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;

import java.util.function.Function;

@FunctionalInterface
public interface LayerMaker<L extends Layer<L>>
        extends Function<LayerSurface, L> {}
