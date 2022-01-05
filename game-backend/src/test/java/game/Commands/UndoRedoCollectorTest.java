package game.Commands;

import game.model.GameImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UndoRedoCollectorTest {
    static UndoRedoCollector collector;

    @BeforeEach
    void init() {
        collector = new UndoRedoCollector();
    }

    @Test
    void addNull() {
        collector.add(null);
        assertTrue(collector.undos.isEmpty());
        assertTrue(collector.redos.isEmpty());
    }

    @Test
    void redo_undo_OnEmpty() {
        assertTrue(collector.undos.isEmpty());
        collector.undo();
        assertTrue(collector.undos.isEmpty());
        collector.add(new RenamePlayer(new GameImpl(), "toto"));
        collector.redo();
        assertTrue(collector.redos.isEmpty());
        assertFalse(collector.undos.isEmpty());
        collector.undo();
        assertTrue(collector.undos.isEmpty());
        assertFalse(collector.redos.isEmpty());
    }

}
