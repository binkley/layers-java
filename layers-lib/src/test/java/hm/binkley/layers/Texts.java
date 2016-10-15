package hm.binkley.layers;

import org.kohsuke.MetaInfServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.Value.mostRecent;
import static hm.binkley.layers.Value.ofValue;
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
        public Layer apply(final Layers.Surface layers) {
            final Layer layer = new Layer(layers, "Base rules for text");
            for (final Texts key : Texts.values())
                layer.put(key, mostRecent(key, ""));
            return layer;
        }
    }
}
