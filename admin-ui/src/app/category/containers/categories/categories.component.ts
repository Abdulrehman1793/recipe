import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { CreateUpdateDialogComponent } from '../../dialogs/create-update-dialog/create-update-dialog.component';
import { Category } from '../../models/category';

import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss'],
})
export class CategoriesComponent implements OnInit {
  categories$: Observable<Category[]>;
  seacrhFormControl = new FormControl('');

  constructor(
    private categoryService: CategoryService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.categories$ = this.categoryService.getAll();
  }

  onSearch() {
    if (this.seacrhFormControl.valid && this.seacrhFormControl.value)
      this.categories$ = this.categoryService.getWithQuery({
        keyword: this.seacrhFormControl.value,
      });
    else this.getAll();
  }

  onAdd() {
    let ref = this.dialog.open(CreateUpdateDialogComponent);

    ref.afterClosed().subscribe((data) => {
      if (data) this.getAll();
    });
  }

  onUpdate(category: Category) {
    let ref = this.dialog.open(CreateUpdateDialogComponent, { data: category });

    ref.afterClosed().subscribe((data) => {
      if (data) this.getAll();
    });
  }
}
