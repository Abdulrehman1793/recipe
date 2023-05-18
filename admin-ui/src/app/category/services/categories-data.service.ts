import { DefaultDataService, HttpUrlGenerator } from '@ngrx/data';
import { Category } from '../models/category';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class CategoriesDataService extends DefaultDataService<Category> {
  constructor(http: HttpClient, httpUrlGenerator: HttpUrlGenerator) {
    super('Category', http, httpUrlGenerator, {
      root: 'http://localhost:8080/api/v2',
      trailingSlashEndpoints: false,//TODO:trailing slash not working
    });
  }
}
