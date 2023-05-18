import { DialogRef } from '@angular/cdk/dialog';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Recipe } from '../../models';

@Component({
  selector: 'app-recipe-update-dialog',
  templateUrl: './recipe-update-dialog.component.html',
  styleUrls: ['./recipe-update-dialog.component.scss'],
})
export class RecipeUpdateDialogComponent {
  constructor(
    private dialogRef: DialogRef,
    @Inject(MAT_DIALOG_DATA) public data: Recipe
  ) {}

  ngOnInit(): void {
    console.log(this.data);

    if (this.data) {
    }
  }

  onUpdate() {
    console.log(this.data);
  }
}
