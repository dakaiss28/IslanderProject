import {Inventaire} from './inventaire';
import {Block} from './tile';

export interface Player {

  name: string;
  inventaire: Inventaire;
  randomName(): string;
  getBlockSVG(b: Block): string;
}
