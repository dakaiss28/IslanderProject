
import {CommandBase, Undoable} from 'interacto';
import {Tile, Block, Type} from '../Model/tile';
import {Game} from '../Model/game';

export class PutBlock extends CommandBase implements Undoable  {
  private block: Block;
  private  tile: Tile;
  private oldScore: number;
  private newScore: number;
  private game: Game;
  private nbUpdates: number;



  constructor(game: Game, block: Block, tile: Tile) {
    super();
    this.game = game;
    this.block = block;
    this.oldScore = this.game.getScore();
    this.newScore = this.oldScore + this.game.updateScore(tile, block);
    this.nbUpdates = 0;
    if (!tile.Occupied && tile.Type === Type.GRASS) {
      this.tile = tile;
    } else {
      throw new Error('erreur');
    }
  }


  execution(): void {
      this.tile.block = this.block;
      this.tile.Occupied = true;
      this.game.setScore(this.newScore);
      this.game.getPlayer().inventaire.removeBlock(this.block);
      while (this.game.getScore() > this.game.getScorLimit()) {
        this.game.updateScoreLimit();
        this.nbUpdates++;
      }
}

get  chosenBlock(): Block {
  return this.block;
}

get chosenTile(): Tile {
  return this.tile;
}


 undo(): void{
  this.tile.block = (Block.NOTHING);
  this.tile.Occupied = (false);
  this.game.setScore(this.oldScore);
  this.game.getPlayer().inventaire.addBlock(this.block);
  for (let i = 0; i < this.nbUpdates; i++) {
    this.game.decreaseScoreLimit();
  }
  this.nbUpdates = 0;
}

  get OldScore(): number{
    return this.oldScore;
}

  get NewScore(): number {
    return this.newScore;
}

  redo(): void {
    this.tile.block = this.block;
    this.tile.Occupied = true;
    this.game.setScore(this.newScore);
    this.game.getPlayer().inventaire.removeBlock(this.block);
    while ( this.game.getScore() > this.game.getScorLimit()) {
      this.game.updateScoreLimit();
      this.nbUpdates++;
    }
  }

  getUndoName(): string {
    return 'put block';
  }
}
