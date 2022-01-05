import {Tile} from './tile';
import {Block} from './tile';
import {Map} from './map';
import {Player} from './player';
import {Storage} from './storage';

export interface Game {

  updateScore(tile: Tile, block: Block): number;
  updateScoreLimit(): number;
  startGame(mapName: string): void;
  endGame(exit: boolean): boolean;
  computeScore(tile: Tile, block: Block): number;
  increaseScore(block: Block): number;
  getMaps(): Array<Map>;
  postNewMap(map: Map): void;
  getPlayer(): Player;
  setMap(map: string): Map;
  setNewMap(map: string): void;
  getStorage(): Storage;
  getCurrentMap(): Map;
  // playTurn(command: Command): void;
  getScore(): number;
  getScorLimit(): number;
  // UndoRedoCollector getCollector();
  setScore(score: number): void;
  setScoreLimit(limit: number): void;
  decreaseScoreLimit(): void;
  getMapsName(): Array<string>;
}
