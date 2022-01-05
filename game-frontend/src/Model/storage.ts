import {Map} from './map';

export class Storage {
  maps: Array<Map>;
  constructor() {
    this.maps = [];
  }
  get mapList(): Array<Map> {
    return this.maps;
  }
  addMap(map: Map): void{
    this.maps.push(map);
  }
  getMap(name: string): Map {
    return this.maps.find(m => m.mapName === name);
  }
}
