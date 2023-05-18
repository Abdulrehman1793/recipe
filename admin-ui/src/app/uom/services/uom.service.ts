// import { HttpClient, HttpParams } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { UnitOfMeasure } from '../models/uom';

// @Injectable({
//   providedIn: 'root',
// })
// export class UomService {
//   rootUrl: string = 'http://localhost:8080/api/v2/uom';

//   constructor(private _http: HttpClient) {}

//   findAll(uom?: string) {
//     let params = new HttpParams();
//     if (uom) params = params.append('uom', uom);

//     return this._http.get<UnitOfMeasure[]>(`${this.rootUrl}`, { params });
//   }

//   create(uom: string) {
//     return this._http.post<UnitOfMeasure>(`${this.rootUrl}`, { uom });
//   }

//   update(uom: string, id?: number) {
//     return this._http.put<UnitOfMeasure>(`${this.rootUrl}/${id}`, { uom });
//   }
// }


import { Injectable } from '@angular/core';
import {
  EntityCollectionServiceBase,
  EntityCollectionServiceElementsFactory,
} from '@ngrx/data';
import { UnitOfMeasure } from '../models/uom';

@Injectable()
export class UomService extends EntityCollectionServiceBase<UnitOfMeasure> {
  constructor(serviceElementsFactory: EntityCollectionServiceElementsFactory) {
    super('UOM', serviceElementsFactory);
  }
}
