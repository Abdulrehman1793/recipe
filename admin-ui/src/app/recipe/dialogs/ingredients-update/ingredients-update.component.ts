import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Observable, skipWhile, switchMap, take } from 'rxjs';
import { Store, select } from '@ngrx/store';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { UnitOfMeasure } from 'src/app/uom/models/uom';
import { UomService } from 'src/app/uom/services/uom.service';
import { Ingredient, Recipe } from '../../models';
import { IngredientService } from '../../services/ingredient.service';
import {
  RecipeState,
  addIngredient,
  failure,
  loading,
  updateIngredient,
} from '../../store';

interface DialogData {
  recipe: Recipe;
  ingredient: Ingredient;
}

@Component({
  selector: 'app-ingredients-update',
  templateUrl: './ingredients-update.component.html',
  styleUrls: ['./ingredients-update.component.scss'],
})
export class IngredientsUpdateComponent implements OnInit {
  title = 'New Ingredient';
  uoms$: Observable<UnitOfMeasure[]>;

  form = this.fb.group({
    description: ['', [Validators.required]],
    amount: [
      0,
      [Validators.required, Validators.min(1), Validators.max(100000)],
    ],
    uomId: [-1, [Validators.required]],
  });

  constructor(
    private fb: FormBuilder,
    private store: Store<RecipeState>,
    private uomService: UomService,
    public dialogRef: MatDialogRef<IngredientsUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: DialogData
  ) {}

  ngOnInit(): void {
    if (this.dialogData && this.dialogData.ingredient) {
      let ingredient = this.dialogData.ingredient;
      this.form.patchValue({
        description: ingredient.description,
        amount: ingredient.amount,
        uomId: ingredient.unitOfMeasure.id,
      });
      this.title = 'Update Ingredient';
    }

    this.uoms$ = this.uomService.getAll();
  }

  onSave() {
    if (this.form.valid) {
      if (this.dialogData.recipe && this.dialogData.recipe.id) {
        if (this.dialogData.ingredient) {
          let ingredientId = this.dialogData.ingredient.id;
          this.store.dispatch(
            updateIngredient({
              ingredientId,
              ingredient: { ...this.form.value },
            })
          );
        } else {
          this.store.dispatch(
            addIngredient({
              recipeId: this.dialogData.recipe.id,
              ingredient: { ...this.form.value },
            })
          );
        }
      }
      this.closeDialog();
    }
  }

  closeDialog() {
    console.log('close dialog');

    this.store
      .pipe(
        select(loading),
        skipWhile((loading) => loading),
        switchMap(() => this.store.select(failure)),
        take(1)
      )
      .subscribe((failure) => {
        if (!failure) {
          this.dialogRef.close();
        }
      });
  }
}
