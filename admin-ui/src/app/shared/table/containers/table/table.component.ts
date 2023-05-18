import {
  AfterViewInit,
  Component,
  ViewChild,
  Input,
  OnInit,
  Output,
  EventEmitter,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Search } from 'src/app/core/models';
import { CustomColumn } from '../../models/table-column.model';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent<T> implements OnInit, AfterViewInit {
  @Input() columns: CustomColumn[];
  displayedColumns: string[];

  @Input() pageSize: number = 10;
  @Input() totalElements: number = 0;

  @Input() rows: T[] = [];
  @Input() loading: boolean;
  @Input() error: string | undefined;

  @Output() onAdd: EventEmitter<T> = new EventEmitter();
  @Output() onUpate: EventEmitter<T> = new EventEmitter();

  @Output() onSortAndPageUpdate: EventEmitter<Search> = new EventEmitter();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  constructor() {}

  ngOnInit() {
    this.displayedColumns = this.columns.map((row) => row.columnDef);
    this.displayedColumns.push('actions');
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => {
      // If the user changes the sort order, reset back to the first page.
      this.paginator.pageIndex = 0;

      this.onSortAndPageUpdate.emit(this.getSortAndPage('sort'));
    });

    this.paginator.page.subscribe((_) => {
      this.onSortAndPageUpdate.emit(this.getSortAndPage('page'));
    });
  }

  getSortAndPage(action: 'sort' | 'page'): Search {
    return {
      action,
      sort: this.sort.active,
      direction: this.sort.direction,
      page: this.paginator.pageIndex,
      pageSize: this.paginator.pageSize,
    };
  }

  create() {
    this.onAdd.emit();
  }

  update(t: T) {
    this.onUpate.emit(t);
  }
}
