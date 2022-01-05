package game.model;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestBlock {

    @Test
    void testRadiusEffectBlockCircus() {
        assertEquals(3, Block.CIRCUS.radiusEffect());
    }

    @Test
    void testRadiusEffectBlockOther() {
        assertEquals(1, Block.HOUSE.radiusEffect());
    }

}
