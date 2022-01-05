package game.model;

import game.Commands.Command;
import game.Commands.MoveBlock;
import game.Commands.PutBlock;
import game.Commands.RenamePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class TestGameImpl {

    public static Game g;
    public static Tile t_grass;
    public static Tile t_water;

    @BeforeEach
    void init() throws IOException {
        g = new GameImpl();
        t_grass = new Tile(1, 1, Type.GRASS);
        t_water = new Tile(1, 1, Type.WATER);

        File mapFile = new File("maps.json");
        FileWriter out = new FileWriter(mapFile);
        out.write("[]");
        out.close();
    }

    @Test
    void testUpdateScoreLimit() {
        assertEquals(20, g.updateScoreLimit());
    }

    @Test
    void testIncreaseScoreBlockCircus() {
        assertEquals(10, g.increaseScore(Block.CIRCUS));
    }

    @Test
    void testIncreaseScoreBlockHouse() {
        assertEquals(5, g.increaseScore(Block.HOUSE));
    }

    @Test
    void testIncreaseScoreBlockFountain() {
        assertEquals(4, g.increaseScore(Block.FONTAIN));
    }

    @Test
    void testIncreaseScoreBlockWind() {
        assertEquals(8, g.increaseScore(Block.WIND_TURBINE));
    }

    @Test
    void testComputeScoreFountainBlock_with_TreeTileNeighbour() {
        assertEquals(4, g.computeScore(new Tile(1, 1, Type.TREE), Block.FONTAIN));
    }

    @Test
    void testComputeScoreWindTurbineBlock_with_TreeTileNeighbour() {
        assertEquals(-5, g.computeScore(new Tile(1, 1, Type.TREE), Block.WIND_TURBINE));

    }

    @Test
    void testComputeScoreFountainBlock_with_HouseBlockNeighbour() {
        t_grass.setBlock(Block.HOUSE);
        assertEquals(5, g.computeScore(t_grass, Block.FONTAIN));
    }

    @Test
    void testComputeScoreFountainBlock_with_CircusBlockNeighbour() {
        t_grass.setBlock(Block.CIRCUS);
        assertEquals(6, g.computeScore(t_grass, Block.FONTAIN));
    }

    @Test
    void testComputeScoreHouseBlock_with_FountainNeighbour() {
        t_grass.setBlock(Block.FONTAIN);
        assertEquals(10, g.computeScore(t_grass, Block.HOUSE));
    }

    @Test
    void testComputeScoreHouseBlock_with_HouseNeighbour() {
        t_grass.setBlock(Block.HOUSE);
        assertEquals(5, g.computeScore(t_grass, Block.HOUSE));
    }

    @Test
    void testComputeScoreHouseBlock_with_WindTurbineNeighbour() {
        t_grass.setBlock(Block.WIND_TURBINE);
        assertEquals(-10, g.computeScore(t_grass, Block.HOUSE));
    }

    @Test
    void testComputeScoreHouseBlock_with_CircusNeighbour() {
        t_grass.setBlock(Block.CIRCUS);
        assertEquals(8, g.computeScore(t_grass, Block.HOUSE));
    }

    @Test
    void testComputeScoreWindTurbineBlock_with_WaterNeighbour() {
        assertEquals(8, g.computeScore(t_water, Block.WIND_TURBINE));
    }

    @Test
    void testComputeScoreWindTurbineBlock_with_HouseNeighbour() {
        t_grass.setBlock(Block.HOUSE);
        assertEquals(-7, g.computeScore(t_grass, Block.WIND_TURBINE));
    }

    @Test
    void testComputeScoreCircusBlock_with_HouseNeighbour() {
        t_grass.setBlock(Block.HOUSE);
        assertEquals(10, g.computeScore(t_grass, Block.CIRCUS));
    }

    @Test
    void testComputeScoreCircusBlock_with_CircusNeighbour() {
        t_grass.setBlock(Block.CIRCUS);
        assertEquals(-20, g.computeScore(t_grass, Block.CIRCUS));
    }

    @Test
    void testStartGame() throws IOException {
        Storage data = new Storage();
        Game game = new GameImpl(data);
        game.startGame("mapTest");
        assertEquals("mapTest", game.getCurrentMap().getMapName());
        Map newMap = new MapImpl("newMap");
        game.postNewMap(newMap);
        game.startGame("newMap");
        assertEquals(newMap, game.getCurrentMap());

    }
    @Test
    void testEndGameAllOccupied() throws IOException {

        g.setNewMap("map1");
        g.getCurrentMap().getTiles().stream()
                .forEach(tile -> tile.setOccupied(true));

        assertTrue(g.endGame(false));
    }

    @Test
    void testEndGameExitTrue() throws IOException {
        g.setNewMap("map1");

        assertTrue(g.endGame(true));
    }



    @Test
    void testUpdateScore() throws IOException {

        List<Tile> listGrass = new ArrayList<>();

        final int[] numsX = java.util.stream.IntStream.rangeClosed(0, 9).toArray();
        final int[] numsY = java.util.stream.IntStream.rangeClosed(0, 9).toArray();

        Arrays.stream(numsX)
                .forEach(numx -> Arrays.stream(numsY).forEach(
                        numy -> listGrass.add(new Tile(numx, numy, Type.GRASS))
                        )
                );

        Map mapGrass = new MapImpl("mapGrass", listGrass);

        g.postNewMap(mapGrass);
        g.setMap("mapGrass");

        assertEquals(8, g.updateScore(mapGrass.getTile(1,1), Block.WIND_TURBINE));

    }

    @Test
    void testGetMaps() throws IOException {
        Map map = new MapImpl("map1");
        g.getStorage().addMap(map);

        List<Map> list = new ArrayList<>();
        list.add(map);

        assertEquals(list.get(0).getMapName(), g.getMaps().get(0).getMapName());
    }

    @Test
    void testGetCurrentMap() {
        Map map = new MapImpl("map1");
        g.getStorage().addMap(map);
        Map map2 = new MapImpl("map2");
        g.getStorage().addMap(map2);

        g.setMap("map1");
        assertEquals("map1", g.getCurrentMap().getMapName());
    }

    @Test
    void testSetMap() {
        Map map = new MapImpl("map1");
        g.getStorage().addMap(map);
        Map map2 = new MapImpl("map2");
        g.getStorage().addMap(map2);

        g.setMap("map1");
        assertEquals("map1", g.getCurrentMap().getMapName());
    }

    @Test
    void testPostNewMap() throws IOException {
        Map map = new MapImpl("map1");
        g.postNewMap(map);

        assertEquals("map1", g.getStorage().getMap("map1").getMapName());
    }

    @Test
    void testGetPlayer() {

        Player p = new PlayerImpl("playertest");
        Game g2 = new GameImpl(p);

        assertEquals("playertest", g2.getPlayer().getName());
        assertEquals(new Inventaire().stock, g2.getPlayer().getInventaire().stock);
    }

    @Test
    void testPlayTurn_With_RenamePlayer() {
        Game game = new GameImpl();
        Player player = game.getPlayer();
        String RandomName = player.getName();
        Command renamer = new RenamePlayer(game, "toto");
        game.playTurn(renamer);
        assertEquals(((RenamePlayer)renamer).getNewName(),player.getName());
        game.getCollector().undo();
        assertEquals(player.getName(),RandomName);
        game.getCollector().redo();
        assertEquals(player.getName(),"toto");

        assertEquals(0,game.getCollector().getNbRedoables());
        assertEquals(1,game.getCollector().getNbUndoables());

    }

    @Test
    void testPlayTurn_With_PutBlock() {
        Game game = new GameImpl();
        try {
            game.setNewMap("MapTest");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tile tileTest = Mockito.mock(Tile.class);
        Mockito.when(tileTest.getType()).thenReturn(Type.GRASS);
        Mockito.when(tileTest.getOccupied()).thenReturn(false);


        Command putBlock = new PutBlock(game,Block.HOUSE,tileTest);
        game.playTurn(putBlock);

        assertNotEquals(0,game.getScore());
        assertEquals(40,game.getScorLimit());

        game.getCollector().undo();
        assertFalse(((PutBlock) putBlock).getTile().getOccupied());
        assertEquals(0,game.getScore());

        Tile mockTile = Mockito.mock(Tile.class);
        Mockito.when(mockTile.getType()).thenReturn(Type.TREE);
        assertThrows(IllegalArgumentException.class, () -> new PutBlock(game,Block.HOUSE,mockTile));

    }

    @Test
    void TestPlayTurn_With_MoveBlock() throws IOException {
        Game game = new GameImpl();

        File mapFile = game.getStorage().getMapFile();
        FileWriter out = new FileWriter(mapFile);
        out.write("[{\"type\":\"game.model.MapImpl\",\"mapName\":\"providedMap1\",\"tiles\":[{\"occupied\":true,\"x\":0,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":1,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":4,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":0,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":1,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":2,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":3,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":3,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":1,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":5,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":3,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":4,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":6,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":5,\"y\":8,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":5,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":8,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":1,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":2,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":5,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":9,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":9,\"type\":\"TREE\",\"block\":\"NOTHING\"}],\"scores\":[]}]");
        out.close();

        game.getStorage().setMapFileandUpdateList(mapFile);

        game.setMap("providedMap1");

        Tile tileOldMock = new Tile(5,7,Type.GRASS);
        Tile tileNewMock = new Tile(6,7,Type.GRASS);
        Command putBlock = new PutBlock(game,Block.HOUSE, tileOldMock);
        game.playTurn(putBlock);
        int oldScore = game.getScore();

        Command moveBlock = new MoveBlock(game,tileOldMock,tileNewMock);
        game.playTurn(moveBlock);

        assertFalse(((MoveBlock) moveBlock).getOldTile().getOccupied());
        assertTrue(((MoveBlock) moveBlock).getNewTile().getOccupied());
        assertEquals(((MoveBlock) moveBlock).getOldTile().getBlock(),Block.NOTHING);
        assertEquals(((MoveBlock) moveBlock).getNewTile().getBlock(),Block.HOUSE);
        assertNotEquals(oldScore,game.getScore());

        game.getCollector().undo();
        assertEquals(oldScore,game.getScore());
        game.getCollector().redo();
        assertNotEquals(oldScore,game.getScore());


    }
}







