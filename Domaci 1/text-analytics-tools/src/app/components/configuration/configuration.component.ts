import {Component, OnInit} from '@angular/core';
import {ConfigService} from "../../services/config.service";

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {

  token: string = '';
  //token: 116b5141ed2c4adfbb8af87923f45d63

  constructor(private configService: ConfigService) {}

  ngOnInit(): void {
  }

  setAuthToken(): void{
    if(this.token){
      this.configService.setToken(this.token);
      alert("Token successfully updated")
    }else{
      alert("The field is empty.")
    }
    this.token = ""
  }

}
