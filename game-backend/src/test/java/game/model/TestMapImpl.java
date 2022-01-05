package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class TestMapImpl {

    public static MapImpl map;
    public static PlayerImpl p;
    public static Score s_expected;

    @BeforeEach
    void init() {
       map = new MapImpl("map1");
       p=new PlayerImpl();
       p.setName("player1");
       s_expected = new Score(p.getName(), 2000);

       File file = map.getScoreFile();
       file.delete();
    }

    @Test
    void testGetTile(){
        Tile t = map.getTile(1,2);

        assertEquals(1,t.getX());
        assertEquals(2,t.getY());

    }

    @Test
    void testGetTileNoSuchElement(){
        assertThrows(NoSuchElementException.class,()-> map.getTile(-1,-3));
    }


    @Test
    void testGetMapName(){
        assertEquals("map1", map.getMapName());
    }

    @Test
    void testSetMapName(){
        map.setMapName("mapModified");
        assertEquals("mapModified", map.getMapName());
    }

    static class Coord{
        final private int _x;
        final private int _y;
        Coord(int x, int y){
            _x = x;
            _y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coord){
                return _x==((Coord) obj)._x && _y == ((Coord) obj)._y;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            return "("+_x+","+_y+")";
        }
    }

    @Test
    void testGetNeighbours(){
        Tile t = map.getTile(1,2);
        List<Tile> res = map.getNeighbours(t,1);

        List<Coord> list = new ArrayList<>();

        for(Tile tile : res){
            list.add(new Coord(tile.getX(), tile.getY()));
        }

        assertEquals("[(0,1), (0,2), (0,3), (1,1), (1,3), (2,1), (2,2), (2,3)]",list.toString());

    }

    @Test
    void testGetNeighboursEdge(){
        Tile t = map.getTile(0,0);
        List<Tile> res = map.getNeighbours(t,1);

        List<Coord> list = new ArrayList<>();

        for(Tile tile : res){
            list.add(new Coord(tile.getX(), tile.getY()));
        }

        assertEquals("[(0,1), (1,0), (1,1)]",list.toString());

    }

    @Test
    void testGetNeighboursEdge2(){
        Tile t = map.getTile(9,9);
        List<Tile> res = map.getNeighbours(t,1);

        List<Coord> list = new ArrayList<>();

        for(Tile tile : res){
            list.add(new Coord(tile.getX(), tile.getY()));
        }

        assertEquals("[(8,8), (8,9), (9,8)]",list.toString());

    }

    @Test
    void testPostNewScore() throws IOException {
        map.postNewScore(p.getName(),2000);

        File fileToRead = map.getScoreFile();
        File testFile = new File("./testfiles/testScore1.txt");

        byte[] f1 = Files.readAllBytes(fileToRead.toPath());
        byte[] f2 = Files.readAllBytes(testFile.toPath());

        assertTrue(Arrays.equals(f1,f2));
    }

    @Test
    void testPostmultipleNewScore() throws IOException {
        map.postNewScore(p.getName(),2000);

        PlayerImpl p2=new PlayerImpl();
        p2.setName("player2");
        map.postNewScore(p2.getName(),4000);

        File fileToRead = map.getScoreFile();
        File testFile = new File("./testfiles/testScore2.txt");

        byte[] f1 = Files.readAllBytes(fileToRead.toPath());
        byte[] f2 = Files.readAllBytes(testFile.toPath());

        assertTrue(Arrays.equals(f1,f2));
    }

    @Test
    void testPostmultipleNewScoreSamePlayer() throws IOException {
        map.postNewScore(p.getName(),2000);
        map.postNewScore(p.getName(),4000);

        File fileToRead = map.getScoreFile();
        File testFile = new File("./testfiles/testScore3.txt");

        byte[] f1 = Files.readAllBytes(fileToRead.toPath());
        byte[] f2 = Files.readAllBytes(testFile.toPath());

        assertTrue(Arrays.equals(f1,f2));
    }

    @Test
    void testGetScore() {
        map.postNewScore(p.getName(),2000);
        Score ret = map.getScore(p.getName());
        assertEquals(s_expected, ret);
    }

    @Test
    void testGetScoreNull() {
        Score ret = map.getScore(p.getName());
        assertNull(ret);
    }

    @Test
    void testGetScores() throws IOException {
        map.postNewScore(p.getName(),2000);
        List<Score> list = map.getScores();
        assertEquals(s_expected, list.get(0));

    }

    @Test
    void testGetScoreFile() {
        assertEquals(new File("./scores/scoresmap1.json"), map.getScoreFile());

    }

}
