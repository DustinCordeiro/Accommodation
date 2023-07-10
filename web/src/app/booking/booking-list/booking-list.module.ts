import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatToolbarModule } from '@angular/material/toolbar'

import { BookingListRoutingModule } from './booking-list-routing.module';
import { BookingListComponent } from './booking-list/booking-list.component';


@NgModule({
  declarations: [
    BookingListComponent
  ],
  imports: [
    CommonModule,
    BookingListRoutingModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatToolbarModule
  ]
})
export class BookingListModule { }
