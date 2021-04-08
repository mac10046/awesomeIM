import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { OrderDetailsComponent } from './order-details.component';
import { OrderDetailsDetailComponent } from './order-details-detail.component';
import { OrderDetailsUpdateComponent } from './order-details-update.component';
import { OrderDetailsDeleteDialogComponent } from './order-details-delete-dialog.component';
import { orderDetailsRoute } from './order-details.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(orderDetailsRoute)],
  declarations: [OrderDetailsComponent, OrderDetailsDetailComponent, OrderDetailsUpdateComponent, OrderDetailsDeleteDialogComponent],
  entryComponents: [OrderDetailsDeleteDialogComponent],
})
export class AwesomeimOrderDetailsModule {}
