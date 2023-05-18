import { DefaultDataService, HttpUrlGenerator } from '@ngrx/data';
import { UnitOfMeasure } from '../models/uom';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class UomDataService extends DefaultDataService<UnitOfMeasure> {
  constructor(http: HttpClient, httpUrlGenerator: HttpUrlGenerator) {
    super('UOM', http, httpUrlGenerator, {
      root: 'http://localhost:8080/api/v2',
      trailingSlashEndpoints: false,//TODO:trailing slash not working
    });
  }
}
