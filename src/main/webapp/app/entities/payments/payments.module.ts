import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { PaymentsComponent } from './payments.component';
import { PaymentsDetailComponent } from './payments-detail.component';
import { PaymentsUpdateComponent } from './payments-update.component';
import { PaymentsDeleteDialogComponent } from './payments-delete-dialog.component';
import { paymentsRoute } from './payments.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(paymentsRoute)],
  declarations: [PaymentsComponent, PaymentsDetailComponent, PaymentsUpdateComponent, PaymentsDeleteDialogComponent],
  entryComponents: [PaymentsDeleteDialogComponent],
})
export class AwesomeimPaymentsModule {}
