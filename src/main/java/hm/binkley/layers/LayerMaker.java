package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;

import java.util.function.Function;

@FunctionalInterface
public interface LayerMaker<L extends Layer>
        extends Function<Surface, L> {}
