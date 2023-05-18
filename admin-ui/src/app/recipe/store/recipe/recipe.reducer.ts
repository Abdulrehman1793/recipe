import { Action, createReducer, on } from '@ngrx/store';
import { RecipeState, initialRecipeState } from './recipe.state';
import * as RecipeActions from './recipe.actions';
import * as IngredientActions from '../ingredients';
import * as PreparationStepActions from '../steps';

const _recipeReducer = createReducer(
  initialRecipeState,
  on(RecipeActions.recipes_request, () => {
    return {
      ...initialRecipeState,
      loading: true,
    };
  }),
  on(RecipeActions.recipes_success, (state, { page }) => {
    return {
      ...state,
      loading: false,
      page,
      recipes: page.content,
    };
  }),
  on(RecipeActions.recipes_fail, (state, { failure }) => ({
    ...state,
    loading: false,
    failure,
  })),
  on(RecipeActions.update_recipes_page, (state, { search }) => ({
    ...state,
    loading: true,
    search,
  })),
  // Get single recipe
  on(RecipeActions.recipe_request, (state) => {
    return {
      ...state,
      loading: true,
      failure: undefined,
    };
  }),
  on(RecipeActions.recipe_success, (state, { recipe }) => {
    return {
      ...state,
      loading: false,
      recipe,
    };
  }),
  on(RecipeActions.recipe_fail, (state, { failure }) => ({
    ...state,
    loading: false,
    failure,
  })),
  // Create Recipe
  on(RecipeActions.createRecipe, (state) => ({
    ...state,
    loading: true,
    failure: undefined,
  })),
  on(RecipeActions.createRecipeSuccess, (state, { recipe }) => ({
    ...state,
    recipe,
    loading: false,
  })),
  on(RecipeActions.createRecipeError, (state, { error }) => ({
    ...state,
    loading: false,
    error,
  })),
  on(RecipeActions.updateRecipe, (state) => ({
    ...state,
    loading: true,
    failure: undefined,
  })),
  on(RecipeActions.updateRecipeSuccess, (state, { recipe }) => ({
    ...state,
    recipe,
    loading: false,
  })),
  on(RecipeActions.updateRecipeError, (state, { error }) => ({
    ...state,
    loading: false,
    error,
  })),
  // Ingredients
  on(IngredientActions.findAllIngredients, (state) => ({
    ...state,
    loading: true,
    failure: undefined,
  })),
  on(IngredientActions.findAllIngredientsSuccess, (state, { ingredients }) => {
    return { ...state, loading: false, ingredients };
  }),
  on(IngredientActions.findAllIngredientsFailure, (state, { error }) => ({
    ...state,
    failure: error,
    loading: false,
  })),
  on(IngredientActions.addIngredient, (state) => {
    return { ...state, loading: true, failure: undefined };
  }),
  on(IngredientActions.addIngredientSuccess, (state, { ingredient }) => {
    const ingredients = [...state.ingredients, ingredient];
    return { ...state, ingredients, loading: false };
  }),
  on(IngredientActions.updateIngredient, (state) => {
    return { ...state, loading: true, failure: undefined };
  }),
  on(IngredientActions.updateIngredientSuccess, (state, { ingredient }) => {
    const updatedIngredients = [...state.ingredients];
    const ingredientIndex = state.ingredients.findIndex(
      (row) => ingredient.id === row.id
    );
    updatedIngredients[ingredientIndex] = ingredient;
    return { ...state, ingredients: updatedIngredients, loading: false };
  }),

  // PreparationSteps
  on(PreparationStepActions.loadPreparationSteps, (state) => ({
    ...state,
    loading: true,
  })),
  on(
    PreparationStepActions.loadPreparationStepsSuccess,
    (state, { preparationSteps }) => ({
      ...state,
      preparationSteps,
      loading: false,
    })
  ),
  on(
    PreparationStepActions.loadPreparationStepsFailure,
    (state, { error }) => ({
      ...state,
      failure: error,
      loading: false,
    })
  ),

  on(PreparationStepActions.addPreparationStep, (state) => ({
    ...state,
    loading: true,
  })),
  on(
    PreparationStepActions.addPreparationStepSuccess,
    (state, { preparationStep }) => ({
      ...state,
      preparationSteps: [...state.preparationSteps, preparationStep],
      loading: false,
    })
  ),
  on(PreparationStepActions.addPreparationStepFailure, (state, { error }) => ({
    ...state,
    failure: error,
    loading: false,
  })),

  on(PreparationStepActions.updatePreparationStep, (state) => ({
    ...state,
    loading: true,
  })),
  on(
    PreparationStepActions.updatePreparationStepSuccess,
    (state, { preparationStep }) => ({
      ...state,
      preparationSteps: state.preparationSteps.map((p) =>
        p.id === preparationStep.id ? preparationStep : p
      ),
      loading: false,
    })
  ),
  on(
    PreparationStepActions.updatePreparationStepFailure,
    (state, { error }) => ({
      ...state,
      failure: error,
      loading: false,
    })
  )
);

export function recipeReducer(state: RecipeState, action: Action) {
  return _recipeReducer(state, action);
}
