package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestPlayerImpl {

    public static PlayerImpl p;

    @BeforeEach
    void init() {
        p = new PlayerImpl("player1");
    }

    @Test
    void testGetName(){
        assertEquals("player1", p.getName());
    }

    @Test
    void testSetName(){
        p.setName("playerModified");
        assertEquals("playerModified", p.getName());
    }


    @Test
    void testGetInventaire(){
        assertEquals(new Inventaire().stock, p.getInventaire().stock);
    }

}
