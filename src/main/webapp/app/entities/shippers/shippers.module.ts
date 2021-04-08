import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { ShippersComponent } from './shippers.component';
import { ShippersDetailComponent } from './shippers-detail.component';
import { ShippersUpdateComponent } from './shippers-update.component';
import { ShippersDeleteDialogComponent } from './shippers-delete-dialog.component';
import { shippersRoute } from './shippers.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(shippersRoute)],
  declarations: [ShippersComponent, ShippersDetailComponent, ShippersUpdateComponent, ShippersDeleteDialogComponent],
  entryComponents: [ShippersDeleteDialogComponent],
})
export class AwesomeimShippersModule {}
