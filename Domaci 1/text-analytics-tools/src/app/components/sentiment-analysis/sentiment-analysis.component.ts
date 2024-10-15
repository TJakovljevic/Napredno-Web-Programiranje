import { Component } from '@angular/core';
import {SentimentAnalysisService} from "../../services/sentiment-analysis.service";
import {SentimentAnalysis} from "../../model";

@Component({
  selector: 'app-sentiment-analysis',
  templateUrl: './sentiment-analysis.component.html',
  styleUrls: ['./sentiment-analysis.component.css']
})
export class SentimentAnalysisComponent {

    text: string ="";
    language: string="";

    results: SentimentAnalysis | null = null;


    constructor(private sentimentAnalysisService: SentimentAnalysisService) {}

    sendRequest(): void{
      this.sentimentAnalysisService.sentimentAnalysis(this.text, this.language)
        .subscribe(
          response=>{
              this.results = response.sentiment;
              console.log(this.results)
          },error => {
            console.error('Error fetching analysis data:', error);
          }
        )
    }

  getColor(score: number): string {
    score = Math.max(-1, Math.min(1, score));

    const redColor = {r: 255, g: 0, b: 0};
    const brownColor = {r: 101, g: 67, b: 33};
    const greenColor = {r: 0, g: 255, b: 0};

    let color;
    if (score < 0) {
      const t = (score + 1);
      color = this.interpolateColors(redColor, brownColor, t);
    } else {
      const t = score;
      color = this.interpolateColors(brownColor, greenColor, t);
    }

    return `rgb(${color.r}, ${color.g}, ${color.b})`;
  }
  interpolateColors(color1: { r: number, g: number, b: number }, color2: { r: number, g: number, b: number }, t: number) {
    const r = Math.round(color1.r + (color2.r - color1.r) * t);
    const g = Math.round(color1.g + (color2.g - color1.g) * t);
    const b = Math.round(color1.b + (color2.b - color1.b) * t);

    return { r, g, b };
  }
}
