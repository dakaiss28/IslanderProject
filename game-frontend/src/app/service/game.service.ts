import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Game} from '../../Model/game';
import {Score} from '../../Model/score';
import {Map} from '../../Model/map';
import {GameImpl} from '../../Model/game-impl';
import {MapImpl} from '../../Model/map-impl';

@Injectable({
  providedIn: 'root'
})
export class GameService {
   game: Game;
   mapName: string;

  constructor(private http: HttpClient) {
    this.game = new GameImpl();
    this.mapName = '';
    this.getMaps();
  }

  get httpClient(): HttpClient {
    return this.http;
  }

  get getGame(): Game {
    return this.game;
  }
  set getGame(game) {
    this.game = game;
  }


  getScore(map: string): void {
    this.http
      .get<Array<Score>>('game/score/' + map, {})
      .subscribe(returnedData =>  this.game.getCurrentMap().scores = returnedData );
  }

  postScore(map: string, player: string, score: number): void {
    this.http
      .post('game/score/' + map + '/' + player + '/' + score, JSON.stringify(new Score(player, score)), {})
      .subscribe(returnData => { });
  }

  getMaps(): void {
    this.http
      .get<Array<Map>>('game/map', {})
      .subscribe(returnedData => this.game.getStorage().maps = returnedData.map( m => (new MapImpl()).jsonToMap(m) ));
  }

  postMap(map: string): void {
    this.http
      .post('game/map/' + map, JSON.stringify(map), {})
      .subscribe(returnData => {
      });

  }

  getNewRandomMap(): void {
    this.http
      .get<Map>('game/random', {})
      .subscribe(returnedData => {this.game.getStorage().addMap((new MapImpl()).jsonToMap(returnedData));
      });
  }


  // You should start the development of the front-end without using
  // a back-end: start by displaying the map with fake data:
  // constructor() {
  //   this.game = new GameImpl();
  //   // Set up the game with fake data.
  //   // ...
  // }

  // getCurrentGame(): Game {
  //   return this.game;
  // }
}
