import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class EntityExtractionService{


  private readonly apiUrl = environment.api;
  private readonly addedUrl = "nex/v1"
  private token = "";

  constructor(private http: HttpClient) {
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

    return this.http.get(this.apiUrl + this.addedUrl, { params });
  }


}
