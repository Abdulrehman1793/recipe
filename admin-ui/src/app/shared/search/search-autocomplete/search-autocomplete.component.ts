import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatDialog } from '@angular/material/dialog';

import { SearchFieldConfig } from '../../model/search-field-config';
import { TitleSeachFieldComponent } from '../title-seach-field/title-seach-field.component';

@Component({
  selector: 'app-search-autocomplete',
  templateUrl: './search-autocomplete.component.html',
  styleUrls: ['./search-autocomplete.component.scss'],
})
export class SearchAutocompleteComponent implements OnInit {
  separatorKeysCodes: number[] = [ENTER, COMMA];
  searchCtrl = new FormControl('');

  selectedSearchFields: SearchFieldConfig[] = [];
  searchFields: SearchFieldConfig[] = [
    { name: 'title', type: 'string' },
    { name: 'description', type: 'string' },
    { name: 'difficulty', type: 'string' },
  ];
  unSelectedSearchFields: SearchFieldConfig[];

  @ViewChild('searchInput') searchInput: ElementRef<HTMLInputElement>;

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {
    this.resetFilteredValues();
  }

  add(event: MatChipInputEvent): void {
    // const value = (event.value || '').trim();
    // if (
    //   value &&
    //   this.unSelectedSearchFields.findIndex(
    //     (searchField) => searchField.name === value
    //   ) > -1
    // ) {
    //   this.selectedSearchFields.push(this.getSearchFieldByName(value));
    //   console.log(this.selectedSearchFields);
    //   this.resetFilteredValues();
    // }
    // // Clear the input value
    // event.chipInput!.clear();
    // this.searchCtrl.setValue(null);
  }

  remove(searchFieldConfig: SearchFieldConfig): void {
    const index = this.selectedSearchFields.findIndex(
      (selected) => selected.name == searchFieldConfig.name
    );

    if (index > -1) {
      this.selectedSearchFields.splice(index, 1);
    }
    this.resetFilteredValues();
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    let selectedSearchFieldConfig = this.getSearchFieldByName(
      event.option.value
    );

    this.openDialog(selectedSearchFieldConfig)
      .afterClosed()
      .subscribe((data) => {
        if (data.save) {
          // TODO: implement add search only apply on filter
          this.selectedSearchFields.push(selectedSearchFieldConfig);
          this.searchInput.nativeElement.value = '';
          this.searchCtrl.setValue(null);

          this.resetFilteredValues();
        }
      });
  }

  resetFilteredValues() {
    this.unSelectedSearchFields = this.searchFields.filter(
      (row) =>
        this.selectedSearchFields.findIndex(
          (searchField) => searchField.name === row.name
        ) <= -1
    );
  }

  getSearchFieldByName(value: string) {
    let index = this.searchFields.findIndex(
      (searchField) => searchField.name === value
    );
    return this.searchFields[index];
  }

  openDialog(searchFieldConfig: SearchFieldConfig) {
    const { x, y } = this.searchInput.nativeElement.getBoundingClientRect();

    return this.dialog.open(TitleSeachFieldComponent, {
      data: searchFieldConfig,
      position: { left: `${x}px`, top: `${y + 25}px` },
    });
  }
}
