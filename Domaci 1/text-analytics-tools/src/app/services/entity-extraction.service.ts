import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {HistoryService} from "./history.service";


@Injectable({
  providedIn: 'root'
})
export class EntityExtractionService{


  private readonly apiUrl = environment.api;
  private readonly addedUrl = "nex/v1/"
  private token = "";

  constructor(private http: HttpClient, private historyService: HistoryService) {
    this.token = <string>localStorage.getItem("authToken");
  }

  getEntityData(text: string, minConfidence: number, include: string): Observable<any> {
    let params = new HttpParams()
      .set('text', text)
      .set('min_confidence', minConfidence.toString())
      .set('token', this.token);

    if (include) {
      params = params.set('include', include);
    }

    const url = this.apiUrl + this.addedUrl;
    const type = "GET";
    const request = this.http.get(url, { params });

    this.historyService.addCall(url + '?' + params, type)
    return request;
  }


}
