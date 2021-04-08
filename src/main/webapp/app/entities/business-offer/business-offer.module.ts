import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { BusinessOfferComponent } from './business-offer.component';
import { BusinessOfferDetailComponent } from './business-offer-detail.component';
import { BusinessOfferUpdateComponent } from './business-offer-update.component';
import { BusinessOfferDeleteDialogComponent } from './business-offer-delete-dialog.component';
import { businessOfferRoute } from './business-offer.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(businessOfferRoute)],
  declarations: [BusinessOfferComponent, BusinessOfferDetailComponent, BusinessOfferUpdateComponent, BusinessOfferDeleteDialogComponent],
  entryComponents: [BusinessOfferDeleteDialogComponent],
})
export class AwesomeimBusinessOfferModule {}
