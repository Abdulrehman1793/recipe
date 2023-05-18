import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';

import { CategoryRoutingModule } from './category-routing.module';
import { CategoriesComponent } from './containers/categories/categories.component';
import { CategoryService } from './services/category.service';
import { CreateUpdateDialogComponent } from './dialogs/create-update-dialog/create-update-dialog.component';
import { DefaultDataServiceConfig, EntityDataService } from '@ngrx/data';
import { CategoriesDataService } from './services/categories-data.service';

const materialModules = [
  MatButtonModule,
  MatIconModule,
  MatChipsModule,
  MatTooltipModule,
  MatFormFieldModule,
  MatInputModule,
  MatDialogModule,
];

@NgModule({
  declarations: [CategoriesComponent, CreateUpdateDialogComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    CategoryRoutingModule,
    ...materialModules,
  ],
  providers: [
    CategoryService,
    CategoriesDataService,
    // {
    //   provide: DefaultDataServiceConfig,
    //   useValue: {
    //     root: 'http://localhost:8080/api/v2',
    //     trailingSlashEndpoints: false,
    //   },
    // },
  ],
})
export class CategoryModule {
  constructor(
    entityDataService: EntityDataService,
    categoryDataService: CategoriesDataService
  ) {
    entityDataService.registerService('Category', categoryDataService);
  }
}
