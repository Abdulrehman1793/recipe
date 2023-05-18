import { Injectable } from '@angular/core';
import { DefaultHttpUrlGenerator } from '@ngrx/data';

@Injectable()
export class CustomHttpUrlGenerator extends DefaultHttpUrlGenerator {
  // Override the getResourceUrl method to remove the leading slash
  getResourceUrl(entityName: string, root: string): string {
    const url = super.getResourceUrls(entityName, root);
    return url.collectionResourceUrl.startsWith('/')
      ? url.collectionResourceUrl.slice(1)
      : url.collectionResourceUrl;
  }
}
