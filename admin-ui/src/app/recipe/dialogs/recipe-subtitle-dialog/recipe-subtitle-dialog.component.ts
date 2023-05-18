import { DialogRef } from '@angular/cdk/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-recipe-subtitle-dialog',
  templateUrl: './recipe-subtitle-dialog.component.html',
  styleUrls: ['./recipe-subtitle-dialog.component.scss'],
})
export class RecipeSubtitleDialogComponent implements OnInit {
  subTitle: FormControl = new FormControl('');

  constructor(
    private dialogRef: DialogRef,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      this.subTitle.setValue(this.data);
    }
  }

  onUpdate() {
    console.log(this.data);
  }
}
