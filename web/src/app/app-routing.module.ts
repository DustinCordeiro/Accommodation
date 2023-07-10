import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {
    path: "guests",
    loadChildren: () => import('./guest/guest-list/guest-list.module').then(modulo => modulo.GuestListModule)
  },
  {
    path: "bookings",
    loadChildren: () => import('./booking/booking-list/booking-list.module').then(modulo => modulo.BookingListModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
