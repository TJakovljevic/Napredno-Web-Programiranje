import {Component, OnInit} from '@angular/core';
import {EntityExtractionService} from "../../services/entity-extraction.service";
import {EntityExtraction} from "../../model";






@Component({
  selector: 'app-entity-extraction',
  templateUrl: './entity-extraction.component.html',
  styleUrls: ['./entity-extraction.component.css']
})
export class EntityExtractionComponent implements OnInit{

  confidence_level: number = 0;
  text: string = "";
  isImageChecked: boolean = false;
  isAbstractChecked: boolean = false;
  isCategoriesChecked: boolean = false;

  results:EntityExtraction[] = [] ;

  ngOnInit() {
  }

  constructor(private extractionService: EntityExtractionService) {
  }
  onSliderInput(event: Event): void {

    const target = event.target as HTMLInputElement;
    const sliderValue = Number(target.value);
    this.confidence_level = sliderValue / 100;
  }

  sendRequest(): void{
    const includeParams = [];
    if (this.isImageChecked) includeParams.push('image');
    if (this.isAbstractChecked) includeParams.push('abstract');
    if (this.isCategoriesChecked) includeParams.push('categories');

    // Call the service with user inputs
    this.extractionService.getEntityData(this.text, this.confidence_level, includeParams.join(','))
      .subscribe(
        response => {
          this.results = response.annotations;  // Adjust based on API response
          console.log(this.results)
        },
        error => {
          console.error('Error fetching entity data:', error);
        }
      );
  }
}
