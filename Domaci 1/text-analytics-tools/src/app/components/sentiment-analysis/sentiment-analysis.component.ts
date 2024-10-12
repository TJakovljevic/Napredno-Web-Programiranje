import { Component } from '@angular/core';
import {SentimentAnalysisService} from "../../services/sentiment-analysis.service";

@Component({
  selector: 'app-sentiment-analysis',
  templateUrl: './sentiment-analysis.component.html',
  styleUrls: ['./sentiment-analysis.component.css']
})
export class SentimentAnalysisComponent {

    text: string ="";
    type: string ="" ;
    language: string="";


    constructor(private sentimentAnalysisService: SentimentAnalysisService) {
    }

    sentimentAnalysis(): void{
      this.sentimentAnalysisService.sentimentAnalysis()
    }
}
