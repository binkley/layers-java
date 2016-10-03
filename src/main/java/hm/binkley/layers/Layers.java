package hm.binkley.layers;

import hm.binkley.layers.dnd.Abilities;
import hm.binkley.layers.dnd.Characters;
import hm.binkley.layers.dnd.Proficiencies;
import hm.binkley.layers.dnd.Races;
import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static hm.binkley.layers.dnd.MagicItems.beltOfGiantStrength;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static java.lang.System.out;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = PRIVATE)
public final class Layers {
    private final Deque<Layer> layers = new ArrayDeque<>();

    public static Layers newLayers(final Function<Surface, Layer> ctor,
            final Consumer<Layer> holder) {
        final Layers layers = new Layers();
        holder.accept(ctor.apply(layers.new Surface()));
        return layers;
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
        public Layer saveAndNext(final Layer layer,
                final Function<Surface, Layer> next) {
            layers.push(layer);
            return next.apply(this);
        }

        public void forget(final Layer discard) {
            layers.remove(discard);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Stream<Value<T>> streamFor(final Object key) {
        return layers.stream().
                filter(layer -> layer.containsKey(key)).
                map(layer -> (Value<T>) layer.<T>get(key));
    }

    public static void main(final String... args) {
        final Layer[] firstLayer = new Layer[1];
        final Layers layers = newLayers(Abilities::baseRuleAbilityScores,
                layer -> firstLayer[0] = layer);

        firstLayer[0].
                saveAndNext(Proficiencies::baseRuleProficiencyBonuses).
                saveAndNext(characterDescription("Bob")).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(Races::plainHuman).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                saveAndNext(beltOfGiantStrength(20)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(Layer::new);

        for (final Characters description : Characters.values())
            out.println(description + " = " + layers.get(description));

        for (final Abilities score : Abilities.values())
            out.println(score + " = " + layers.get(score));

        for (final Proficiencies bonus : Proficiencies.values())
            out.println(bonus + " = " + layers.get(bonus));
    }
}
