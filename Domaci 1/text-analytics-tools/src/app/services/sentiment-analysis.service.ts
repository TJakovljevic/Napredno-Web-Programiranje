import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class SentimentAnalysisService{

  private readonly  apiUrl = environment.api;

  sentimentAnalysis(): void{

  }
}
