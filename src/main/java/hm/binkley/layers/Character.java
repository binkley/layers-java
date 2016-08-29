package hm.binkley.layers;

import hm.binkley.layers.Field.IntegerField;
import hm.binkley.layers.Field.StringField;

import java.util.HashMap;
import java.util.Map;

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
    private Layer current;

    public static void main(final String... args) {
        final Character bob = new Character();
        out.println(bob);
        bob.layers.history().forEach(out::println);
    }

    public Character() {
        layers = newLayers(
                s -> new PlayerLayer(s, "Brian", "The Empire of Texas"),
                l -> current = l, PlayerLayer.fields);
        current = current.
                accept(s -> new CharacterLayer(s, "Bob"),
                        CharacterLayer.fields).
                accept(s -> new StatsLayer(s, 15, 14, 13, 12, 10, 8),
                        StatsLayer.fields).
                accept(HumanLayer::new, HumanLayer.fields).
                accept(s -> new CharacterLayer(s, "Sally")). // ex of rename
                accept(s -> new BlankLayer(s, "Scratch"));
    }

    @Override
    public String toString() {
        return layers.toString();
    }

    public static final class PlayerLayer
            extends BlankLayer {
        public static final String PLAYER_NAME = "player:name";
        public static final String PLAYER_CAMPAIGN = "player:campaign";

        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put(PLAYER_NAME, new StringField());
            fields.put(PLAYER_CAMPAIGN, new StringField());
        }

        public PlayerLayer(final Surface surface, final String name,
                final String campaign) {
            super(surface, "Player");
            put(PLAYER_NAME, name);
            put(PLAYER_CAMPAIGN, campaign);
        }
    }

    public static final class CharacterLayer
            extends BlankLayer {
        private static final Map<String, Field> fields = new HashMap<>();

        public static final String CHARACTER_NAME = "character:name";

        static {
            fields.put(CHARACTER_NAME, new StringField());
        }

        public CharacterLayer(final Surface surface, final String name) {
            super(surface, "Character");
            put(CHARACTER_NAME, name);
        }
    }

    public static final class StatsLayer
            extends BlankLayer {
        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("STR", new IntegerField((a, b) -> a + b));
            fields.put("DEX", new IntegerField((a, b) -> a + b));
            fields.put("CON", new IntegerField((a, b) -> a + b));
            fields.put("INT", new IntegerField((a, b) -> a + b));
            fields.put("WIS", new IntegerField((a, b) -> a + b));
            fields.put("CHA", new IntegerField((a, b) -> a + b));
        }

        public StatsLayer(final Surface surface, final int STR, final int DEX,
                final int CON, final int INT, final int WIS, final int CHA) {
            super(surface, "Stats");
            put("STR", STR);
            put("DEX", DEX);
            put("CON", CON);
            put("INT", INT);
            put("WIS", WIS);
            put("CHA", CHA);
        }
    }

    private abstract static class RaceLayer
            extends BlankLayer {
        protected static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("race:name", new StringField());
            fields.put("race:STR-bonus", new StringField());
            fields.put("race:DEX-bonus", new StringField());
            fields.put("race:CON-bonus", new StringField());
            fields.put("race:INT-bonus", new StringField());
            fields.put("race:WIS-bonus", new StringField());
            fields.put("race:CHA-bonus", new StringField());
        }

        protected RaceLayer(final Surface surface, final String raceName) {
            super(surface, "Race");
            put("race:name", raceName);
        }
    }

    public static final class HumanLayer
            extends RaceLayer {
        public HumanLayer(final Surface surface) {
            super(surface, "Human");
            put("STR", 1);
            put("race:STR-bonus", "1");
            put("DEX", 1);
            put("race:DEX-bonus", "1");
            put("CON", 1);
            put("race:CON-bonus", "1");
            put("INT", 1);
            put("race:INT-bonus", "1");
            put("WIS", 1);
            put("race:WIS-bonus", "1");
            put("CHA", 1);
            put("race:CHA-bonus", "1");
        }
    }
}
