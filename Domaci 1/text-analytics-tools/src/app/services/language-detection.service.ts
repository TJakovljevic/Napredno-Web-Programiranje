import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {HistoryService} from "./history.service";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root',
})
export class LanguageDetectionService{

  private readonly apiUrl = environment.api;
  private readonly addedUrl = "li/v1/"
  private token = "";


  constructor(private http: HttpClient,private historyService: HistoryService) {
    this.token = <string>localStorage.getItem("authToken");
  }

  detectLanguage(text: string, clean: boolean): Observable<any>{
    let params = new HttpParams()
      .set("text", text)
      .set("token", this.token);

    if(clean)
      params = params.set("clean", clean)


    const url = this.apiUrl + this.addedUrl;
    const type = "GET"
    const request = this.http.get(url, { params });
    console.log(params)
    this.historyService.addCall(url + '?' + params, type)

    return request;
  }


}
