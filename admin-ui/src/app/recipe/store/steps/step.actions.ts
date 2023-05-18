import { createAction, props } from '@ngrx/store';
import { PreparationStep } from '../../models';

export const loadPreparationSteps = createAction(
  '[Preparation Steps] Load Preparation Steps',
  props<{ recipeId: string }>()
);
export const loadPreparationStepsSuccess = createAction(
  '[Preparation Steps] Load Preparation Steps Success',
  props<{ preparationSteps: PreparationStep[] }>()
);
export const loadPreparationStepsFailure = createAction(
  '[Preparation Steps] Load Preparation Steps Failure',
  props<{ error: any }>()
);

export const addPreparationStep = createAction(
  '[Preparation Steps] Add Preparation Step',
  props<{ recipeId: string; preparationStep: any }>()
);
export const addPreparationStepSuccess = createAction(
  '[Preparation Steps] Add Preparation Step Success',
  props<{ preparationStep: PreparationStep }>()
);
export const addPreparationStepFailure = createAction(
  '[Preparation Steps] Add Preparation Step Failure',
  props<{ error: any }>()
);

export const updatePreparationStep = createAction(
  '[Preparation Steps] Update Preparation Step',
  props<{ preparationStepId: number; preparationStep: any }>()
);
export const updatePreparationStepSuccess = createAction(
  '[Preparation Steps] Update Preparation Step Success',
  props<{ preparationStep: PreparationStep }>()
);
export const updatePreparationStepFailure = createAction(
  '[Preparation Steps] Update Preparation Step Failure',
  props<{ error: any }>()
);
