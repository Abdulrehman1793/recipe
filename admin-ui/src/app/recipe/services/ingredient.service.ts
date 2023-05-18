import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ingredient } from '../models';

@Injectable({
  providedIn: 'root',
})
export class IngredientService {
  rootUrl: string = 'http://localhost:8080/api/v2/ingredient';

  constructor(private _http: HttpClient) {}

  findAllIngredients(recipeId: string) {
    return this._http.get<Ingredient[]>(`${this.rootUrl}/${recipeId}`);
  }

  addIngredient(
    recipeId: string,
    ingredient: Ingredient
  ): Observable<Ingredient> {
    return this._http.post<Ingredient>(
      `${this.rootUrl}/${recipeId}`,
      ingredient
    );
  }

  updateIngredient(
    ingredientId: number,
    ingredient: Ingredient
  ): Observable<Ingredient> {
    return this._http.put<Ingredient>(
      `${this.rootUrl}/${ingredientId}`,
      ingredient
    );
  }
}
