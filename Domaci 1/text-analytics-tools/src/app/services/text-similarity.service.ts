import {Injectable } from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {HistoryService} from "./history.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TextSimilarityService {

  private readonly apiUrl = environment.api
  private readonly addedUrl = "sim/v1/"
  private token = "";

  constructor(private http: HttpClient, private historyService: HistoryService) {
    this.token = <string>localStorage.getItem("authToken")
  }

  compareTexts(text1: string, text2: string): Observable<any>{
      let params = new HttpParams()
        .set("text1", text1)
        .set("text2", text2)
        .set("token", this.token)

      const url = this.apiUrl + this.addedUrl;
      const type = "GET";
      const request = this.http.get(url, { params })

    this.historyService.addCall(url + '?' + params, type);

    return request;

  }



}
