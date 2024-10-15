import { Component } from '@angular/core';
import {LanguageDetectionService} from "../../services/language-detection.service";
import {LanguageDetection} from "../../model";

@Component({
  selector: 'app-language-detection',
  templateUrl: './language-detection.component.html',
  styleUrls: ['./language-detection.component.css']
})
export class LanguageDetectionComponent {

    text: string = "";
    clean: boolean = false;
    results: LanguageDetection[] = []

  constructor(private languageDetectionService: LanguageDetectionService) {}

  sendRequest(): void{
    this.languageDetectionService.detectLanguage(this.text, this.clean).
    subscribe(
      response=>{
        this.results = response.detectedLangs;
      },
      error => {
        console.error('Error fetching detection data:', error);
      }
    )
  }
}
