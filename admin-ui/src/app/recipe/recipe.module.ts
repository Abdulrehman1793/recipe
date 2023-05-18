import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { RecipeRoutingModule } from './recipe-routing.module';
import { RecipesComponent } from './containers/recipes/recipes.component';
import { TableModule } from '../shared/table/table.module';
import { RecipeService } from './services/recipe.service';
import { SearchModule } from '../shared/search/search.module';
import { RecipeComponent } from './containers/recipe/recipe.component';
import { RecipeFormComponent } from './components/recipe-form/recipe-form.component';
import { RecipeSubtitleDialogComponent } from './dialogs/recipe-subtitle-dialog/recipe-subtitle-dialog.component';
import { RecipeContentDialogComponent } from './dialogs/recipe-content-dialog/recipe-content-dialog.component';
import { RecipeEditableViewComponent } from './components/recipe-editable-view/recipe-editable-view.component';
import { RecipeUpdateDialogComponent } from './dialogs/recipe-update-dialog/recipe-update-dialog.component';
import { CategoryService } from '../category/services/category.service';
import { RecipeIngredientsComponent } from './containers/recipe-ingredients/recipe-ingredients.component';
import { RecipePrepStepsComponent } from './containers/recipe-prep-steps/recipe-prep-steps.component';
import { IngredientsUpdateComponent } from './dialogs/ingredients-update/ingredients-update.component';
import { UomService } from '../uom/services/uom.service';
import { IngredientService } from './services/ingredient.service';
import { PrepStepsUpdateComponent } from './dialogs/prep-steps-update/prep-steps-update.component';
import {
  IngredientEffects,
  RECIPE_STATE_NAME,
  RecipeEffects,
  recipeReducer,
} from './store';
import { PreparationStepService } from './services/prep-steps.service';
import { PreparationStepEffects } from './store/steps/step.effects';

const materials = [
  MatToolbarModule,
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule,
  MatRadioModule,
  MatSelectModule,
  MatDialogModule,
  MatIconModule,
  MatListModule,
  MatSnackBarModule,
];

@NgModule({
  declarations: [
    RecipesComponent,
    RecipeComponent,
    RecipeFormComponent,
    RecipeSubtitleDialogComponent,
    RecipeContentDialogComponent,
    RecipeEditableViewComponent,
    RecipeUpdateDialogComponent,
    RecipeIngredientsComponent,
    RecipePrepStepsComponent,
    IngredientsUpdateComponent,
    PrepStepsUpdateComponent,
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    RecipeRoutingModule,
    TableModule,
    SearchModule,
    ...materials,
    StoreModule.forFeature(RECIPE_STATE_NAME, recipeReducer),
    EffectsModule.forFeature([
      RecipeEffects,
      IngredientEffects,
      PreparationStepEffects,
    ]),
  ],
  providers: [
    RecipeService,
    CategoryService,
    UomService,
    IngredientService,
    PreparationStepService,
  ],
})
export class RecipeModule {}
