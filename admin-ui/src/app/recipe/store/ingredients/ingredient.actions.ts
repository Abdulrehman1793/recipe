import { createAction, props } from '@ngrx/store';
import { Ingredient } from '../../models';

export const findAllIngredients = createAction(
  '[Ingredients] Find All',
  props<{ recipeId: string }>()
);
export const findAllIngredientsSuccess = createAction(
  '[Ingredients] Find All Success',
  props<{ ingredients: Ingredient[] }>()
);
export const findAllIngredientsFailure = createAction(
  '[Ingredients] Find All Failure',
  props<{ error: string }>()
);

export const addIngredient = createAction(
  '[Ingredients] Add',
  props<{ recipeId: string; ingredient: any }>()
);
export const addIngredientSuccess = createAction(
  '[Ingredients] Add Success',
  props<{ ingredient: Ingredient }>()
);
export const addIngredientFailure = createAction(
  '[Ingredients] Add Failure',
  props<{ error: string }>()
);

export const updateIngredient = createAction(
  '[Ingredients] Update',
  props<{ ingredientId: number; ingredient: any }>()
);
export const updateIngredientSuccess = createAction(
  '[Ingredients] Update Success',
  props<{ ingredient: Ingredient }>()
);
export const updateIngredientFailure = createAction(
  '[Ingredients] Update Failure',
  props<{ error: string }>()
);
