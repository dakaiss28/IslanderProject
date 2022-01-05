package game.Commands;

import game.model.Block;
import game.model.Game;
import game.model.Tile;
import game.model.Type;

public class PutBlock implements Command, Undoable {
    private final Block block;
    private final Tile tile;
    private final int oldScore;
    private final int newScore;
    private final Game game;


    /**
     * create a new command to place a Block on a given Tile of the Map
     * @param game the game the player is on
     * @param block the block to place
     * @param tile the tile on which the block will be put on
     */
    public PutBlock(final Game game, final Block block, final Tile tile) {
        this.game = game;
        this.block = block;
        this.oldScore = game.getScore();
        this.newScore = oldScore + game.updateScore(tile, block);
        if(!tile.getOccupied() && tile.getType() == Type.GRASS) {
            this.tile = tile;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * set the block on the given tile and update the score
     */
    @Override
    public void execute() {
        tile.setBlock(block);
        tile.setOccupied(true);
        game.setScore(newScore);
        game.getPlayer().getInventaire().removeBlock(block);
        game.updateScoreLimit();
        redo();
    }

    public Block getBlock() {
        return block;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public void undo() {
        tile.setBlock(Block.NOTHING);
        tile.setOccupied(false);
        game.setScore(oldScore);
        game.getPlayer().getInventaire().addBlock(block);
        game.decreaseScoreLimit();
    }

    public int getOldScore() {
        return oldScore;
    }

    public int getNewScore() {
        return newScore;
    }

    @Override
    public void redo() {
        tile.setBlock(block);
        tile.setOccupied(true);
        game.setScore(newScore);
        game.getPlayer().getInventaire().removeBlock(block);
        game.updateScoreLimit();
    }
}
