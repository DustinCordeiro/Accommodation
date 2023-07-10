import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from './booking.module';
import { URL_BOOKING } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private httpClient: HttpClient) { }

  list():Observable<Booking[]>{
    return this.httpClient.get<Booking[]>(URL_BOOKING);
  }
}
