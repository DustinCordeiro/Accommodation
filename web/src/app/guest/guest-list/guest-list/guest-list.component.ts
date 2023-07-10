import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Guest } from '../../guest.model';
import { GuestService } from '../../guest.service';

@Component({
  selector: 'app-guest-list',
  templateUrl: './guest-list.component.html',
  styleUrls: ['./guest-list.component.scss']
})
export class GuestListComponent implements OnInit{

  guests$!: Observable<Guest[]>;

  tableColumns = ['id', 'name', 'document', 'phone'];

  constructor(private guestService: GuestService) { }

  ngOnInit() {
    this.listGuests();
  }

  listGuests() {
    this.guests$ = this.guestService.list();
  }
}
