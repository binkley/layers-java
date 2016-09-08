package hm.binkley.layers.dnd;

import hm.binkley.layers.Layers;

import java.time.ZonedDateTime;

import static hm.binkley.layers.Layers.newLayers;
import static hm.binkley.layers.dnd.CharacterLayer.characterLayer;
import static hm.binkley.layers.dnd.FreeFormLayer.freeFormLayer;
import static hm.binkley.layers.dnd.HumanLayer.humanLayer;
import static hm.binkley.layers.dnd.PlayerLayer.playerLayer;
import static hm.binkley.layers.dnd.StatsLayer.statsLayer;
import static java.lang.System.out;

/**
 * {@code Character} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Character {
    private final Layers layers;
    private final ZonedDateTime started;
    private AnnotatedLayer<?> current;

    public static void main(final String... args) {
        final Character bob = new Character();
        out.println(bob);
        out.println(bob.started);
        out.println(bob.current);
        bob.layers.history().
                forEach(out::println);
    }

    private Character() {
        layers = newLayers(playerLayer("Starting a new game", "Brian",
                "The Empire of Texas"), l -> current = l, PlayerLayer.fields);
        final CharacterLayer bob = current.
                accept(characterLayer("Note #2", "Bob"),
                        CharacterLayer.fields);
        started = current.accepted();
        current = bob.
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
}
