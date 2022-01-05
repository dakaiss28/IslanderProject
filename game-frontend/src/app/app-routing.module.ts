import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MapComponent} from './map/map.component';
import {MenuComponent} from './menu/menu.component';


const routes: Routes = [
  { path: 'map', component: MapComponent },
  { path: 'menu', component: MenuComponent},
  {
    path: '',
    redirectTo: '/menu', // auto redirect to /c1
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
