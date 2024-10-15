import { Component } from '@angular/core';
import {TextSimilarityService} from "../../services/text-similarity.service";
import {TextSimilarity} from "../../model";


@Component({
  selector: 'app-text-similarity',
  templateUrl: './text-similarity.component.html',
  styleUrls: ['./text-similarity.component.css']
})
export class TextSimilarityComponent {

  text1: string="";
  text2: string="";
  results: TextSimilarity | null = null

  constructor(private textSimilarityService: TextSimilarityService) {}

  sendRequest(): void{
     this.textSimilarityService.compareTexts(this.text1, this.text2)
       .subscribe(
         response=>{
           this.results = response
           console.log(this.results)
         },
         error => {
           console.error('Error fetching similarity data:', error);
         }
       )
  }
}
