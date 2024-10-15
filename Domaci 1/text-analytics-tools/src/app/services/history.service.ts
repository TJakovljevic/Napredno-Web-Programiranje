import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private apiCallHistory: string[] = []

  addCall(url: string, type: string): void {
    let date = this.getCurrentDateTime()
    let call = "[" + date  +  "]" + " " + type + " " + url
    this.apiCallHistory.push(call);
  }

  getHistory(): string[] {
    return this.apiCallHistory;
  }

  private getCurrentDateTime(): string {
    const now = new Date();
    const day = this.padZero(now.getDate());
    const month = this.padZero(now.getMonth() + 1);
    const year = now.getFullYear();
    const hours = this.padZero(now.getHours());
    const minutes = this.padZero(now.getMinutes());
    const seconds = this.padZero(now.getSeconds());

    return `${day}.${month}.${year}. ${hours}:${minutes}:${seconds}`;
  }

  private padZero(value: number): string {
    return value < 10 ? '0' + value : value.toString();
  }

}
