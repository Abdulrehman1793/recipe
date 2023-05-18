import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RecipeSubtitleDialogComponent } from '../../dialogs/recipe-subtitle-dialog/recipe-subtitle-dialog.component';
import { RecipeUpdateDialogComponent } from '../../dialogs/recipe-update-dialog/recipe-update-dialog.component';
import { Recipe } from '../../models';

@Component({
  selector: 'app-recipe-editable-view',
  templateUrl: './recipe-editable-view.component.html',
  styleUrls: ['./recipe-editable-view.component.scss'],
})
export class RecipeEditableViewComponent implements OnChanges {
  @Input() recipe: Recipe;

  constructor(public dialog: MatDialog) {}

  ngOnChanges(changes: SimpleChanges): void {}

  openSubtitleDialog() {
    this.dialog.open(RecipeSubtitleDialogComponent, {
      width: '50%',
      data: this.recipe.subTitle,
    });
  }

  onEdit() {
    this.dialog.open(RecipeUpdateDialogComponent, {
      height: '75%',
      width: '75%',
      data: this.recipe,
    });
  }
}
