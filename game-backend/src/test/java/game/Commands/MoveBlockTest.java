package game.Commands;

import game.model.Block;
import game.model.Game;
import game.model.GameImpl;
import game.model.Tile;
import game.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveBlockTest {
    static Game gameTest;
    static Tile oldtile;
    static Tile newtile;
    static MoveBlock moveBlock;

    @BeforeEach
    void init() {
        gameTest = new GameImpl();
        try {
            gameTest.setNewMap("mapTest");
        } catch (IOException e) {
            e.printStackTrace();
        }
        oldtile = new Tile(5,7, Type.GRASS);
        newtile = new Tile(6,6,Type.GRASS);
        PutBlock putBlock = new PutBlock(gameTest, Block.HOUSE, oldtile);
        putBlock.execute();
        moveBlock = new MoveBlock(gameTest, oldtile, newtile);
    }

    @Test
    void TestMoveBlock() {
        int oldScore = gameTest.getScore();
        moveBlock.execute();
        int newScore = gameTest.getScore();
        assertEquals(oldScore,moveBlock.getOldScore());
        assertEquals(newScore,moveBlock.getNewScore());

    }

    @Test
    void testGetElement() { assertEquals(Block.HOUSE, moveBlock.getElement());}

    @Test
    void testException() {
        Tile test = new Tile(1,2,Type.TREE);
        assertThrows(IllegalArgumentException.class, () -> new MoveBlock(gameTest,oldtile,test));
        Tile test2 = new Tile(5, 5, Type.GRASS);
        (new PutBlock(gameTest, Block.HOUSE, test2)).execute();
        assertThrows(IllegalArgumentException.class, () -> new MoveBlock(gameTest, oldtile, test2));
    }
}
