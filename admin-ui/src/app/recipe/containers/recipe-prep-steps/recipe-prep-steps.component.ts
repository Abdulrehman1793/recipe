import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { PrepStepsUpdateComponent } from '../../dialogs/prep-steps-update/prep-steps-update.component';
import { PreparationStep, Recipe } from '../../models';
import { Store } from '@ngrx/store';
import {
  RecipeState,
  ingredients,
  loadPreparationSteps,
  preparationSteps,
} from '../../store';

@Component({
  selector: 'app-recipe-prep-steps',
  templateUrl: './recipe-prep-steps.component.html',
  styleUrls: ['./recipe-prep-steps.component.scss'],
})
export class RecipePrepStepsComponent implements OnInit {
  @Input()
  recipe: Recipe;

  prepSteps$: Observable<PreparationStep[]>;

  constructor(private dialog: MatDialog, private store: Store<RecipeState>) {
    this.prepSteps$ = store.select(preparationSteps);
  }

  ngOnInit(): void {
    if (this.recipe && this.recipe.id)
      this.store.dispatch(loadPreparationSteps({ recipeId: this.recipe.id }));
  }

  onAdd() {
    this.dialog.open(PrepStepsUpdateComponent, {
      data: { recipe: this.recipe },
    });
  }

  onEdit(preparationStep: PreparationStep) {
    this.dialog.open(PrepStepsUpdateComponent, {
      data: { recipe: this.recipe, preparationStep },
    });
  }
}
