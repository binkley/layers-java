package hm.binkley.layers;

import hm.binkley.layers.Layer.LayerView;
import hm.binkley.layers.dnd.Abilities;
import hm.binkley.layers.dnd.Characters;
import hm.binkley.layers.dnd.MagicItems;
import hm.binkley.layers.dnd.Proficiencies;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.WIS;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static hm.binkley.layers.dnd.Races.humanVariant;
import static java.lang.System.out;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = PRIVATE)
public final class Layers {
    private final List<Layer> layers;

    public static Layer firstLayer(final LayerMaker ctor,
            final Consumer<Layers> layersHolder) {
        final Layers layers = new Layers(new ArrayList<>());
        layersHolder.accept(layers);
        return ctor.apply(layers.new Surface());
    }

    public boolean isEmpty() {
        return !layers.stream().
                anyMatch(layer -> !layer.isEmpty());
    }

    public int size() {
        return (int) layers.stream().
                flatMap(layer -> layer.keys().stream()).
                distinct().
                count();
    }

    public Set<Object> keys() {
        return unmodifiableSet(layers.stream().
                flatMap(layer -> layer.keys().stream()).
                collect(toSet()));
    }

    public boolean containsKey(final Object key) {
        return streamFor(key).
                findAny().
                isPresent();
    }

    /** @todo Rethink tradeoff of no type token in arg vs safety */
    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T> T get(final Object key) {
        return this.<T>ruleValueFor(key).
                apply(this);
    }

    public Map<Object, Object> toMap() {
        return unmodifiableMap(keys().stream().
                map(key -> new SimpleImmutableEntry<>(key, get(key))).
                collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
    }

    public Stream<LayerView> history() {
        return layers.stream().
                map(Layer::view);
    }

    public <T> Stream<T> plainValuesFor(final Object key) {
        return this.<T>streamFor(key).
                map(Value::value).
                filter(Optional::isPresent).
                map(Optional::get);
    }

    public Layers whatIfWith(final Layer layer) {
        final List<Layer> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.add(0, layer);
        return new Layers(scenario);
    }

    public Layers whatIfWithout(final Layer layer) {
        final List<Layer> scenario = new ArrayList<>();
        scenario.addAll(layers);
        scenario.remove(layer);
        return new Layers(scenario);
    }

    public final class Surface {
        public Layer saveAndNext(final Layer layer, final LayerMaker next) {
            layers.add(0, layer);
            return next.apply(this);
        }

        public void discard(final Layer discard) {
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

    private <T> Value<T> ruleValueFor(final Object key) {
        return this.<T>streamFor(key).
                filter(value -> value.rule().isPresent()).
                findFirst().
                orElse(Value.mostRecent(key));
    }

    private <T> Stream<Value<T>> streamFor(final Object key) {
        return layers.stream().
                filter(layer -> layer.containsKey(key)).
                map(layer -> layer.<T>get(key));
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
                saveAndNext(humanVariant().withSTR().withDEX()).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(abilityScoreIncrease(CON, WIS)).
                saveAndNext(ScratchLayer::new);

        out.println(layers);

        for (final Characters description : Characters.values())
            out.println(description + " = " + layers.get(description));

        for (final Abilities score : Abilities.values())
            out.println(score + " = " + layers.get(score));

        for (final Proficiencies bonus : Proficiencies.values())
            out.println(bonus + " = " + layers.get(bonus));
    }
}
