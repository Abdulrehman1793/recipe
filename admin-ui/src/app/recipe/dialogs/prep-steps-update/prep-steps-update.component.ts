import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Store, select } from '@ngrx/store';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { PreparationStep, Recipe } from '../../models';
import {
  RecipeState,
  addPreparationStep,
  failure,
  loading,
  updatePreparationStep,
} from '../../store';
import { skipWhile, switchMap, take } from 'rxjs';
import { PreparationStepService } from '../../services/prep-steps.service';

interface DialogData {
  recipe: Recipe;
  preparationStep: PreparationStep;
}

@Component({
  selector: 'app-prep-steps-update',
  templateUrl: './prep-steps-update.component.html',
  styleUrls: ['./prep-steps-update.component.scss'],
})
export class PrepStepsUpdateComponent implements OnInit {
  title = 'New Preparation step';
  form = this.fb.group({
    title: ['', [Validators.required]],
    description: ['', [Validators.required]],
    images: new FormData(),
  });

  constructor(
    private service: PreparationStepService,
    private fb: FormBuilder,
    private store: Store<RecipeState>,
    public dialogRef: MatDialogRef<PrepStepsUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: DialogData
  ) {}

  ngOnInit(): void {
    if (this.dialogData && this.dialogData.preparationStep) {
      let step = this.dialogData.preparationStep;
      this.form.patchValue({
        description: step.description,
        title: step.title,
      });
      this.title = 'Update ' + step.title;
    }
  }

  onSave() {
    if (this.form.valid) {
      if (this.dialogData.recipe && this.dialogData.recipe.id) {
        if (this.dialogData.preparationStep) {
          this.store.dispatch(
            updatePreparationStep({
              preparationStepId: this.dialogData.preparationStep.id,
              preparationStep: { ...this.form.value },
            })
          );
        } else {
          this.service
            .addWithImage(
              this.dialogData.recipe.id,
              {
                ...this.form.value,
              },
              this.files
            )
            .subscribe();
          // this.store.dispatch(
          //   addPreparationStep({
          //     recipeId: this.dialogData.recipe.id,
          //     preparationStep: { ...this.form.value },
          //   })
          // );
        }
      }
      this.closeDialog();
    }
  }

  closeDialog() {
    console.log('close dialog');

    this.store
      .pipe(
        select(loading),
        skipWhile((loading) => loading),
        switchMap(() => this.store.select(failure)),
        take(1)
      )
      .subscribe((failure) => {
        if (!failure) {
          this.dialogRef.close();
        }
      });
  }

  imagesUrl: string[] = [];
  files: File[] = [];
  onUploadFile(target: EventTarget) {
    this.imagesUrl = [];
    const files = (target as HTMLInputElement).files || [];

    const formData = new FormData();
    for (var i = 0; i < files.length; i++) {
      const file = files[i];
      formData.append('files', file, file.name);

      const reader = new FileReader();
      reader.onload = () => {
        this.imagesUrl = [...this.imagesUrl, reader.result as string];
      };
      reader.readAsDataURL(file);
      this.files = [...this.files, file];
    }
    // this.form.patchValue({
    //   images: formData,
    // });
  }
}
