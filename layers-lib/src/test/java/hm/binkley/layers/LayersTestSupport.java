package hm.binkley.layers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.BeforeEach;

import static lombok.AccessLevel.PROTECTED;

@Accessors(fluent = true)
@RequiredArgsConstructor(access = PROTECTED)
public abstract class LayersTestSupport<L extends Layer<L>> {
    private final LayerMaker<L> ctor;
    @Getter
    private Layers layers;
    @Getter
    private L firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = Layers.firstLayer(ctor, layers -> this.layers = layers);
    }
}
