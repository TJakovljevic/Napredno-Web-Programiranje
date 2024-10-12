import { Component } from '@angular/core';
import {LanguageDetectionService} from "../../services/language-detection.service";

@Component({
  selector: 'app-language-detection',
  templateUrl: './language-detection.component.html',
  styleUrls: ['./language-detection.component.css']
})
export class LanguageDetectionComponent {

    text: string = "";
    clean: boolean = false;
    // language: Language;

  constructor(private languageDetectionService: LanguageDetectionService) {
  }

  detectLanguage(): void{
    this.languageDetectionService.detectLanguage()
  }
}
