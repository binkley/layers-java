package hm.binkley.layers.dnd.item;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "inCuft")
public final class Volume
        implements Comparable<Volume> {
    private final float cuft;

    /** @todo Use our friend, Lombok */
    public float feet() {return cuft;}

    @Override
    public String toString() {
        return cuft + " cuft";
    }

    @Override
    public int compareTo(final Volume that) {
        return Float.compare(cuft, that.cuft);
    }
}
