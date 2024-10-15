import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private tokenKey = "authToken";


  constructor() {
  }
  setToken(token: string): void{
    localStorage.setItem(this.tokenKey, token);
  }


}
