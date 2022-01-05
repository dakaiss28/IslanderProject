import {Score} from './score';
import {Tile} from './tile';
import {Type} from './tile';
import {Block} from './tile';
import {Map} from './map';

export class MapImpl implements Map{

  tiles: Tile[];
  scores: Score[];
  mapName: string;

  constructor() {
    this.tiles = [];
    this.scores = [];
    this.mapName = '';
    this.initializeList();
  }

  jsonToMap(map: any): Map {
        const res = new MapImpl();
        res.tiles.length = 0;
        const aux = new Tile(0, 0);
        res.tiles = aux.jsonToTiles(map.tiles);
        res.mapName = map.mapName;
        res.scores = map.scores;
        return res;
    }

  initializeList(): void {
    for (let x = 0; x < 10; x++) {
      for (let y = 0; y < 10; y++) {
        this.tiles.push(new Tile(x, y));
      }
    }
  }

  getTile(x1: number, y1: number): Tile {
    const aux = this.tiles.find(t => (t.X === x1 && t.Y === y1));
    return aux;
  }

  getNeighbours(tile: Tile, radius: number): Array<Tile> {
    const x = tile.X;
    const y = tile.Y;
    const res = [];

    console.log('(x,y):' + x + ' ' + y);
    let minX = x - radius;
    if (minX < 0) {
      minX = 0;
    }
    let maxX = x + radius;
    if (maxX > 9) {
      maxX = 9;
    }

    let minY = y - radius;
    if (minY < 0) {
      minY = 0;
    }

    let maxY = y + radius;
    if (maxY > 9) {
      maxY = 9;
    }

    for (let i = minX; i <= maxX; i++) {
      for (let j = minY; j <= maxY; j++) {
        if (i !== x || j !== y) {
          res.push(this.getTile(i, j));
        }
      }
    }

    console.log(res);

    return res;
  }

  getTileSVG(tile: Tile): string {
    switch (tile.Type) {
      case Type.TREE:
        return 'assets/tree.svg';
      case Type.WATER:
        return 'assets/water.svg';
      default:
        switch (tile.Block) {
          case Block.NOTHING:
            return 'assets/empty.svg';
          case Block.CIRCUS:
            return 'assets/circus.svg';
          case Block.FONTAIN:
            return 'assets/fountain.svg';
          case Block.HOUSE:
            return 'assets/house.svg';
          default:
            return 'assets/wind-turbine.svg';
        }
    }
  }

  postNewScore(player: string, newScore: number): void {
    const aux = new Score(player, newScore);
    const oldScore = this.scores.find(s => s.player === player).score;
    if (oldScore !== null) {
      this.scores = this.scores.filter(s => s.player !== player);
      this.scores.push(aux.changeScore(oldScore));
    } else {
      this.scores.push(aux);
    }
  }

  get name(): string {
    return this.mapName;
  }

  set name(name) {
    this.mapName = name;
  }

  get tileList(): Array<Tile> {
    return this.tiles;
  }

  set tileList(liste) {
    this.tiles = liste;
  }

  get scoresList(): Array<Score> {
    return this.scores;
  }

  getScore(player: string): Score {
    return this.scores.find(s => s.player === player);
  }
}
