import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecipeRoutingModule } from './recipe.routing.module';
import { RecipeDetailComponent } from './pages/recipe-detail/recipe-detail.component';
import { RecipesComponent } from './pages/recipes/recipes.component';

@NgModule({
  declarations: [
    RecipeDetailComponent,
    RecipesComponent
  ],
  imports: [CommonModule, RecipeRoutingModule],
})
export class RecipeModule {}
