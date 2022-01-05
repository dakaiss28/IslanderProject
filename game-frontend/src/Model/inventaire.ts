import {Block} from './tile';

export class Inventaire {
  stock: Map<Block, number>;
  constructor() {
    this.stock = new Map<Block, number>();
    this.stock.set(Block.HOUSE, 1);
    this.stock.set(Block.CIRCUS, 0);
    this.stock.set(Block.FONTAIN, 0);
    this.stock.set(Block.WIND_TURBINE, 0);
  }

  updateStock(): void {
    this.stock.forEach((v, b) => this.stock.set(b, v + 1));
  }
  removeBlocks(): void {
    this.stock.forEach( (v, b) => {
      if (v > 0) { this.stock.set(b, v - 1); }
    });
  }

  removeBlock(b: Block): void {
    const nbBlocks = this.stock.get(b);
    if (nbBlocks > 0) {
      this.stock.set(b, nbBlocks - 1);
    }
  }
  addBlock(b: Block): void {
    const nbBlocks = this.stock.get(b);
    this.stock.set(b, nbBlocks + 1);
  }

  nbBlock(b: Block): number {
    return this.stock.get(b);
  }
}
