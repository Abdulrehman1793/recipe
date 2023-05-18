import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

import { SearchAutocompleteComponent } from './search-autocomplete/search-autocomplete.component';
import { TitleSeachFieldComponent } from './title-seach-field/title-seach-field.component';

const materialModules = [
  MatFormFieldModule,
  MatInputModule,
  MatSelectModule,
  MatChipsModule,
  MatIconModule,
  MatAutocompleteModule,
  MatProgressSpinnerModule,
  MatButtonModule,
  MatDialogModule,
];

@NgModule({
  declarations: [SearchAutocompleteComponent, TitleSeachFieldComponent],
  imports: [CommonModule, ReactiveFormsModule, ...materialModules],
  exports: [SearchAutocompleteComponent],
})
export class SearchModule {}
