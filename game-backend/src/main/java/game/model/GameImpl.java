package game.model;

import game.Commands.Command;
import game.Commands.PutBlock;
import game.Commands.UndoRedoCollector;
import game.Commands.Undoable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GameImpl implements Game {

    private  int score;
    private  int scorLimit;
    private final Player player;
    private final Storage storage;
    private Map currentMap;
    //private RenamePlayer renamer;
    private UndoRedoCollector collector;

    public GameImpl() {
        score = 0;
        scorLimit = 10;
        player = new PlayerImpl();
        storage = new Storage();
        collector = new UndoRedoCollector();

    }

    public GameImpl(final Storage storage) {
        score = 0;
        scorLimit = 10;
        player = new PlayerImpl();
        this.storage = storage;

    }

    public GameImpl(final Player player) {
        score = 0;
        scorLimit = 10;
        this.player = player;
        storage = new Storage();

    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getScorLimit() {
        return scorLimit;
    }

    @Override
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     *
     * @param command the command the player is making at each turn
     */
    @Override
    public void playTurn(final Command command) {
        command.execute();
        if(command instanceof Undoable) {
            collector.add((Undoable) command);
        }
        if(command instanceof PutBlock) {
            updateScoreLimit();
        }
    }

    /**
     *
     * @param tile the given tile
     * @param block the given block
     * @return the score calculated when puting block on th tile regarding the neighbours of the tile
     */
    @Override
    public int updateScore(final Tile tile, final Block block) {
        int scorePlus = increaseScore(block);
        scorePlus += currentMap.getNeighbours(tile, block.radiusEffect())
                .stream()
                .mapToInt(neighbour -> computeScore(neighbour, block))
                .sum();
        return scorePlus;

    }

    @Override
    public int updateScoreLimit() {
        scorLimit += 10;
        player.getInventaire().updateStock();
        return scorLimit;
    }

    /**
     * set up a map if it doesn't exist
     * @param mapName the name of the map the player want to get
     * @throws IOException if problem with map File
     */
    @Override
    public void startGame(final String mapName) throws IOException {
        final Optional<Map> map = storage.getMaps()
                .stream()
                .filter(m -> m.getMapName().equals(mapName))
                .findFirst();
        if(map.isEmpty()) {
            setNewMap(mapName);
        } else {
            setMap(mapName);
        }
    }

    /**
     *
     * @param exit true if the player pressed on the exit button
     * @return boolean to check if it's time to end the game
     */
    @Override
    public boolean endGame(final boolean exit) {
        final boolean fullMap = currentMap
                .getTiles()
                .stream()
                .allMatch(Tile::getOccupied);
        final boolean stockVide = player.getInventaire().stock.values()
                .stream()
                .allMatch(i -> i == 0);

        return fullMap || stockVide || exit;
    }


    @Override
    public UndoRedoCollector getCollector() {
        return collector;
    }

    /**
     *
     * @param block the block that will be put on the map
     * @return the bonus point gotten by putting the block on the map
     */
    @Override
    public int increaseScore(final Block block) {
        switch (block) {
            case CIRCUS: return 10;
            case HOUSE: return 5;
            case FONTAIN: return 4;
            default: return 8;
        }
    }

    /**
     *
     * @param tile the neighbour tile of the tile the player will put a block on
     * @param block the block put
     * @return the points got by puttiing a block regarding the neigbour Tile
     */
    @Override
    public int computeScore(final Tile tile, final Block block) {
       if(tile.getType() == Type.TREE) {
           switch (block) {
               case HOUSE:
               case FONTAIN:
                   return 4;
               case WIND_TURBINE: return -5;
               default:
           }
       }

       switch (block) {
           case FONTAIN:
               switch (tile.getBlock()) {
                   case HOUSE: return 5;
                   case CIRCUS: return +6;
                   default:
                       return 0;
               }
           case HOUSE:
               switch (tile.getBlock()) {
                   case FONTAIN: return 10;
                   case HOUSE: return 5;
                   case WIND_TURBINE: return -10;
                   case CIRCUS: return 8;
               }
           case WIND_TURBINE:
               if(tile.getType() == Type.WATER) {
                   return 8;
               }
               if (tile.getBlock() == Block.HOUSE) {
                   return -7;
               }
           case CIRCUS:
               switch (tile.getBlock()) {
                   case FONTAIN:
                   case HOUSE: return 10;
                   case WIND_TURBINE:
                   case CIRCUS: return -20;
                   default:
                       return 0;
               }

       }
        return 0;
    }

    @Override
    public void decreaseScoreLimit() {
        scorLimit -= 10;
        player.getInventaire().removeBlocks();
    }

    @Override
    public List<Map> getMaps() throws IOException {
        return storage.getMaps();
    }

    @Override
    public Map getCurrentMap() {
        return currentMap;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Map setMap(final String map) {

        if(storage.getMap(map) != null) {
            currentMap = storage.getMap(map);
        }
        return currentMap;
    }

    /**
     * creates a new map
     * @param map the name of the map to be created
     * @throws IOException
     */
    @Override
    public void setNewMap(final String map) throws IOException {
        currentMap = new MapImpl(map);
        storage.addMap(currentMap);
    }

    @Override
    public Storage getStorage() {
        return storage;
    }


    /**
     * put a new map on the storage
     * @param map the map to put on
     * @throws IOException if problems with the map File
     */
    @Override
    public void postNewMap(final Map map) throws IOException {
        storage.addMap(map);
    }
}
