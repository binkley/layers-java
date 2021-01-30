package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.rules.BaseRule.BaseRulesLayer;
import hm.binkley.layers.set.LayerSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.item.Attunement.attune;
import static hm.binkley.layers.dnd.item.Attunement.detune;
import static hm.binkley.layers.dnd.item.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.item.Rarity.RARE;
import static hm.binkley.layers.dnd.item.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.item.Rarity.VERY_RARE;
import static hm.binkley.layers.dnd.item.Type.ARMOR;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagicItemsTest {
    private Layers layers;
    private BaseRulesLayer firstLayer;

    @BeforeEach
    void setUpLayersAndFirstLayer() {
        firstLayer = firstLayer(BaseRule::baseRules,
                layers -> this.layers = layers);
    }

    @Test
    void shouldGrant19ConstitutionFromAmuletOfHealth() {
        firstLayer.
                saveAndNext(AmuletOfHealth::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 19, layers.get(CON));
    }

    @Test
    void shouldGrant21StrengthFromHillGiantGirdle() {
        firstLayer.
                saveAndNext(BeltOfHillGiantStrength::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 21, layers.get(STR));
    }

    @Test
    void shouldGrant23StrengthFromStoneGiantGirdle() {
        firstLayer.
                saveAndNext(BeltOfStoneGiantStrength::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldGrant23StrengthFromFrostGiantGirdle() {
        firstLayer.
                saveAndNext(BeltOfFrostGiantStrength::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldGrant25StrengthFromFireGiantGirdle() {
        firstLayer.
                saveAndNext(BeltOfFireGiantStrength::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 25, layers.get(STR));
    }

    @Test
    void shouldGrant27StrengthFromCloudGiantGirdle() {
        firstLayer.
                saveAndNext(BeltOfCloudGiantStrength::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 27, layers.get(STR));
    }

    @Test
    void shouldGrant29StrengthFromStormGiantGirdle() {
        firstLayer.
                saveAndNext(BeltOfStormGiantStrength::new).
                attuneSaveAndNext(ScratchLayer::new);

        assertEquals((Integer) 29, layers.get(STR));
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
    void shouldDisplayTypeForArmor() {
        assertEquals("Armor", ARMOR.toString());
    }

    @Test
    void shouldDisplayTypeForWonderousItem() {
        assertEquals("Wondrous Item", WONDROUS_ITEM.toString());
    }

    @Test
    void shouldDisplayAttunementBriefly() {
        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);
        final Attunement attunement = amuletOfHealth.
                saveAndNext(attune(amuletOfHealth));
        attunement.
                saveAndNext(ScratchLayer::new);
        final String display = attunement.toString();

        assertTrue(display.contains(amuletOfHealth.name()));
    }

    @Test
    void shouldNotDisplayAttunementVerbosely() {
        final AmuletOfHealth amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new);
        final Attunement attunement = amuletOfHealth.
                saveAndNext(attune(amuletOfHealth));
        attunement.
                saveAndNext(ScratchLayer::new);
        final String display = attunement.toString();

        assertFalse(display.contains(
                amuletOfHealth.details().get("Description").toString()),
                () -> "Attunement layer overly verbose: " + display);
    }

    @Test
    void shouldBeAbleToAttune3Items() { // TODO: Distinct items
        firstLayer.
                saveAndNext(AmuletOfHealth::new).
                attuneSaveAndNext(BeltOfHillGiantStrength::new).
                attuneSaveAndNext(BeltOfStoneGiantStrength::new).
                attuneSaveAndNext(BeltOfFrostGiantStrength::new);

        assertEquals(3, layers.<LayerSet<?>>get(Attunement.class).size());
    }

    @Test
    void shouldLimitAttunementTo3Items() { // TODO: Distinct items
        final AttunementItem<?> beltOfFrostGiantStrength = firstLayer.
                saveAndNext(AmuletOfHealth::new).
                attuneSaveAndNext(BeltOfHillGiantStrength::new).
                attuneSaveAndNext(BeltOfStoneGiantStrength::new).
                attuneSaveAndNext(BeltOfFrostGiantStrength::new).
                self();

        assertThrows(IllegalStateException.class,
                () -> beltOfFrostGiantStrength.
                        attuneSaveAndNext(ScratchLayer::new));
    }

    @Test
    void shouldBeAbleToDetuneAttunedItem() {
        final AttunementItem<?> amuletOfHealth = firstLayer.
                saveAndNext(AmuletOfHealth::new).
                self();

        amuletOfHealth.
                attuneSaveAndNext(detune(amuletOfHealth)).
                saveAndNext(ScratchLayer::new);

        assertFalse(amuletOfHealth.isAttuned());
    }
}
