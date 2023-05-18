import { Actions, createEffect, ofType } from '@ngrx/effects';
import { PreparationStepService } from '../../services/prep-steps.service';

import * as PreparationStepActions from '../steps';
import { catchError, map, of, switchMap } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class PreparationStepEffects {
  constructor(
    private _actions$: Actions,
    private _preparationStepService: PreparationStepService
  ) {}

  findAllPreparationSteps$ = createEffect(() =>
    this._actions$.pipe(
      ofType(PreparationStepActions.loadPreparationSteps),
      switchMap((action) =>
        this._preparationStepService
          .findAllPreparationSteps(action.recipeId)
          .pipe(
            map((preparationSteps) =>
              PreparationStepActions.loadPreparationStepsSuccess({
                preparationSteps,
              })
            ),
            catchError((error) =>
              of(
                PreparationStepActions.loadPreparationStepsFailure({
                  error: error.message,
                })
              )
            )
          )
      )
    )
  );

  addPreparationStep$ = createEffect(() =>
    this._actions$.pipe(
      ofType(PreparationStepActions.addPreparationStep),
      switchMap((action) =>
        this._preparationStepService
          .addPreparationStep(action.recipeId, action.preparationStep)
          .pipe(
            map((preparationStep) =>
              PreparationStepActions.addPreparationStepSuccess({
                preparationStep,
              })
            ),
            catchError((error) =>
              of(
                PreparationStepActions.addPreparationStepFailure({
                  error: error.message,
                })
              )
            )
          )
      )
    )
  );

  updatePreparationStep$ = createEffect(() =>
    this._actions$.pipe(
      ofType(PreparationStepActions.updatePreparationStep),
      switchMap((action) =>
        this._preparationStepService
          .updatePreparationStep(
            action.preparationStepId,
            action.preparationStep
          )
          .pipe(
            map((preparationStep) =>
              PreparationStepActions.updatePreparationStepSuccess({
                preparationStep,
              })
            ),
            catchError((error) =>
              of(
                PreparationStepActions.updatePreparationStepFailure({
                  error: error.message,
                })
              )
            )
          )
      )
    )
  );
}
