import {CommandBase, Undoable} from 'interacto';
import {Game} from '../Model/game';

export class RenamePlayer extends CommandBase implements Undoable {
  public oldName: string;
  public newName: string;
  public game: Game;
  constructor(game: Game, name: string) {
    super();
    this.game = game;
    this.oldName = game.getPlayer().name;
    this.newName = name;

  }
  protected execution(): void {
    this.game.getPlayer().name = this.newName;
    this.redo();
  }

  getUndoName(): string {
    return 'rename a player';
  }

  redo(): void {
    this.game.getPlayer().name = this.newName;
  }

  undo(): void {
    this.game.getPlayer().name = this.oldName;
  }

}
