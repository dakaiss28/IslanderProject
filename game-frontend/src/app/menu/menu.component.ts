import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {GameService} from '../service/game.service';
import {GameImpl} from '../../Model/game-impl';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit, AfterViewInit {
  mapName: string;
  active: number;

  constructor(public router: Router, public gameService: GameService) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {

    this.gameService.getMaps();
    // console.log(this.gameService.getGame.getMaps());
  }

  onClick(index: number): void {
    this.active = index;
  }

  goToMap($myParam: string = ''): void {
    this.router.navigate(['/map']);
  }
  selectMap(map: string): void {
    this.gameService.getMaps();
    this.gameService.getGame = new GameImpl();
    this.gameService.getGame.startGame(map);
    this.goToMap();
  }

  async newRandomMap($myParam: string = ''): Promise<void> {
    this.gameService.getNewRandomMap();
    await new Promise(r => setTimeout(r, 500));
    this.gameService.getMaps();
    await new Promise(r => setTimeout(r, 50));
    const name = this.gameService.game.getStorage().maps[this.gameService.game.getStorage().maps.length - 1].mapName;
    this.gameService.getGame = new GameImpl();
    this.gameService.getGame.startGame(name);
    this.goToMap();
  }

}
