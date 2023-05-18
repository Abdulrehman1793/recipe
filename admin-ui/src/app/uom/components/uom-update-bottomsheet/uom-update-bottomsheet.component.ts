import { Component, OnInit, Inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  MatBottomSheetRef,
  MAT_BOTTOM_SHEET_DATA,
} from '@angular/material/bottom-sheet';
import { UnitOfMeasure } from '../../models/uom';
import { UomService } from '../../services/uom.service';

@Component({
  selector: 'app-uom-update-bottomsheet',
  templateUrl: './uom-update-bottomsheet.component.html',
  styleUrls: ['./uom-update-bottomsheet.component.scss'],
})
export class UomUpdateBottomsheetComponent implements OnInit {
  uomControl = new FormControl('');

  constructor(
    private _bottomSheetRef: MatBottomSheetRef<UomUpdateBottomsheetComponent>,
    @Inject(MAT_BOTTOM_SHEET_DATA) public data: UnitOfMeasure,
    private serviceUom: UomService
  ) {}

  ngOnInit(): void {
    if (this.data) this.uomControl.setValue(this.data.uom);
  }

  saveOrUpdate() {
    if (this.uomControl.valid && this.uomControl.value) {
      let uom$;
      if (this.data) {
        uom$ = this.serviceUom.update({
          uom: this.uomControl.value,
          id: this.data.id,
        });
      } else {
        uom$ = this.serviceUom.add({ uom: this.uomControl.value });
      }

      uom$.subscribe((response) => {
        this._bottomSheetRef.dismiss(response);
      });
    }
  }
}
