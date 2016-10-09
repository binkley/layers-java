package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.MagicItems.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Attunement.UNATTUNED;
import static hm.binkley.layers.dnd.MagicItems.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.MagicItems.Rarity.RARE;
import static hm.binkley.layers.dnd.MagicItems.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.MagicItems.Rarity.VERY_RARE;
import static hm.binkley.layers.dnd.MagicItems.Type.ARMOR;
import static hm.binkley.layers.dnd.MagicItems.Type.WONDROUS_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MagicItemsTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayersAndFirstLayer() {
        firstLayer = firstLayer(Bases::baseRules,
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
    void shouldDiplayRarityForUncommon() {
        assertEquals("Uncommon", UNCOMMON.toString());
    }

    @Test
    void shouldDiplayRarityForRare() {
        assertEquals("Rare", RARE.toString());
    }

    @Test
    void shouldDiplayRarityForVeryRare() {
        assertEquals("Very rare", VERY_RARE.toString());
    }

    @Test
    void shouldDiplayRarityForLegendary() {
        assertEquals("Legendary", LEGENDARY.toString());
    }

    @Test
    void shouldDiplayAttunementForYes() {
        assertEquals("yes", ATTUNED.toString());
    }

    @Test
    void shouldDiplayAttunementForNo() {
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
}
