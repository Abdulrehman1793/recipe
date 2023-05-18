import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Page, Search } from 'src/app/core/models';

import { Recipe } from '../models';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  rootUrl: string = 'http://localhost:8080/recipe';

  constructor(private _http: HttpClient) {}

  findPage(search: Search): Observable<Page<Recipe>> {
    let params = new HttpParams();

    params = params.append('page', search.page);
    params = params.append('size', search.pageSize);

    if ((search.sort && search.direction) || search.action === 'sort') {
      params = params.append('sort', search.sort + ',' + search.direction);
    }
    return this._http.get<Page<Recipe>>(`${this.rootUrl}/load`, { params });
  }

  findRecipeById(id: string) {
    return this._http.get<Recipe>(`${this.rootUrl}/${id}`);
  }

  create(recipe: Recipe): Observable<Recipe> {
    return this._http.post<Recipe>(`${this.rootUrl}`, recipe);
  }

  update(id: string, recipe: Recipe): Observable<Recipe> {
    return this._http.put<Recipe>(`${this.rootUrl}/${id}`, recipe);
  }
}
