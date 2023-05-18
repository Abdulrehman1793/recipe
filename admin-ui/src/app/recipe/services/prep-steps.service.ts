import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PreparationStep } from '../models/preparation-steps';

@Injectable({
  providedIn: 'root',
})
export class PreparationStepService {
  rootUrl: string = 'http://localhost:8080/api/v2/steps';

  constructor(private _http: HttpClient) {}

  findAllPreparationSteps(recipeId: string) {
    return this._http.get<PreparationStep[]>(`${this.rootUrl}/${recipeId}`);
  }

  addPreparationStep(recipeId: string, preparationStep: any) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    const formData = new FormData();
    console.log('working', preparationStep);
    debugger;
    for (const key of Object.keys(preparationStep)) {
      const value = preparationStep[key];

      if (value instanceof FileList) {
        for (let i = 0; i < value.length; i++) {
          formData.append(key, value[i]);
        }
      } else {
        formData.append(key, value);
      }
    }
    return this._http.post<PreparationStep>(
      `${this.rootUrl}/images/${recipeId}`,
      formData,
      { headers }
    );
  }

  updatePreparationStep(preparationStepId: number, preparationStep: any) {
    return this._http.put<PreparationStep>(
      `${this.rootUrl}/${preparationStepId}`,
      preparationStep
    );
  }

  addWithImage(recipeId: string, preparationStep: any, image: File[]) {
    const formData = new FormData();
    formData.append('title', preparationStep.title);
    formData.append('description', preparationStep.description);
    formData.append('multipartFile', image[0]);
    console.log('working');

    return this._http.post<PreparationStep>(
      `${this.rootUrl}/images/${recipeId}`,
      formData
    );
  }
}
