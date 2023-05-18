import { Action, createReducer, on } from '@ngrx/store';

import * as IngredientActions from './ingredient.actions';
import { RecipeState, initialRecipeState } from '../recipe';

const _ingredientReducer = createReducer(
  initialRecipeState,
  on(IngredientActions.findAllIngredients, (state) => ({
    ...state,
    loading: true,
  })),
  on(IngredientActions.findAllIngredientsSuccess, (state, { ingredients }) => {
    return { ...state, loading: false };
  }),
  on(IngredientActions.findAllIngredientsFailure, (state, { error }) => ({
    ...state,
    failure: error,
    loading: false,
  })),
  on(IngredientActions.addIngredientSuccess, (state) => {
    const recipe = { ...state.recipe };
    // recipe.ingredients.push(ingredient);
    return { ...state };
  }),
  on(IngredientActions.updateIngredientSuccess, (state, { ingredient }) => {
    // const recipe = { ...state.recipe };
    // recipe.ingredients = recipe.ingredients.map((i) =>
    //   i.id === ingredient.id ? ingredient : i
    // );
    return { ...state };
  })
);

export function ingredientReducer(
  state: RecipeState | undefined,
  action: Action
) {
  return _ingredientReducer(state, action);
}
