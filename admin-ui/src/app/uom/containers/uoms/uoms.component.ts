import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatBottomSheet } from '@angular/material/bottom-sheet';

import { Observable } from 'rxjs';
import { UomUpdateBottomsheetComponent } from '../../components/uom-update-bottomsheet/uom-update-bottomsheet.component';

import { UnitOfMeasure } from '../../models/uom';
import { UomService } from '../../services/uom.service';

@Component({
  selector: 'app-uoms',
  templateUrl: './uoms.component.html',
  styleUrls: ['./uoms.component.scss'],
})
export class UomsComponent implements OnInit {
  uoms$: Observable<UnitOfMeasure[]>;

  uomFormControl = new FormControl('');

  constructor(
    private uomService: UomService,
    private _bottomSheet: MatBottomSheet
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.uoms$ = this.uomService.load();
  }

  onSearch() {
    if (this.uomFormControl.valid && this.uomFormControl.value)
      this.uoms$ = this.uomService.loadWithQuery({
        keyword: this.uomFormControl.value,
      });
    else this.load();
  }

  onAdd() {
    let ref = this._bottomSheet.open(UomUpdateBottomsheetComponent);

    ref.afterDismissed().subscribe((data) => {
      if (data) this.load();
    });
  }

  onUpdate(uom: UnitOfMeasure) {
    let ref = this._bottomSheet.open(UomUpdateBottomsheetComponent, {
      data: uom,
    });

    ref.afterDismissed().subscribe((data) => {
      if (data) this.load();
    });
  }
}
