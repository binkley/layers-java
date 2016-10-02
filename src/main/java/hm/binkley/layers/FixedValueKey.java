package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Map.Entry;
import java.util.function.Function;

@RequiredArgsConstructor
public final class FixedValueKey<T>
        implements Key<T> {
    private final Object key;

    @Override
    public Object key() {
        return key;
    }

    @Override
    public Function<Layers, T> value() {
        return layers -> (T) layers.findEntries(key).
                map(Entry::getValue).
                findFirst().
                get();
    }
}
