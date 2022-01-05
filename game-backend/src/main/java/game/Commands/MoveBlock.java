package game.Commands;

import game.model.Block;
import game.model.Game;
import game.model.Tile;
import game.model.Type;

public class MoveBlock implements Command, Undoable {
        private final Block element;
        private final Tile oldTile;
        private final Tile newTile;
        private final int oldScore;
        private final int newScore;
        private final Game game;

    /**
     * create a new command to allow Moving a Block
     * @param game the game on which the action is done
     * @param oldTile the tile with the block on it
     * @param newTile the tile on which the block will be moved to
     */
        public MoveBlock(final Game game, final Tile oldTile, final Tile  newTile) {
            this.game = game;
            oldScore = game.getScore();
            element = oldTile.getBlock();
            this.oldTile = oldTile;
            if(newTile.getType() == Type.GRASS && !newTile.getOccupied()) {
                this.newTile = newTile;
            } else {
                throw new IllegalArgumentException();
            }
            newScore = oldScore  - game.updateScore(oldTile, element) + game.updateScore(newTile, element);


        }

    public int getOldScore() {
        return oldScore;
    }

    public int getNewScore() {
        return newScore;
    }

    public Block getElement() {
        return element;
    }

    public Tile getOldTile() {
        return oldTile;
    }

    public Tile getNewTile() {
        return newTile;
    }

    /**
     * move the block from oldTile to NewTile and update the tiles' attributes and the score
     */
    @Override
        public void execute() {
            oldTile.setOccupied(false);
            oldTile.setBlock(Block.NOTHING);
            newTile.setOccupied(true);
            newTile.setBlock(element);
            game.setScore(newScore);
            redo();

        }

        @Override
        public void undo() {
            oldTile.setBlock(element);
            oldTile.setOccupied(true);
            newTile.setOccupied(false);
            newTile.setBlock(Block.NOTHING);
            game.setScore(oldScore);

        }

        @Override
        public void redo() {
            oldTile.setOccupied(false);
            oldTile.setBlock(Block.NOTHING);
            newTile.setOccupied(true);
            newTile.setBlock(element);
            game.setScore(newScore);
        }


}
