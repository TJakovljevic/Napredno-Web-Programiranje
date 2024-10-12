import {Component, OnInit} from '@angular/core';
import {EntityExtractionService} from "../../services/entity-extraction.service";




@Component({
  selector: 'app-entity-extraction',
  templateUrl: './entity-extraction.component.html',
  styleUrls: ['./entity-extraction.component.css']
})
export class EntityExtractionComponent implements OnInit{

  sliderValue: number = 0;
  isImageChecked: boolean = false;
  isAbstractChecked: boolean = false;
  isCategoriesChecked: boolean = false;

  ngOnInit() {
  }

  constructor(private extractionService: EntityExtractionService) {
  }
  onSliderInput(event: Event): void {

    const target = event.target as HTMLInputElement;
    const sliderValue = Number(target.value);
    this.sliderValue = sliderValue / 100;
  }

  sendRequest(): void{
    this.extractionService.getEntityData()
  }
}
