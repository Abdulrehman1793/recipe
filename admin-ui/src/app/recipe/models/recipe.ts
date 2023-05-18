export interface Recipe {
  id?: string;
  title: string;
  subTitle: string;
  description: string;
  prepTime?: number;
  cookTime?: number;
  servings?: number;
  difficulty: any;
  ingredients?: any[];
  preparationSteps?: any[];
  categories: any;
}
