import { Component } from '@angular/core';
import {TextSimilarityService} from "../../services/text-similarity.service";

@Component({
  selector: 'app-text-similarity',
  templateUrl: './text-similarity.component.html',
  styleUrls: ['./text-similarity.component.css']
})
export class TextSimilarityComponent {

  text1: string="";
  text2: string="";
  similarity: number=0

  constructor(private textSimilarityService: TextSimilarityService) {}

  compare(): void{
    // this.textSimilarityService.compareTexts(this.text1, this.text2)
  }
}
