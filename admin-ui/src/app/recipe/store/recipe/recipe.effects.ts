import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { AppState } from 'src/app/store';
import { RecipeService } from '../../services/recipe.service';
import { catchError, concatMap, map, mergeMap, of, switchMap, tap } from 'rxjs';
import {
  createRecipe,
  createRecipeError,
  createRecipeSuccess,
  recipe_request,
  recipe_success,
  recipes_fail,
  recipes_request,
  recipes_success,
  updateRecipe,
  updateRecipeError,
  updateRecipeSuccess,
  update_recipes_page,
} from './recipe.actions';
import { recipe_search } from './recipe.selectors';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class RecipeEffects {
  constructor(
    private router: Router,
    private snackBar: MatSnackBar,
    private actions$: Actions,
    private recipeService: RecipeService,
    private store: Store<AppState>
  ) {}

  loadRecipes$ = createEffect(() =>
    this.actions$.pipe(
      ofType(recipes_request),
      switchMap(() => this.store.select(recipe_search)),
      switchMap((data) => {
        return this.recipeService.findPage(data).pipe(
          map((page) => {
            return recipes_success({ page });
          })
        );
      })
    )
  );

  updateRecipeSearch$ = createEffect(() =>
    this.actions$.pipe(
      ofType(update_recipes_page),
      switchMap(({ search }) => {
        return this.recipeService.findPage(search).pipe(
          map((page) => {
            return recipes_success({ page });
          })
        );
      })
    )
  );

  loadRecipe$ = createEffect(() =>
    this.actions$.pipe(
      ofType(recipe_request),
      switchMap(({ id, tab }) => {
        return this.recipeService.findRecipeById(id).pipe(
          map((recipe) => {
            return recipe_success({ recipe });
          })
        );
      })
    )
  );

  createRecipe$ = createEffect(() =>
    this.actions$.pipe(
      ofType(createRecipe),
      switchMap(({ recipe }) => {
        return this.recipeService.create(recipe).pipe(
          map((recipe) => createRecipeSuccess({ recipe })),
          catchError((error) => of(createRecipeError({ error })))
        );
      })
    )
  );

  updateRecipe$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateRecipe),
      switchMap(({ id, recipe }) =>
        this.recipeService.update(id, recipe).pipe(
          map((recipe) => updateRecipeSuccess({ recipe })),
          catchError((error) => of(updateRecipeError({ error })))
        )
      )
    )
  );

  createRecipeSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(createRecipeSuccess),
        tap(() => {
          this.router.navigate(['/recipe']);
        }),
        concatMap(({ recipe }) => {
          const message = `Recipe "${recipe.title}" created successfully`;
          const snackBarRef = this.snackBar.open(message, 'View Recipe', {
            duration: 5000,
          });
          return snackBarRef
            .onAction()
            .pipe(tap(() => this.router.navigate(['/recipe', recipe.id])));
        })
      ),
    { dispatch: false }
  );

  updateRecipeSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(updateRecipeSuccess),
        tap(() => {
          this.router.navigate(['/recipe']);
        }),
        concatMap(({ recipe }) => {
          const message = `Recipe "${recipe.title}" updated successfully`;
          const snackBarRef = this.snackBar.open(message, 'View Recipe', {
            duration: 5000,
          });
          return snackBarRef
            .onAction()
            .pipe(tap(() => this.router.navigate(['/recipe', recipe.id])));
        })
      ),
    { dispatch: false }
  );
}
