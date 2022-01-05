package game.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import game.serialization.OptionalAdapter;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MapImpl implements Map {
    private  String mapName;
    private final List<Tile> tiles;
    private final List<Score> scores;

    @JsonIgnore
    File scoreFile;


    public MapImpl() {
        mapName = "";
        tiles = new ArrayList<>();
        scores = new ArrayList<>();
        initializeMap();
        scoreFile = new File("./scores/scores" + mapName + ".json");

        try (FileWriter out = new FileWriter(scoreFile)) {
            out.write("[]");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MapImpl(final String name) {
        mapName = name;
        tiles = new ArrayList<>();
        scores = new ArrayList<>();
        initializeMap();

        scoreFile = new File("./scores/scores" + mapName + ".json");

        try (FileWriter out = new FileWriter(scoreFile)) {
            out.write("[]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapImpl(final String name, final List<Tile> list) {
        mapName = name;
        tiles = list;
        scores = new ArrayList<>();

        scoreFile = new File("./scores/scores" + mapName + ".json");

        try (FileWriter out = new FileWriter(scoreFile)) {
            out.write("[]");
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    /**
     * randomly initialize a map
     */

    @Override
    public void initializeMap() {
        final int[] numsX = java.util.stream.IntStream.rangeClosed(0, 9).toArray();
        final int[] numsY = java.util.stream.IntStream.rangeClosed(0, 9).toArray();

        Arrays.stream(numsX)
                .forEach(numx -> Arrays.stream(numsY).forEach(
                        numy -> tiles.add(new Tile(numx, numy, Tile.getRandomType()))
                        )
                );
    }

    /**
     *
     * @param tile the given tile
     * @param radius the radius to consider on the map
     * @return a list of the neighbours of the given tile on the map
     */

    @Override
    public List<Tile> getNeighbours(final Tile tile, final int radius) {
        final int x = tile.getX();
        final int y = tile.getY();
        final List<Tile> res = new ArrayList<>();

        int minX = x - radius;
        if (minX < 0) {
            minX = 0;
        }

        int maxX = x + radius;
        if (maxX > 9) {
            maxX = 9;
        }

        int minY = y - radius;
        if (minY < 0) {
            minY = 0;
        }

        int maxY = y + radius;
        if (maxY > 9) {
            maxY = 9;
        }

        final int[] numsX = java.util.stream.IntStream.rangeClosed(minX, maxX).toArray();
        final int[] numsY = java.util.stream.IntStream.rangeClosed(minY, maxY).toArray();

        Arrays.stream(numsX)
                .forEach(numx ->
                        Arrays.stream(numsY)
                        .forEach(
                            numy -> {
                                if(numx != x || numy != y) {
                                    res.add(getTile(numx, numy));
                                }
                            }
                )
        );

        return res;

    }

    @Override
    public Score getScore(final String player) {
        return scores
                .stream()
                .filter(score -> score.player.equals(player))
                .findFirst()
                .orElse(null);

    }

    @Override
    public List<Score> getScores() throws IOException {
        return OptionalAdapter.fromJSONtoScores(new File("./scores/scores" + mapName + ".json"));
        //return scores;
    }

    /**
     *
     * @param player the name of the player
     * @param score the score achieved by the player
     *   for every player, we only save the highest score achieved on a given map
     */

    @Override
    public void postNewScore(final String player, final int score) {
        scoreFile = new File("./scores/scores" + mapName + ".json");
        final Score sc = new Score(player, score);
        scores
                .stream()
                .filter(filtre -> filtre.player.equals(player))
                .findFirst()
                .ifPresentOrElse(s -> s.changeScore(score), () -> scores.add(sc));

        try (FileWriter out = new FileWriter(scoreFile)) {
            out.write(OptionalAdapter.fromListScoreToJSON(scores));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Tile> getTiles() {
        return tiles;
    }

    @Override
    public Tile getTile(final int x, final int y) {
       return tiles
               .stream()
               .filter(t -> t.getX() == x && t.getY() == y)
               .findAny()
               .orElseThrow();
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    public File getScoreFile() {
        return new File("./scores/scores" + mapName + ".json");
    }


    @Override
    public void setMapName(final String mapName) {
        this.mapName = mapName;
    }

    /*
      public static void main(final String[] args) throws IOException {
        final Map map1 = new MapImpl("map1");
        final Map map2 = new MapImpl("map2");
        final PlayerImpl player1 = new PlayerImpl();
        player1.setName("player1");
        map1.postNewScore(player1.getName(), 14);
        map2.postNewScore(player1.getName(), 20);
    }
     */
}
