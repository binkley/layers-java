package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public final class SumAllKey
        implements Key<Integer> {
    private final Object key;

    @Override
    public Object key() {
        return key;
    }

    @Override
    public Function<Layers, Integer> value() {
        return layers -> layers.findEntries(key).
                mapToInt(e -> (Integer) e.getValue()).
                sum();
    }
}
