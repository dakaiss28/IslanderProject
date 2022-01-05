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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PutBlockTest {

    static Game gameTest;
    static Tile tile;
    static PutBlock putBlock;
    @BeforeEach
    void init() {
         gameTest = new GameImpl();
        try {
            gameTest.setNewMap("mapTest");
        } catch (IOException e) {
            e.printStackTrace();
        }
        tile = new Tile(3,7, Type.GRASS);
        putBlock = new PutBlock(gameTest, Block.HOUSE, tile);
    }

    @Test
    void TestPutBlock() {
        int oldScore = gameTest.getScore();
        putBlock.execute();
        assertEquals(gameTest.getScorLimit(), 30);
        assertEquals(1, gameTest.getPlayer().getInventaire().nbBlock(Block.HOUSE));
        int newScore = gameTest.getScore();
        assertNotEquals(oldScore,gameTest.getScore());
        putBlock.undo();
        assertEquals(gameTest.getScorLimit(), 20);
        assertEquals(oldScore,gameTest.getScore());
        putBlock.redo();
        assertEquals(gameTest.getScorLimit(), 30);
        assertEquals(newScore,gameTest.getScore());


    }
    @Test
    void TestPutBlockException() {
        Tile failTile = new Tile(5,6,Type.TREE);
        assertThrows(UnsupportedOperationException.class, () -> new PutBlock(gameTest, Block.FONTAIN,tile).execute());
        assertThrows(IllegalArgumentException.class, () -> new PutBlock(gameTest,Block.CIRCUS, failTile));
        Tile failTile2 = new Tile(5, 5, Type.GRASS);
        (new PutBlock(gameTest, Block.HOUSE, failTile2)).execute();
        assertThrows(IllegalArgumentException.class, () -> new PutBlock(gameTest, Block.CIRCUS, failTile2));


    }

    @Test
    void getBlock() {assertEquals(putBlock.getBlock(),Block.HOUSE);}

    @Test
    void getScores() {
        putBlock.execute();
        assertEquals(putBlock.getOldScore(),0);
        assertEquals(putBlock.getNewScore(),gameTest.getScore());
    }

}
