import { createFeatureSelector, createSelector } from '@ngrx/store';
import { RecipeState } from './recipe.state';

export const RECIPE_STATE_NAME = 'recipe';

const getRecipeState = createFeatureSelector<RecipeState>(RECIPE_STATE_NAME);

export const recipes = createSelector(getRecipeState, (state) => state.recipes);
export const recipe = createSelector(getRecipeState, (state) => state.recipe);
export const recipe_search = createSelector(
  getRecipeState,
  (state) => state.search
);
export const loading = createSelector(getRecipeState, (state) => state.loading);
export const failure = createSelector(getRecipeState, (state) => state.failure);

export const ingredients = createSelector(
  getRecipeState,
  (state) => state.ingredients
);
export const preparationSteps = createSelector(
  getRecipeState,
  (state) => state.preparationSteps
);
