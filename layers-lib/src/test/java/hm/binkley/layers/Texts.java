package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.rules.Rule;
import org.kohsuke.MetaInfServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.values.Value.ofValue;
import static java.util.Collections.unmodifiableList;

public class Texts
        extends XEnum<Texts> {
    private static final AtomicInteger ordinal = new AtomicInteger();
    private static final List<Texts> values = new ArrayList<>();

    public static final Texts NAME = new Texts("NAME");

    public static List<Texts> values() {
        return unmodifiableList(values);
    }

    protected Texts(final String name) {
        super(name, ordinal.getAndIncrement());
        values.add(this);
    }

    public static LayerMaker<Layer> texts(final String name) {
        return layers -> {
            final Layer layer = new Layer(layers, "Textual matters");
            layer.put(NAME, ofValue(name));
            return layer;
        };
    }

    @MetaInfServices
    public static final class TextsBaseRules
            implements BaseRule {
        @Override
        public Layer apply(final LayerSurface layers) {
            final Layer layer = new Layer(layers, "Base rules for text");
            for (final Texts key : values())
                layer.put(key, "", (key1) -> Rule.mostRecent());
            return layer;
        }
    }
}
