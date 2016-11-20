package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.rules.Rule.sumAll;
import static java.util.Collections.unmodifiableList;

public class Numbers
        extends XEnum<Numbers> {
    private static final AtomicInteger ordinal = new AtomicInteger();
    private static final List<Numbers> values = new ArrayList<>();
    public static final Numbers FIRST = new Numbers("FIRST");
    public static final Numbers SECOND = new Numbers("SECOND");

    public static List<Numbers> values() {
        return unmodifiableList(values);
    }

    public Numbers(final String name) {
        super(name, ordinal.getAndIncrement());
        values.add(this);
    }

    /** @todo Builder */
    public static LayerMaker<Layer> numbers(final int first,
            final int second) {
        return layers -> {
            final Layer layer = new Layer(layers, "Base numbers");
            layer.put(FIRST, first);
            layer.put(SECOND, second);
            return layer;
        };
    }

    public static LayerMaker<Layer> numberIncrease(final Numbers number) {
        return layers -> {
            final Layer layer = new Layer(layers, "Numeric increase");
            layer.put(number, 1);
            return layer;
        };
    }

    @MetaInfServices
    public static final class AbilitiesBaseRules
            implements BaseRule {
        @Override
        public Layer apply(final LayerSurface layers) {
            final Layer layer = new Layer(layers, "Base rules for numbers");
            for (final Numbers key : values())
                layer.put(key, k -> sumAll());
            return layer;
        }
    }
}
