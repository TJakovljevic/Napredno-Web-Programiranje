import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {HistoryService} from "./history.service";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class SentimentAnalysisService{

  private readonly  apiUrl = environment.api;
  private readonly  addedUrl = "sent/v1/"
  private token: string = ""

  constructor(private http:HttpClient, private historyService: HistoryService) {
    this.token = <string>localStorage.getItem("authToken");
  }

  sentimentAnalysis(text: string, lang: string): Observable<any>{
    let params = new HttpParams()
      .set("text", text)
      .set("token", this.token)

    if(lang)
      params = params.set("lang", lang);

    const url = this.apiUrl + this.addedUrl;
    const type = "GET"
    const request = this.http.get(url, { params });

    this.historyService.addCall(url + "?" + params, type);

    return request;
  }
}
