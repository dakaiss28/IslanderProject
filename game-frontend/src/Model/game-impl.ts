import {Game} from './game';
import {Block, Tile, Type} from './tile';
import {Player} from './player';
import {Map} from './map';
import {PlayerImpl} from './player-impl';
import {Storage} from './storage';
import {MapImpl} from './map-impl';

export class GameImpl implements Game{

  constructor(){
    this.score = 0;
    this.scorLimit = 10;
    this.player = new PlayerImpl();
  }
  static storage: Storage = new Storage();

  score: number;
  scorLimit: number;
  player: Player;
  currentMap: Map;

  getCurrentMap(): Map {
    return this.currentMap;
  }

  getMaps(): Array<Map> {
    return GameImpl.storage.mapList;
  }

  getPlayer(): Player {
    return this.player;
  }

  getScorLimit(): number {
    return this.scorLimit;
  }

  getScore(): number {
    return this.score;
  }

  getStorage(): Storage {
    return GameImpl.storage;
  }

  postNewMap(map: Map): void {
    GameImpl.storage.addMap(map);
  }

  setMap(map: string): Map {
    // console.log(this.storage);
    console.log(GameImpl.storage.getMap(map));
    if (GameImpl.storage.getMap(map) != null) {
      this.currentMap = GameImpl.storage.getMap(map);
    }
    return this.currentMap;
  }

  setNewMap(map: string): void {
    const aux = new MapImpl();
    aux.mapName = map;
    this.currentMap = aux;
    GameImpl.storage.addMap(this.currentMap);
  }

  setScore(score: number): void {
    this.score = score;
  }

  setScoreLimit(limit: number): void {
    this.scorLimit = limit;
  }

  endGame(exit: boolean): boolean {
    let fullMap = true;
    let stockVide = true;

    this.currentMap.tiles.forEach(t => {
      if (t.Occupied === false) {
        fullMap = false;
      }
    });

    this.player.inventaire.stock.forEach(b => {
      if (this.player.inventaire.stock.get(b) !== 0) {
        stockVide = false;
      }
    });

    return fullMap || stockVide || exit;
  }

  startGame(mapName: string): void {
    if (GameImpl.storage.getMap(mapName) != null) {
      this.setMap(mapName);
    } else {
      this.setNewMap(mapName);
    }
  }

  increaseScore(block: Block): number {
    switch (block) {
      case Block.CIRCUS: return 10;
      case Block.HOUSE: return 5;
      case Block.FONTAIN: return 4;
      default: return 8;
    }
  }


  computeScore(tile: Tile, block: Block): number {
    if (tile.Type === Type.TREE) {
      switch (block) {
        case Block.HOUSE:
        case Block.FONTAIN:
          return 4;
        case Block.WIND_TURBINE: return -5;
        default:
      }
    }

    switch (block) {
      case Block.FONTAIN:
        switch (tile.Block) {
          case Block.HOUSE: return 5;
          case Block.CIRCUS: return +6;
          default:
            return 0;
        }

      // tslint:disable-next-line:no-switch-case-fall-through
      case Block.HOUSE:
        switch (tile.Block) {
          case Block.FONTAIN: return 10;
          case Block.HOUSE: return 5;
          case Block.WIND_TURBINE: return -10;
          case Block.CIRCUS: return 8;
          default:
            return 0;
        }

      // tslint:disable-next-line:no-switch-case-fall-through
      case Block.WIND_TURBINE:
        if (tile.Type === Type.WATER) {
          return 8;
        }
        if (tile.Block === Block.HOUSE) {
          return -7;
        }

      // tslint:disable-next-line:no-switch-case-fall-through
      case Block.CIRCUS:
        switch (tile.Block) {
          case Block.FONTAIN:
          case Block.HOUSE: return 10;
          case Block.WIND_TURBINE:
          case Block.CIRCUS: return -20;
          default:
            return 0;
        }

        return 0;

    }
    return 0;
  }

  updateScore(tile: Tile, block: Block): number {
    if (block === Block.NOTHING) {
      return 0;
    }
    let scorePlus = this.increaseScore(block);
    this.currentMap.getNeighbours(tile, tile.radiusEffect(block)).forEach(neighbour => {
      scorePlus += this.computeScore(neighbour, block);
    });
    this.score += scorePlus;
    return scorePlus;
  }

  updateScoreLimit(): number {
    this.scorLimit += 10;
    this.player.inventaire.updateStock();
    return this.scorLimit;
  }

  decreaseScoreLimit(): void {
    this.scorLimit -= 10;
    this.player.inventaire.removeBlocks();
  }

  getMapsName(): Array<string> {
    return this.getMaps().map(m => m.mapName);
  }


}
