import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { CouponsComponent } from './coupons.component';
import { CouponsDetailComponent } from './coupons-detail.component';
import { CouponsUpdateComponent } from './coupons-update.component';
import { CouponsDeleteDialogComponent } from './coupons-delete-dialog.component';
import { couponsRoute } from './coupons.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(couponsRoute)],
  declarations: [CouponsComponent, CouponsDetailComponent, CouponsUpdateComponent, CouponsDeleteDialogComponent],
  entryComponents: [CouponsDeleteDialogComponent],
})
export class AwesomeimCouponsModule {}
