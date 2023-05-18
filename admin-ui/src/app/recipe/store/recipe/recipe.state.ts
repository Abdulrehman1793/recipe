import { Page, Search } from 'src/app/core/models';
import { Ingredient, PreparationStep, Recipe } from '../../models';

export interface RecipeState {
  page: Page<Recipe> | undefined;
  recipes: Recipe[];
  recipe?: Recipe;
  search: Search;
  loading: boolean;
  failure?: string | undefined;
  ingredients: Ingredient[];
  preparationSteps: PreparationStep[];
}

export const initialRecipeState: RecipeState = {
  page: undefined,
  recipes: [],
  search: {
    page: 0,
    sort: 'id',
    pageSize: 10,
    action: 'page',
    direction: 'asc',
  },
  recipe: undefined,
  loading: false,
  failure: undefined,
  ingredients: [],
  preparationSteps: [],
};
