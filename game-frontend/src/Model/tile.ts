
export enum Block {
  HOUSE, FONTAIN, CIRCUS, WIND_TURBINE, NOTHING
}

export enum Type {
  TREE, WATER, GRASS
}

export class Tile {

  private occupied: boolean;
  type: Type;
  block: Block ;
  private x: number;
  private y: number;
  constructor(  x: number, y: number) {
    this.x = x;
    this.y = y;
    this.type = this.getRandomType();
    this.occupied = (this.type !== Type.GRASS);
    this.block = Block.NOTHING;
  }
  getRandomType(): Type{
    const random = Math.floor(Math.random() * Math.floor(3));
    switch (random) {
      case 0 :
        return Type.GRASS;
      case 1 :
        return Type.TREE;
      default:
        return Type.WATER;
    }
  }
  equals(obj: Tile): boolean {
    return this.x === obj.x && this.y === obj.y && this.type === obj.type;
  }

  get X(): number {
    return this.x;
  }
  set X(value: number) {
    this.x = value;
  }

  get Y(): number{
    return this.y;
  }
  set Y(value: number) {
    this.y = value;
  }

  get Type(): Type{
    return this.type;
  }
  set Type(value: Type) {
    this.x = value;
  }

  get Occupied(): boolean{
    return this.occupied;
  }
  set Occupied(value: boolean) {
    this.occupied = value;
  }

  get Block(): Block{
    return this.block;
  }
  set Block(value: Block) {
    this.block = value;
  }
  radiusEffect(b: Block): number {
    switch (b){
      case Block.CIRCUS: return 3;
      case Block.NOTHING: return 0;
      default: return 1;
    }
  }
  jsonToBlock(b: any): Block {
    switch (b) {
      case 'HOUSE': return Block.HOUSE;
      case 'WIND_TURBINE': return Block.WIND_TURBINE;
      case 'FONTAIN': return Block.FONTAIN;
      case 'CIRCUS' : return Block.CIRCUS;
      default : return Block.NOTHING;
    }
  }
  jsonToType(t: any): Type {
    switch (t) {
      case 'TREE': return Type.TREE;
      case 'WATER': return Type.WATER;
      default: return Type.GRASS;
    }
  }
  jsonToTile(tile: any): Tile {
    const res = new Tile(tile.x, tile.y);
    res.occupied = tile.occupied;
    res.type = this.jsonToType(tile.type);
    res.block = this.jsonToBlock(tile.block);
    return res;
  }

  jsonToTiles(tiles: any): Array<Tile> {
    const liste = [];
    tiles.forEach( t =>
    liste.push(this.jsonToTile(t)));
    return liste;
  }
}
