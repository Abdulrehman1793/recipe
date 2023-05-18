import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SearchFieldConfig } from '../../model/search-field-config';

@Component({
  selector: 'app-title-seach-field',
  templateUrl: './title-seach-field.component.html',
  styleUrls: ['./title-seach-field.component.scss'],
})
export class TitleSeachFieldComponent implements OnInit {
  titleFormControl = new FormControl('');

  constructor(
    public dialogRef: MatDialogRef<TitleSeachFieldComponent>,
    @Inject(MAT_DIALOG_DATA) public searchFieldConfig: SearchFieldConfig
  ) {}

  ngOnInit(): void {
    // console.log(this.searchFieldConfig);
  }

  onSearch() {
    this.dialogRef.close({ save: true, data: this.titleFormControl.value });
  }
}
