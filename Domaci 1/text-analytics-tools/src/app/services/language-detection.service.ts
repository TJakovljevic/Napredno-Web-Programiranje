import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root',
})
export class LanguageDetectionService{

  private readonly apiUrl = environment.api;
  detectLanguage(): void{

  }

}
