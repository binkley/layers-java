package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

final class AttunableItemRule<R>
        extends Rule<R> {
    private final AttunementItem<?> layer;
    private final Rule<R> rule;

    AttunableItemRule(final AttunementItem<?> layer, final Rule<R> rule) {
        super("Attunable item");
        this.layer = layer;
        this.rule = rule;
    }

    @Override
    public R apply(final RuleSurface layers) {
        return layer.isAttuned() ? rule.apply(layers) : layers.getWithout();
    }

    @Override
    public String toString() {
        return "[Rule: " + rule.name() + " (" + (layer.isAttuned() ? "attuned"
                : "unattuned") + ")]";
    }
}
