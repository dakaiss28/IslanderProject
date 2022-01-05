import {Block, Tile, Type} from './tile';
import {Score} from './score';

export interface Map {
  tiles: Tile[];
  scores: Score[];
  mapName: string;


  initializeList(): void;

  getTile(x1: number, y1: number): Tile;

  getNeighbours(tile: Tile, radius: number): Array<Tile>;

  getTileSVG(tile: Tile): string;

  postNewScore(player: string, newScore: number): void;

  getScore(player: string): Score;

  jsonToMap(map: any): Map;
}
