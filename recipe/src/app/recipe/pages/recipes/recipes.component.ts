import { Component } from '@angular/core';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss'],
})
export class RecipesComponent {
  recipes = [
    { title: 'Italian Chicken Marinade', slug: 'italian-chicken-marinade' },
    { title: 'Pickled Egg Salad Sandwich', slug: 'pickled-egg-salad-sandwich' },
  ];
}
