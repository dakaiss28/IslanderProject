package game.Commands;

import java.util.ArrayDeque;
import java.util.Deque;

public class UndoRedoCollector {


    Deque<Undoable> undos;
    Deque<Undoable> redos;

    /**
     * collects the commands to undo or redo, for replay later
     */
    public UndoRedoCollector() {
        undos = new ArrayDeque<>();
        redos = new ArrayDeque<>();
    }
    public void add(final Undoable undoable) {
        if(undoable != null) {
            undos.push(undoable);
            redos.clear();
        }
    }

    public void undo() {
        if(!undos.isEmpty()) {
            final Undoable un = undos.pop();
            un.undo();
            redos.push(un);
        }
    }

    public void redo() {
        if(!redos.isEmpty()) {
            final Undoable re = redos.pop();

            re.redo();
            undos.push(re);
        }
    }

    public int getNbUndoables() {
        return undos.size();
    }
    public int getNbRedoables() {
        return redos.size();
    }


}
