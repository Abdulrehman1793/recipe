import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Category } from 'src/app/category/models/category';
import { CategoryService } from 'src/app/category/services/category.service';
import { Recipe } from '../../models';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store';
import { createRecipe, updateRecipe } from '../../store';

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.component.html',
  styleUrls: ['./recipe-form.component.scss'],
})
export class RecipeFormComponent implements OnInit, OnChanges {
  @Input()
  recipe: Recipe;

  categories$: Observable<Category[]>;

  form = this.fb.group({
    title: ['', Validators.required],
    subTitle: ['', Validators.required],
    description: ['', Validators.required],
    prepTime: [
      0,
      [Validators.required, Validators.min(0), Validators.max(14400)],
    ],
    cookTime: [
      1,
      [Validators.required, Validators.min(1), Validators.max(14400)],
    ],
    servings: [
      1,
      [Validators.required, Validators.min(1), Validators.max(1000)],
    ],
    difficulty: ['EASY'],
    categories: '',
  });

  constructor(
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private router: Router,
    private store: Store<AppState>
  ) {}

  ngOnInit(): void {}

  onCategory() {
    // console.log(this.form.controls.categories.value);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadData();
  }

  loadData() {
    if (this.recipe) this.form.patchValue(this.recipe);
    this.categories$ = this.categoryService.getAll();
  }

  onSave() {
    if (this.form.valid) {
      let recipe: Recipe = {
        title: this.form.value.title!,
        subTitle: this.form.value.subTitle!,
        description: this.form.value.description!,
        difficulty: this.form.value.difficulty!,
        prepTime: this.form.value.prepTime!,
        cookTime: this.form.value.cookTime!,
        servings: this.form.value.servings!,
        categories: this.form.value.categories,
      };

      if (this.recipe.id) {
        this.store.dispatch(updateRecipe({ id: this.recipe.id, recipe }));
      } else {
        this.store.dispatch(createRecipe({ recipe }));
      }
    }
  }

  onCancel() {
    this.router.navigate(['./recipe']);
  }
}
