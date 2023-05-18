import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

import * as IngredientActions from './ingredient.actions';
import { IngredientService } from '../../services/ingredient.service';

@Injectable()
export class IngredientEffects {
  constructor(
    private actions$: Actions,
    private ingredientService: IngredientService
  ) {}

  findAllIngredients$ = createEffect(() =>
    this.actions$.pipe(
      ofType(IngredientActions.findAllIngredients),
      switchMap(({ recipeId }) =>
        this.ingredientService.findAllIngredients(recipeId).pipe(
          map((ingredients) =>
            IngredientActions.findAllIngredientsSuccess({ ingredients })
          ),
          catchError((error) =>
            of(
              IngredientActions.findAllIngredientsFailure({
                error: error.message,
              })
            )
          )
        )
      )
    )
  );

  addIngredient$ = createEffect(() =>
    this.actions$.pipe(
      ofType(IngredientActions.addIngredient),
      switchMap(({ recipeId, ingredient }) =>
        this.ingredientService.addIngredient(recipeId, ingredient).pipe(
          map((data) =>
            IngredientActions.addIngredientSuccess({
              ingredient: data,
            })
          ),
          catchError((error) =>
            of(IngredientActions.addIngredientFailure({ error: error.message }))
          )
        )
      )
    )
  );

  updateIngredient$ = createEffect(() =>
    this.actions$.pipe(
      ofType(IngredientActions.updateIngredient),
      switchMap(({ ingredientId, ingredient }) =>
        this.ingredientService.updateIngredient(ingredientId, ingredient).pipe(
          map((data) =>
            IngredientActions.updateIngredientSuccess({ ingredient: data })
          ),
          catchError((error) =>
            of(
              IngredientActions.updateIngredientFailure({
                error: error.message,
              })
            )
          )
        )
      )
    )
  );
}
