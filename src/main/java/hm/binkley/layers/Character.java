package hm.binkley.layers;

import hm.binkley.layers.Field.StringField;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static hm.binkley.layers.Character.CharacterLayer.characterLayer;
import static hm.binkley.layers.Character.FreeFormLayer.freeFormLayer;
import static hm.binkley.layers.Character.HumanLayer.humanLayer;
import static hm.binkley.layers.Character.PlayerLayer.playerLayer;
import static hm.binkley.layers.Character.StatsLayer.statsLayer;
import static hm.binkley.layers.Field.IntegerField.additiveIntegerField;
import static hm.binkley.layers.Layers.newLayers;
import static java.lang.System.out;

/**
 * {@code Character} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Character {
    private final Layers layers;
    private AnnotatedLayer<?> current;

    public static void main(final String... args) {
        final Character bob = new Character();
        out.println(bob);
        out.println(bob.current);
        bob.layers.history().forEach(out::println);
    }

    public Character() {
        layers = newLayers(playerLayer("Starting a new game", "Brian",
                "The Empire of Texas"), l -> current = l, PlayerLayer.fields);
        current = current.
                accept(characterLayer("Note #2", "Bob"),
                        CharacterLayer.fields).
                accept(statsLayer("Note #3", 15, 14, 13, 12, 10, 8),
                        StatsLayer.fields).
                accept(humanLayer("Note #4"), HumanLayer.fields).
                accept(characterLayer("Renamed character", "Sally")).
                accept(freeFormLayer("Scratch", "Note #6"));
    }

    @Override
    public String toString() {
        return layers.toString();
    }

    public static class AnnotatedLayer<L extends AnnotatedLayer<L>>
            extends BlankLayer<L> {
        private final String notes;
        private ZonedDateTime accepted;

        protected AnnotatedLayer(final Surface surface, final String name,
                final String notes) {
            super(surface, name);
            this.notes = notes;
        }

        @Override
        public <M extends Layer<M>> M accept(final Function<Surface, M> next,
                final Map<String, Field> fields) {
            accepted = ZonedDateTime.now();
            return super.accept(next, fields);
        }

        @Override
        public String toString() {
            return "{notes=" + notes + ", accepted=" + accepted + ", "
                    + changed();
        }

        public final String notes() {
            return notes;
        }

        public final ZonedDateTime accepted() {
            return accepted;
        }
    }

    public static final class FreeFormLayer
            extends AnnotatedLayer<FreeFormLayer> {
        public static Function<Surface, FreeFormLayer> freeFormLayer(
                final String name, final String notes) {
            return s -> new FreeFormLayer(s, name, notes);
        }

        private FreeFormLayer(final Surface surface, final String name,
                final String notes) {
            super(surface, name, notes);
        }
    }

    public static final class PlayerLayer
            extends AnnotatedLayer<PlayerLayer> {
        public static final String PLAYER_NAME = "player:name";
        public static final String PLAYER_CAMPAIGN = "player:campaign";

        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put(PLAYER_NAME, new StringField());
            fields.put(PLAYER_CAMPAIGN, new StringField());
        }

        public static Function<Surface, PlayerLayer> playerLayer(
                final String notes, final String name,
                final String campaign) {
            return s -> new PlayerLayer(s, notes, name, campaign);
        }

        private PlayerLayer(final Surface surface, final String notes,
                final String name, final String campaign) {
            super(surface, "Player", notes);
            put(PLAYER_NAME, name);
            put(PLAYER_CAMPAIGN, campaign);
        }
    }

    public static final class CharacterLayer
            extends AnnotatedLayer<CharacterLayer> {
        private static final Map<String, Field> fields = new HashMap<>();

        public static final String CHARACTER_NAME = "character:name";

        static {
            fields.put(CHARACTER_NAME, new StringField());
        }

        public static Function<Surface, CharacterLayer> characterLayer(
                final String notes, final String name) {
            return s -> new CharacterLayer(s, notes, name);
        }

        private CharacterLayer(final Surface surface, final String notes,
                final String name) {
            super(surface, "Character", notes);
            put(CHARACTER_NAME, name);
        }
    }

    public static final class StatsLayer
            extends AnnotatedLayer<StatsLayer> {
        public static final String STATS_STR = "stats:STR";
        public static final String STATS_DEX = "stats:DEX";
        public static final String STATS_CON = "stats:CON";
        public static final String STATS_INT = "stats:INT";
        public static final String STATS_WIS = "stats:WIS";
        public static final String STATS_CHA = "stats:CHA";

        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put(STATS_STR, additiveIntegerField());
            fields.put(STATS_DEX, additiveIntegerField());
            fields.put(STATS_CON, additiveIntegerField());
            fields.put(STATS_INT, additiveIntegerField());
            fields.put(STATS_WIS, additiveIntegerField());
            fields.put(STATS_CHA, additiveIntegerField());
        }

        public static Function<Surface, StatsLayer> statsLayer(
                final String notes, final int STR, final int DEX,
                final int CON, final int INT, final int WIS, final int CHA) {
            return s -> new StatsLayer(s, notes, STR, DEX, CON, INT, WIS,
                    CHA);
        }

        private StatsLayer(final Surface surface, final String notes,
                final int STR, final int DEX, final int CON, final int INT,
                final int WIS, final int CHA) {
            super(surface, "Stats", notes);
            put(STATS_STR, STR);
            put(STATS_DEX, DEX);
            put(STATS_CON, CON);
            put(STATS_INT, INT);
            put(STATS_WIS, WIS);
            put(STATS_CHA, CHA);
        }
    }

    private abstract static class RaceLayer<L extends RaceLayer<L>>
            extends AnnotatedLayer<L> {
        protected static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("race:name", new StringField());
            fields.put("race:STR-bonus", additiveIntegerField());
            fields.put("race:DEX-bonus", additiveIntegerField());
            fields.put("race:CON-bonus", additiveIntegerField());
            fields.put("race:INT-bonus", additiveIntegerField());
            fields.put("race:WIS-bonus", additiveIntegerField());
            fields.put("race:CHA-bonus", additiveIntegerField());
        }

        protected RaceLayer(final Surface surface, final String notes,
                final String name) {
            super(surface, "Race", notes);
            put("race:name", name);
        }
    }

    public static final class HumanLayer
            extends RaceLayer<HumanLayer> {
        public static Function<Surface, HumanLayer> humanLayer(
                final String notes) {
            return s -> new HumanLayer(s, notes);
        }

        private HumanLayer(final Surface surface, final String notes) {
            super(surface, notes, "Human");
            put("stats:STR", 1);
            put("race:STR-bonus", 1);
            put("stats:DEX", 1);
            put("race:DEX-bonus", 1);
            put("stats:CON", 1);
            put("race:CON-bonus", 1);
            put("stats:INT", 1);
            put("race:INT-bonus", 1);
            put("stats:WIS", 1);
            put("race:WIS-bonus", 1);
            put("stats:CHA", 1);
            put("race:CHA-bonus", 1);
        }
    }
}
