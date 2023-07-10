import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Guest } from './guest.model';
import { URL_GUEST } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GuestService {

  constructor(private httpClient: HttpClient) { }

  list():Observable<Guest[]>{
    return this.httpClient.get<Guest[]>(URL_GUEST);
  }
}
