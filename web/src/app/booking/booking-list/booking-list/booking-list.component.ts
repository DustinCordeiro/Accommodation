import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from '../../booking.module';
import { BookingService } from '../../booking.service';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.scss']
})
export class BookingListComponent {

  bookings$!: Observable<Booking[]>;

  tableColumns = ['id', 'guestId', 'checkInDate', 'checkOutDate', 'hasCheckIn', 'hasCheckOut', 'hasVehicle', 'initialValue', 'finalValue'];

  constructor(private bookingService: BookingService) { }

  ngOnInit() {
    this.listBookings();
  }

  listBookings() {
    this.bookings$ = this.bookingService.list();
  }
}
