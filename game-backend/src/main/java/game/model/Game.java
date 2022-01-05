package game.model;

import game.Commands.Command;
import game.Commands.UndoRedoCollector;

import java.io.IOException;
import java.util.List;

public interface Game {
    int updateScore(Tile tile, Block block);
    int updateScoreLimit();
    void startGame(String mapName) throws IOException;
    boolean endGame(boolean exit);
    int computeScore(Tile tile, Block block);
    int increaseScore(final Block block);
    List<Map> getMaps() throws IOException;
    void postNewMap(Map map) throws IOException;
    Player getPlayer();
    Map setMap(String map);
    void setNewMap(String map) throws IOException;
    Storage getStorage();
    Map getCurrentMap();
    void playTurn(final Command command);
    int getScore();
    int getScorLimit();
    UndoRedoCollector getCollector();
    void setScore(int score);
    void decreaseScoreLimit();
}
