
export class Score {
  constructor(private playerName: string, private scoreValue: number) {
  }


  changeScore(newScore: number): Score{
    this.scoreValue = Math.max(this.scoreValue, newScore);
    return this;
  }
  equals(obj: Score): boolean {
    return this.score === obj.score && this.player === obj.player;
  }
  get player(): string {
    return this.playerName;
  }

  set player(value: string) {
    this.playerName = value;
  }

  get score(): number {
    return this.scoreValue;
  }

  set score(value: number) {
    this.scoreValue = value;
  }
}
