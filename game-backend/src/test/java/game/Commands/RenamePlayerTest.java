package game.Commands;

import game.model.Game;
import game.model.GameImpl;
import game.model.Player;
import game.model.PlayerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenamePlayerTest {
    static Player player;
    static Game game;
    static RenamePlayer renamer;

    @BeforeEach
    void init(){
        player = new PlayerImpl();
        game = new GameImpl(player);
        renamer = new RenamePlayer(game, "toto");

    }

    @Test
    void RenamePlayer() {
        String oldName = player.getName();
        renamer.execute();
        assertEquals(oldName, renamer.getOldName());
        assertEquals(renamer.getNewName(), player.getName());
        renamer.undo();
        assertEquals(oldName, player.getName());
        renamer.redo();
        assertEquals(renamer.getNewName(), player.getName());
    }
}
