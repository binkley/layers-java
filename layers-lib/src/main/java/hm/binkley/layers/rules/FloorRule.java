package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

import static java.lang.Integer.max;

public class FloorRule
        extends Rule<Integer, Integer> {
    private final int floor;

    FloorRule(final int floor) {
        super("Floor: " + floor);
        this.floor = floor;
    }

    @Override
    public Integer apply(final RuleSurface<Integer, Integer> layers) {
        return max(floor, layers.getWithout());
    }
}
