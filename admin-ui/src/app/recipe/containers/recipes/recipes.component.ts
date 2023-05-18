import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { TableComponent } from 'src/app/shared/table/containers/table/table.component';
import { CustomColumn } from 'src/app/shared/table/models/table-column.model';

import { Recipe } from '../../models';
import { Search } from 'src/app/core/models';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store';
import { recipes, recipes_request, update_recipes_page } from '../../store';

export interface UserData {
  id: string;
  name: string;
  progress: string;
  fruit: string;
}

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss'],
})
export class RecipesComponent implements OnInit, OnDestroy {
  loading: boolean = false;
  error: string | undefined;

  resultLenght = 0;
  recipes: Recipe[] = [];
  recipesSubscription: Subscription;
  recipeCustomColumns: CustomColumn[] = [
    { columnDef: 'id', sort: false, title: 'Id' },
    {
      columnDef: 'title',
      title: 'Title',
      sort: true,
    },
    {
      columnDef: 'description',
      title: 'Description',
      sort: true,
    },
    {
      columnDef: 'difficulty',
      title: 'Difficulty',
      sort: true,
    },
  ];

  @ViewChild('recipeTable') recipeTable = TableComponent<Recipe>;

  constructor(private router: Router, private store: Store<AppState>) {
    this.recipesSubscription = this.store
      .select(recipes)
      .subscribe((recipes) => {
        this.recipes = recipes;
      });
  }

  ngOnInit() {
    this.store.dispatch(recipes_request());
  }

  ngOnDestroy(): void {
    this.recipesSubscription.unsubscribe();
  }

  onSortAndPageUpdate(search: Search) {
    this.store.dispatch(update_recipes_page({ search }));
  }

  onCreate() {
    this.router.navigate(['recipe', 'new']);
  }

  onUpdate(data: Recipe) {
    this.router.navigate(['recipe', data.id]);
  }
}
