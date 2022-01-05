import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {GameService} from '../service/game.service';
import {Map} from '../../Model/map';
import {Game} from '../../Model/game';
import {Block} from '../../Model/tile';
import {UndoCollector} from 'interacto';
import {PutBlock} from '../../Command/putBlock';
import {RenamePlayer} from '../../Command/renamePlayer';
import {Router} from '@angular/router';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'] ,
  styles: [
    `.modal{background: rgba(0,0,0, .5)}`
  ]
})
export class MapComponent implements AfterViewInit {
  gameTest: Game;
  temporaireBlock: Block;
  collector: UndoCollector;
  @ViewChild('newName')
  newName: ElementRef<HTMLTextAreaElement>;

  displayReview = 'none';
  displayExit = 'none';
  displayError = 'none';

    constructor(public gameService: GameService, public router: Router) {
      this.gameTest = gameService.getGame;
      this.collector = new UndoCollector();
  }

  get map(): Map {
    return this.gameTest.getCurrentMap();
  }


  set_new_player_name(): void {
      console.log(this.newName.nativeElement.value);
      const cmd = new RenamePlayer(this.gameTest, this.newName.nativeElement.value);
      this.collector.add(cmd);
      cmd.execute();
  }
  chooseBlock(b: Block): void {
      this.temporaireBlock = b;
  }
  put_a_block(x: number, y: number): void {
      if (this.temporaireBlock === Block.NOTHING) {
      return;
    }
      if (this.gameTest.getCurrentMap().getTile(x, y).Occupied === true) {
        this.openModalError();
        return;
      }
      const cmd = new PutBlock(this.gameTest, this.temporaireBlock, this.gameTest.getCurrentMap().getTile(x, y));
      this.collector.add(cmd);
      cmd.execute();
      this.temporaireBlock = Block.NOTHING;

      if (this.gameTest.endGame(false)) {
        this.openModalExit();
      }
  }

  onUndo(): void {
      this.collector.undo();
  }
  onRedo(): void {
      this.collector.redo();
  }
  ngAfterViewInit(): void {
  }

  goToMenu(): void {
    this.router.navigate(['/menu']);
  }

  // Handle Modals for review
  openModalReview(): void {
    this.displayReview = 'block';
    this.gameService.getScore(this.gameTest.getCurrentMap().mapName);
  }
  onCloseHandledReview(): void {
    this.displayReview = 'none';
  }

  // Handle Modal to exit a game
  openModalExit(): void {
    this.displayExit = 'block';
  }

  saveScore(): void {
    this.gameService.postScore(this.gameTest.getCurrentMap().mapName, this.gameTest.getPlayer().name, this.gameTest.getScore());
  }
  onCloseHandledExit(): void {
    this.displayExit = 'none';
    this.goToMenu();
  }
  // Handle Modal for error
  openModalError(): void {
    this.displayError = 'block';
  }

  onCloseHandledError(): void {
    this.displayError = 'none';
  }

}


