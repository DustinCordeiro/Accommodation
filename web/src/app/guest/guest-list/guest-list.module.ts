import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatToolbarModule } from '@angular/material/toolbar'

import { GuestListRoutingModule } from './guest-list-routing.module';
import { GuestListComponent } from './guest-list/guest-list.component';


@NgModule({
  declarations: [
    GuestListComponent
  ],
  imports: [
    CommonModule,
    GuestListRoutingModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatToolbarModule
  ]
})
export class GuestListModule { }
