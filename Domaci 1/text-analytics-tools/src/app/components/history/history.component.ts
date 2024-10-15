import { Component } from '@angular/core';
import {HistoryService} from "../../services/history.service";


@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent {

  apiHistory: string[] = [];

  constructor(private historyService: HistoryService, private apiHistoryService: HistoryService) {
    this.apiHistory = apiHistoryService.getHistory()
    console.log(apiHistoryService)
  }


}
