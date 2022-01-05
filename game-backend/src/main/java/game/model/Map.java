package game.model;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.List;

@XmlRootElement
@XmlSeeAlso(MapImpl.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
public interface Map {
    void initializeMap();
    List<Tile> getTiles();
    List<Tile> getNeighbours(Tile tile, int radius);
    Score getScore(String player);
    void postNewScore(String player, int score) throws IOException;
    Tile getTile(int x, int y);
    String getMapName();
    List<Score> getScores() throws IOException;
    void setMapName(String mapName);
}
