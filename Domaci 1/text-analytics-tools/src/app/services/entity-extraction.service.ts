import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class EntityExtractionService{


    private readonly apiUrl = environment.api
  constructor() {
  }

  getEntityData():void{

  }
}
