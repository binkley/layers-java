package hm.binkley.layers;

import java.util.function.Function;

public interface Key<T> {
    Object key();

    Function<Layers, T> value();
}
