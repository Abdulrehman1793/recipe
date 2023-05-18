import { createAction, props } from '@ngrx/store';
import { Recipe } from '../../models';
import { Page, Search } from 'src/app/core/models';

// ALL Recipes
export const recipes_request = createAction('[Recipe] Get recipes request');
export const recipes_success = createAction(
  '[Recipe] Get recipes success',
  props<{ page: Page<Recipe> }>()
);
export const recipes_fail = createAction(
  '[Recipe] Get recipes fail',
  props<{ failure?: any }>()
);

export const update_recipes_page = createAction(
  '[Recipe] Get update recipes page',
  props<{ search: Search }>()
);

export const recipe_request = createAction(
  '[Recipe] Get recipe request',
  props<{ id: string; tab: 'recipe' | 'ingredients' | 'steps' }>()
);
export const recipe_success = createAction(
  '[Recipe] Get recipe success',
  props<{ recipe: Recipe }>()
);
export const recipe_fail = createAction(
  '[Recipe] Get recipe fail',
  props<{ failure?: any }>()
);

// Create Recipe
export const createRecipe = createAction(
  '[Recipe] Create Recipe',
  props<{ recipe: Recipe }>()
);
export const createRecipeSuccess = createAction(
  '[Recipe] Create Recipe Success',
  props<{ recipe: Recipe }>()
);
export const createRecipeError = createAction(
  '[Recipe] Create Recipe Error',
  props<{ error: any }>()
);

// Update Recipe
export const updateRecipe = createAction(
  '[Recipe] Update Recipe',
  props<{ id: string; recipe: Recipe }>()
);
export const updateRecipeSuccess = createAction(
  '[Recipe] Update Recipe Success',
  props<{ recipe: Recipe }>()
);
export const updateRecipeError = createAction(
  '[Recipe] Update Recipe Error',
  props<{ error: any }>()
);
