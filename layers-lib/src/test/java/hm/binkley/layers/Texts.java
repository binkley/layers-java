package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule;
import org.kohsuke.MetaInfServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.rules.Rule.mostRecent;
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

    public static LayerMaker<TextsLayer> texts(final String name) {
        return layers -> new TextsLayer(layers).
                put(NAME, name);
    }

    public static final class TextsLayer
            extends Layer<TextsLayer> {
        public TextsLayer(final LayerSurface layers) {
            super(layers, "Textual matters");
        }
    }

    @MetaInfServices
    public static final class TextsRules
            implements BaseRule {
        @Override
        public BaseRulesLayer apply(final LayerSurface layers) {
            final BaseRulesLayer layer = new BaseRulesLayer(layers);
            for (final Texts key : values())
                layer.put(key, k -> mostRecent(""));
            return layer;
        }
    }
}
