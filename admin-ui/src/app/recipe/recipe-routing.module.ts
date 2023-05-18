import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecipeComponent } from './containers/recipe/recipe.component';
import { RecipesComponent } from './containers/recipes/recipes.component';

const routes: Routes = [
  { path: '', component: RecipesComponent },
  { path: 'new', component: RecipeComponent },
  { path: ':id', component: RecipeComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RecipeRoutingModule {}
