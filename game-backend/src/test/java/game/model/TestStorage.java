package game.model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestStorage {

    public static Storage s;

    @BeforeEach
    void init() {
        s = new Storage();
    }

    @Test
    void testGetMap(){

        Map map1 = new MapImpl("map1");
        s.addMap(map1);
        assertEquals("map1", s.getMap("map1").getMapName());
    }

    @Test
    void testGetMapFile(){
        assertEquals(new File("maps.json"), s.getMapFile());
    }

    @Test
    void testsetMapFileandUpdateList() throws IOException {

        File mapFile = new File("testFile.txt");
        FileWriter out = new FileWriter(mapFile);
        out.write("[{\"type\":\"game.model.MapImpl\",\"mapName\":\"providedMap1\",\"tiles\":[{\"occupied\":true,\"x\":0,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":1,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":4,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":0,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":1,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":2,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":3,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":3,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":1,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":5,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":3,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":4,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":6,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":5,\"y\":8,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":5,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":8,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":1,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":2,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":5,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":9,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":9,\"type\":\"TREE\",\"block\":\"NOTHING\"}],\"scores\":[]}]");
        out.close();

        s.setMapFileandUpdateList(mapFile);

        assertEquals(new File("testFile.txt"), s.getMapFile());
        assertEquals("providedMap1", s.getMaps().get(0).getMapName());

        mapFile.delete();

    }





}
