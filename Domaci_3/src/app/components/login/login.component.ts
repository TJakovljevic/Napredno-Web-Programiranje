import { Component } from '@angular/core';
import {LoginService} from "../../services/login.service";
import {JwtToken} from "../../model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email: string = "";
  password: string = "";
  results: string = "";
  token: string | null = null;


  constructor(private loginService: LoginService) {
    this.token = localStorage.getItem("authToken")
  }

  sendRequest(): void{
    this.loginService.login(this.email, this.password).
    subscribe(
      response=>{
        this.results = response.jwt;
        console.log("Login successful: " + this.results)

      },
      error => {
        console.error('Error fetching detection data:', error);
      });
  }

  logout(): void{
    this.loginService.logout();
    this.results = "";
    console.log(this.results)
  }
}
