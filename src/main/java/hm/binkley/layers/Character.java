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
        out.println(new Character("Brian", "Bob"));
    }

    public Character(final String player, final String name) {
        layers = newLayers(surface -> new BaseLayer(surface, player, name),
                l -> current = l, BaseLayer.fields);
        current = current.
                accept(s -> new StatsLayer(s, 15, 14, 13, 12, 10, 8),
                        StatsLayer.fields).
                accept(HumanLayer::new, HumanLayer.fields).
                accept(BlankLayer::new);
    }

    @Override
    public String toString() {
        return layers.toString();
    }

    public static final class BaseLayer
            extends BlankLayer {
        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("player", new StringField());
            fields.put("name", new StringField());
        }

        public BaseLayer(final Surface surface, final String player,
                final String name) {
            super(surface);
            put("player", player);
            put("name", name);
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
            super(surface);
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
            super(surface);
            put("race:name", raceName);
        }
    }

    public static final class HumanLayer
            extends RaceLayer {
        public HumanLayer(final Surface surface) {
            super(surface, "name:human");
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
            put("CAH", 1);
            put("race:CHA-bonus", "1");
        }
    }
}
