package hm.binkley.layers.dnd.item;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "inPounds")
public final class Weight
        implements Comparable<Weight> {
    private final float pounds;

    /** @todo Use our friend, Lombok */
    public float pounds() {return pounds;}

    @Override
    public String toString() {
        return "#" + pounds;
    }

    @Override
    public int compareTo(final Weight that) {
        return Float.compare(pounds, that.pounds);
    }
}
