package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.dnd.MagicItems.Attunement;
import hm.binkley.layers.dnd.MagicItems.MagicItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static hm.binkley.layers.LayerSet.singleton;
import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.MagicItems.Attune.attune;
import static hm.binkley.layers.dnd.MagicItems.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Attunement.UNATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.MagicItems.Rarity.RARE;
import static hm.binkley.layers.dnd.MagicItems.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.MagicItems.Rarity.VERY_RARE;
import static hm.binkley.layers.dnd.MagicItems.Type.ARMOR;
import static hm.binkley.layers.dnd.MagicItems.Type.WONDROUS_ITEM;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagicItemsTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayersAndFirstLayer() {
        firstLayer = firstLayer(BaseRule::baseRules,
                layers -> this.layers = layers);
    }

    @Test
    void shouldGrant19ConstitutionFromAmuletOfHealth() {
        firstLayer.saveAndNext(MagicItems::amuletOfHealth).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 19, layers.get(CON));
    }

    @Test
    void shouldGrant21StrengthFromHillGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 21, layers.get(STR));
    }

    @Test
    void shouldGrant23StrengthFromStoneGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfStoneGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldGrant23StrengthFromFrostGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfFrostGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldGrant25StrengthFromFireGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfFireGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 25, layers.get(STR));
    }

    @Test
    void shouldGrant27StrengthFromCloudGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfCloudGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 27, layers.get(STR));
    }

    @Test
    void shouldGrant29StrengthFromStormGiantGirdle() {
        firstLayer.saveAndNext(MagicItems::beltOfStormGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 29, layers.get(STR));
    }

    @Test
    void shouldCreateAdamantineArmor() {
        assertEquals("Adamantine Armor",
                firstLayer.saveAndNext(MagicItems::adamantineArmor).name());
    }

    @Test
    void shouldDisplayRarityForUncommon() {
        assertEquals("Uncommon", UNCOMMON.toString());
    }

    @Test
    void shouldDisplayRarityForRare() {
        assertEquals("Rare", RARE.toString());
    }

    @Test
    void shouldDisplayRarityForVeryRare() {
        assertEquals("Very rare", VERY_RARE.toString());
    }

    @Test
    void shouldDisplayRarityForLegendary() {
        assertEquals("Legendary", LEGENDARY.toString());
    }

    @Test
    void shouldDisplayAttunementForYes() {
        assertEquals("yes", ATTUNED.toString());
    }

    @Test
    void shouldDisplayAttunementForNo() {
        assertEquals("", UNATTUNED.toString());
    }

    @Test
    void shouldDisplayTypeForArmor() {
        assertEquals("Armor", ARMOR.toString());
    }

    @Test
    void shouldDisplayTypeForWonderousItem() {
        assertEquals("Wondrous Item", WONDROUS_ITEM.toString());
    }

    @Test
    void shouldDisplayAttunementNicely() {
        final Layer amuletOfHealth = firstLayer.
                saveAndNext(MagicItems::amuletOfHealth);
        final Layer attunement = amuletOfHealth.
                saveAndNext(ScratchLayer::new).
                put(Attunement.class, ofValue(singleton(amuletOfHealth)));
        attunement.
                saveAndNext(ScratchLayer::new);
        final String display = attunement.toString();

        assertAll(() -> assertTrue(display.contains(amuletOfHealth.name())),
                () -> assertFalse(display.contains(
                        amuletOfHealth.details().get("Description")
                                .toString())));
    }

    @Test
    void shouldBeAbleToAttune3Items() { // TODO: Distinct items
        final MagicItem amuletOfHealth = firstLayer.
                saveAndNext(MagicItems::amuletOfHealth);
        final MagicItem beltOfHillGiantStrength = amuletOfHealth.
                saveAndNext(attune(amuletOfHealth)).
                saveAndNext(MagicItems::beltOfHillGiantStrength);
        final MagicItem beltOfStoneGiantStrength = beltOfHillGiantStrength.
                saveAndNext(MagicItems::beltOfStoneGiantStrength);
        beltOfStoneGiantStrength.
                saveAndNext(MagicItems::beltOfFrostGiantStrength);

        beltOfHillGiantStrength.attuneAndNext(ScratchLayer::new);
        beltOfStoneGiantStrength.attuneAndNext(ScratchLayer::new);

        assertEquals(3, layers.<Set<Layer>>get(Attunement.class).size());
    }

    @Test
    void shouldLimitAttunementTo3Items() { // TODO: Distinct items
        final MagicItem amuletOfHealth = firstLayer.
                saveAndNext(MagicItems::amuletOfHealth);
        final MagicItem beltOfHillGiantStrength = amuletOfHealth.
                saveAndNext(attune(amuletOfHealth)).
                saveAndNext(MagicItems::beltOfHillGiantStrength);
        final MagicItem beltOfStoneGiantStrength = beltOfHillGiantStrength.
                saveAndNext(MagicItems::beltOfStoneGiantStrength);
        final MagicItem beltOfFrostGiantStrength = beltOfStoneGiantStrength.
                saveAndNext(MagicItems::beltOfFrostGiantStrength);

        beltOfHillGiantStrength.attuneAndNext(ScratchLayer::new);
        beltOfStoneGiantStrength.attuneAndNext(ScratchLayer::new);

        assertThrows(IllegalStateException.class,
                () -> beltOfFrostGiantStrength
                        .attuneAndNext(ScratchLayer::new));
    }
}
