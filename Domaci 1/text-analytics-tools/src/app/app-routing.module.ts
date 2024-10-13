import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {EntityExtractionComponent} from "./components/entity-extraction/entity-extraction.component";
import {TextSimilarityComponent} from "./components/text-similarity/text-similarity.component";
import {LanguageDetectionComponent} from "./components/language-detection/language-detection.component";
import {SentimentAnalysisComponent} from "./components/sentiment-analysis/sentiment-analysis.component";
import {ConfigurationComponent} from "./components/configuration/configuration.component";
import {HistoryComponent} from "./components/history/history.component";
import {AuthGuard} from "./auth.guard";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent,

  },
  {
    path:"entity-extraction",
    component: EntityExtractionComponent,
    canActivate: [AuthGuard],
    canDeactivate: [AuthGuard]
  },
  {
    path:"text-similarity",
    component: TextSimilarityComponent,
    canActivate: [AuthGuard],
    canDeactivate: [AuthGuard]
  },
  {
    path:"language-detection",
    component:LanguageDetectionComponent,
    canActivate: [AuthGuard],
    canDeactivate: [AuthGuard]
  },
  {
    path:"sentiment-analysis",
    component:SentimentAnalysisComponent,
    canActivate: [AuthGuard],
    canDeactivate: [AuthGuard]
  },
  {
    path:"history",
    component:HistoryComponent,
    canActivate: [AuthGuard],
    canDeactivate: [AuthGuard]
  },
  {
    path:"configuration",
    component:ConfigurationComponent,

  },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
