import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { Store } from '@ngrx/store';

import { MatDialog } from '@angular/material/dialog';

import { IngredientsUpdateComponent } from '../../dialogs/ingredients-update/ingredients-update.component';
import { Ingredient, Recipe } from '../../models';
import { AppState } from 'src/app/store';
import { findAllIngredients, ingredients } from '../../store';

@Component({
  selector: 'app-recipe-ingredients',
  templateUrl: './recipe-ingredients.component.html',
  styleUrls: ['./recipe-ingredients.component.scss'],
})
export class RecipeIngredientsComponent implements OnInit {
  @Input()
  recipe: Recipe;

  ingredients$: Observable<Ingredient[]>;

  constructor(private dialog: MatDialog, private store: Store<AppState>) {
    this.ingredients$ = store.select(ingredients);
  }

  ngOnInit(): void {
    if (this.recipe && this.recipe.id)
      this.store.dispatch(findAllIngredients({ recipeId: this.recipe.id }));
  }

  onAdd() {
    this.dialog.open(IngredientsUpdateComponent, {
      data: { recipe: this.recipe },
    });
  }

  onEdit(ingredient: Ingredient) {
    this.dialog.open(IngredientsUpdateComponent, {
      data: { recipe: this.recipe, ingredient },
    });
  }
}
