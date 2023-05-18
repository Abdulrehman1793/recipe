import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { combineLatest, Observable, Subscription } from 'rxjs';
import { Recipe } from '../../models';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store';
import { recipe, recipe_request } from '../../store';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss'],
})
export class RecipeComponent implements OnInit, OnDestroy {
  recipe$: Observable<Recipe | undefined>;
  subscriptionQueryParam: Subscription | undefined;

  tab: 'recipe' | 'ingredients' | 'steps' = 'recipe';

  constructor(private route: ActivatedRoute, private store: Store<AppState>) {
    this.recipe$ = this.store.select(recipe);
  }

  ngOnInit(): void {
    combineLatest([this.route.params, this.route.queryParamMap]).subscribe(
      ([params, queryParams]) => {
        if (params['id'] ) {//&& queryParams.get('tab') == null
          this.store.dispatch(
            recipe_request({ id: params['id'], tab: 'recipe' })
          );
        }
      }
    );

    this.subscriptionQueryParam = this.route.queryParamMap.subscribe((data) => {
      if (data.get('tab') === 'steps') {
        this.tab = 'steps';
      }
      if (data.get('tab') === 'ingredients') {
        this.tab = 'ingredients';
      }
      if (!data.get('tab')) {
        this.tab = 'recipe';
      }
    });
  }

  ngOnDestroy(): void {
    this.subscriptionQueryParam?.unsubscribe();
  }
}
