import {
  EntityMetadataMap,
  EntityDataModuleConfig,
  DefaultHttpUrlGenerator,
} from '@ngrx/data';

const entityMetadata: EntityMetadataMap = { Category: {}, UOM: {} };

const pluralNames = { Category: 'Category', UOM: 'uom' };

const httpUrlGenerator = {
  urlBuilder: DefaultHttpUrlGenerator,
  options: {
    trailingSlash: true,
  },
};

export const entityConfig: EntityDataModuleConfig = {
  entityMetadata,
  pluralNames,
};
