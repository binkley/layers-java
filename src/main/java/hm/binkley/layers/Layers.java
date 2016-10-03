package hm.binkley.layers;

import hm.binkley.layers.dnd.AbilityScore;
import hm.binkley.layers.dnd.CharacterDescription;
import hm.binkley.layers.dnd.ProficiencyBonus;
import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.dnd.AbilityScore.CHA;
import static hm.binkley.layers.dnd.AbilityScore.CON;
import static hm.binkley.layers.dnd.AbilityScore.DEX;
import static hm.binkley.layers.dnd.AbilityScore.INT;
import static hm.binkley.layers.dnd.AbilityScore.STR;
import static hm.binkley.layers.dnd.AbilityScore.WIS;
import static hm.binkley.layers.dnd.AbilityScore.abilityScores;
import static hm.binkley.layers.dnd.AbilityScore.baseRuleAbilityScores;
import static hm.binkley.layers.dnd.CharacterDescription.characterDescription;
import static hm.binkley.layers.dnd.ProficiencyBonus.ACROBATICS;
import static hm.binkley.layers.dnd.ProficiencyBonus.ATHLETICS;
import static hm.binkley.layers.dnd.ProficiencyBonus
        .baseRuleProficiencyBonuses;
import static hm.binkley.layers.dnd.ProficiencyBonus.doubleProficiency;
import static hm.binkley.layers.dnd.ProficiencyBonus.proficiencyBonus;
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

    public static Layer plainHuman(final Surface layers) {
        final Layer layer = new Layer(layers);
        layer.put(STR, ofValue(1));
        layer.put(DEX, ofValue(1));
        layer.put(CON, ofValue(1));
        layer.put(INT, ofValue(1));
        layer.put(WIS, ofValue(1));
        layer.put(CHA, ofValue(1));
        return layer;
    }

    public static Function<Surface, Layer> beltOfGiantStrength(
            final int _str) {
        return layers -> {
            final Layer layer = new Layer(layers);
            layer.put(STR, Value.ofBoth(_str, Rule.exactly()));
            return layer;
        };
    }

    public static void main(final String... args) {
        final Layers layers = new Layers();
        final Surface surface = layers.new Surface();

        layers.layers.add(baseRuleAbilityScores(surface));
        layers.layers.add(baseRuleProficiencyBonuses(surface));

        layers.layers.add(characterDescription("Bob").apply(surface));
        layers.layers
                .add(abilityScores(8, 15, 14, 10, 13, 12).apply(surface));
        layers.layers.add(plainHuman(surface));
        layers.layers.add(proficiencyBonus(ACROBATICS, 1).apply(surface));
        layers.layers.add(proficiencyBonus(ATHLETICS, 1).apply(surface));
        layers.layers.add(doubleProficiency(ACROBATICS).apply(surface));
        layers.layers.add(beltOfGiantStrength(20).apply(surface));
        layers.layers.add(abilityScores(1, 0, 0, 0, 0, 0).apply(surface));

        for (final CharacterDescription description : CharacterDescription
                .values())
            out.println(description + " = " + layers.get(description));

        for (final AbilityScore score : AbilityScore.values())
            out.println(score + " = " + layers.get(score));

        for (final ProficiencyBonus bonus : ProficiencyBonus.values())
            out.println(bonus + " = " + layers.get(bonus));
    }
}
