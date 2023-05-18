import { Injectable } from '@angular/core';
import {
  EntityCollectionServiceBase,
  EntityCollectionServiceElementsFactory,
} from '@ngrx/data';
import { Category } from '../models/category';

@Injectable()
export class CategoryService extends EntityCollectionServiceBase<Category> {
  constructor(serviceElementsFactory: EntityCollectionServiceElementsFactory) {
    super('Category', serviceElementsFactory);
  }
}
