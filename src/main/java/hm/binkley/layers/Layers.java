package hm.binkley.layers;

import hm.binkley.layers.dnd.Abilities;
import hm.binkley.layers.dnd.Characters;
import hm.binkley.layers.dnd.Proficiencies;
import hm.binkley.layers.dnd.Races;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static hm.binkley.layers.dnd.MagicItems.beltOfGiantStrength;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static java.lang.System.out;
import static java.util.stream.Collectors.joining;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = PRIVATE)
public final class Layers {
    private final List<Layer> layers = new ArrayList<>();

    public static Layer firstLayer(final LayerMaker ctor,
            final Consumer<Layers> layersHolder) {
        final Layers layers = new Layers();
        layersHolder.accept(layers);
        return ctor.apply(layers.new Surface());
    }

    public <T> T get(final Object key) {
        return this.<T>ruleValuesFor(key).
                findFirst().
                orElse(Value.mostRecent(key)).
                apply(this);
    }

    public <T> Stream<T> valuesFor(final Object key) {
        return this.<T>streamFor(key).
                map(Value::value).
                filter(Optional::isPresent).
                map(Optional::get);
    }

    public <T> Stream<Value<T>> ruleValuesFor(final Object key) {
        return this.<T>streamFor(key).
                filter(value -> value.rule().isPresent());
    }

    public final class Surface {
        public Layer saveAndNext(final Layer layer, final LayerMaker next) {
            layers.add(0, layer);
            return next.apply(this);
        }

        public void forget(final Layer discard) {
            layers.remove(discard);
        }
    }

    @Override
    public String toString() {
        final int size = layers.size();
        return IntStream.range(0, size).
                mapToObj(i -> (size - i) + ": " + layers.get(i)).
                collect(joining("\n"));
    }

    @SuppressWarnings("unchecked")
    private <T> Stream<Value<T>> streamFor(final Object key) {
        return layers.stream().
                filter(layer -> layer.containsKey(key)).
                map(layer -> (Value<T>) layer.<T>get(key));
    }

    public static void main(final String... args) {
        final Layers[] layersHolder = new Layers[1];
        final Layer firstLayer = firstLayer(Abilities::baseRuleAbilityScores,
                layers -> layersHolder[0] = layers);
        final Layers layers = layersHolder[0];

        firstLayer.
                saveAndNext(Proficiencies::baseRuleProficiencyBonuses).
                saveAndNext(characterDescription("Bob")).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(Races::plainHuman).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                saveAndNext(beltOfGiantStrength(20)).
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);

        out.println("layers =");
        out.println(layers);

        for (final Characters description : Characters.values())
            out.println(description + " = " + layers.get(description));

        for (final Abilities score : Abilities.values())
            out.println(score + " = " + layers.get(score));

        for (final Proficiencies bonus : Proficiencies.values())
            out.println(bonus + " = " + layers.get(bonus));
    }
}
