import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UomsComponent } from './containers/uoms/uoms.component';

const routes: Routes = [{ path: '', component: UomsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UomRoutingModule {}
