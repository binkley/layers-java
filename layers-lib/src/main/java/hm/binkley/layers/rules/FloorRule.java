package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;

import static java.lang.Integer.max;

public class FloorRule<L extends Layer<L>>
        extends Rule<L, Integer, Integer> {
    private final int floor;

    FloorRule(final int floor) {
        super("Floor: " + floor);
        this.floor = floor;
    }

    @Override
    public Integer apply(final RuleSurface<L, Integer, Integer> layers) {
        return max(floor, layers.getWithout());
    }
}
