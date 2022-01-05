import {Player} from './player';
import {Inventaire} from './inventaire';
import {Block} from './tile';

export class PlayerImpl implements Player{
  name: string;
  inventaireStock: Inventaire;

  constructor() {
    this.name = this.randomName();
    this.inventaireStock = new Inventaire();
  }


  get inventaire(): Inventaire {
    return this.inventaireStock;
  }

  set playerName(aux: string) {
    this.name = aux;
  }
  get playerName(): string {
    return this.name;
  }

  getBlockSVG(b: Block): string {
    switch (b){
      case Block.HOUSE: return 'assets/house.svg';
      case Block.FONTAIN: return 'assets/fountain.svg';
      case Block.CIRCUS: return 'assets/circus.svg';
      case Block.WIND_TURBINE: return 'assets/wind-turbine.svg';
    }
  }

  randomString(length, chars: string): string {
    let result = '';
    for (let i = length; i > 0; --i)
    { result += chars[Math.floor(Math.random() * chars.length)];
    }
    return result;
  }

  randomName(): string {
    return this.randomString(8, 'abcdefghijklmnopqrstuvwxyz');
  }
}
