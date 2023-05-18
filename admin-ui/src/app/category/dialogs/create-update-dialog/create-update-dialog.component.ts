import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';
import { EMPTY, Observable, map } from 'rxjs';

@Component({
  selector: 'app-create-update-dialog',
  templateUrl: './create-update-dialog.component.html',
  styleUrls: ['./create-update-dialog.component.scss'],
})
export class CreateUpdateDialogComponent implements OnInit, OnDestroy {
  error$: Observable<any> = EMPTY;
  form = this.fb.group({
    title: ['', Validators.required],
    description: [''],
  });

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<CreateUpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public category: Category,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    if (this.category) this.form.patchValue(this.category);

    this.error$ = this.categoryService.errors$.pipe(
      map((data) => data.payload.data.error.message)
    );
  }

  ngOnDestroy(): void {
    this.error$ = EMPTY;
  }

  onSave() {
    if (this.form.valid) {
      let category: Category = {
        title: this.form.controls.title.value!,
        description: this.form.controls.description.value!,
      };
      let category$;
      if (this.category && this.category.id) {
        category$ = this.categoryService.update({
          ...category,
          id: this.category.id,
        });
      } else {
        category$ = this.categoryService.add(category);
      }

      category$.subscribe((response) => {
        this.dialogRef.close(response);
      });
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
