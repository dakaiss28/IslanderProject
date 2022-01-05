package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestScore {

    public static Score s;

    @BeforeEach
    void init() {
       s = new Score("player1", 20);
    }

    @Test
    void testGetPlayer(){
       assertEquals("player1", s.getPlayer());
    }

    @Test
    void testGetScore(){
        assertEquals(20, s.getScore());
    }

    @Test
    void testSetPlayer(){
        s.setPlayer("player2");
        assertEquals("player2", s.getPlayer());
    }

    @Test
    void testChangeScore(){
        assertEquals(40,  s.changeScore(40).getScore());
    }

    @Test
    void testEquals(){
        assertEquals(new Score("player1", 20),  s);
    }

    @Test
    void testNotEquals_name(){
        assertNotEquals(new Score("player2", 20),  s);
    }

    @Test
    void testNotEquals_score(){
        assertNotEquals(new Score("player1", 50),  s);
    }

    @Test
    void testNotEquals(){
        assertFalse(s.equals(new Storage()));
    }

    @Test
    public void testHashCode() {
        Score s2 = new Score("player1", 20);
        Map<Score, String> map = new HashMap<>();
        map.put(s2, "test");
        assertEquals("test", map.get(s2));
    }
    
}
