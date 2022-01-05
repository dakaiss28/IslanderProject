package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestTile {

    public static Tile t;

    @BeforeEach
    void init() {
        t = new Tile(1, 1, Type.GRASS);
    }

    @Test
    void getBlock() {
        assertEquals(Block.NOTHING, t.getBlock());
    }

    @Test
    void setBlock() {
        t.setBlock(Block.CIRCUS);
        assertEquals(Block.CIRCUS, t.getBlock());
    }

    @Test
    void getOccupied() {
        assertFalse(t.getOccupied());
    }

    @Test
    void getX() {
        assertEquals(1, t.getX());
    }

    @Test
    void getY() {
        assertFalse(t.getOccupied());
    }

    @Test
    void setOccupied() {
        t.setOccupied(true);
        assertTrue(t.getOccupied());
    }

    @Test
    void getType() {
        assertEquals(Type.GRASS, t.getType());
    }


    @Test
    void testEquals(){
        assertEquals(new Tile(1, 1, Type.GRASS),  t);
    }

    @Test
    void testNotEquals_x(){
        assertNotEquals(new Tile(2, 1, Type.GRASS),  t);
    }

    @Test
    void testNotEquals_y(){
        assertNotEquals(new Tile(1, 4, Type.GRASS),  t);
    }

    @Test
    void testNotEquals_type(){
        assertNotEquals(new Tile(1, 1, Type.WATER),  t);
    }

    @Test
    void testNotEquals(){
        assertFalse(t.equals(new Storage()));
    }

    @Test
    public void testHashCode() {
        Tile t2 = new Tile(1, 1, Type.GRASS);
        Map <Tile, String> map = new HashMap<>();
        map.put(t2, "test");
        assertEquals("test", map.get(t2));
    }

}
