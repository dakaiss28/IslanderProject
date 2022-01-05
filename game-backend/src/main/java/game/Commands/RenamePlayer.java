package game.Commands;

import game.model.Game;


public class RenamePlayer implements Command, Undoable {

     String oldName;
     String newName;
     Game game;


    /**
     * Change the name of a player during a game
     * @param game the game that the player is playing on
     * @param newName the new name of the player
     */
    public RenamePlayer(final Game game, final String newName) {
        this.game = game;
        this.oldName = game.getPlayer().getName();
        this.newName = newName;
    }

    @Override
    public void execute() {
        game.getPlayer().setName(newName);
        redo();
    }

    @Override
    public void undo() {
        game.getPlayer().setName(oldName);
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }



    @Override
    public void redo() {
        game.getPlayer().setName(newName);
    }
}

