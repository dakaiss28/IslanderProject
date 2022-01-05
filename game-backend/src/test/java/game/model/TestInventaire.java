package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestInventaire {

    public static Inventaire i;

    @BeforeEach
    void init() {
        i = new Inventaire();
    }

    @Test
    void testUpdateStock(){
        i.updateStock();

        Map<Block, Integer> expected = new HashMap<>();
        expected.put(Block.HOUSE, 2);
        expected.put(Block.WIND_TURBINE, 1);
        expected.put(Block.CIRCUS, 1);
        expected.put(Block.FONTAIN, 1);

        assertEquals(expected, i.stock);
    }

    @Test
    void testRemoveBlock() {
        i.removeBlock(Block.HOUSE);

        Map<Block, Integer> expected = new HashMap<>();
        expected.put(Block.HOUSE, 0);
        expected.put(Block.WIND_TURBINE, 0);
        expected.put(Block.CIRCUS, 0);
        expected.put(Block.FONTAIN, 0);

        assertEquals(expected, i.stock);
    }

    @Test
    void testRemoveBlockAlready0() {
        assertThrows(UnsupportedOperationException.class, () -> i.removeBlock(Block.CIRCUS));
    }

    @Test
    void testAddBlock() {
        i.addBlock(Block.HOUSE);

        Map<Block, Integer> expected = new HashMap<>();
        expected.put(Block.HOUSE, 2);
        expected.put(Block.WIND_TURBINE, 0);
        expected.put(Block.CIRCUS, 0);
        expected.put(Block.FONTAIN, 0);

        assertEquals(expected, i.stock);
    }

}
