import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { BusinessDetailsComponent } from './business-details.component';
import { BusinessDetailsDetailComponent } from './business-details-detail.component';
import { BusinessDetailsUpdateComponent } from './business-details-update.component';
import { BusinessDetailsDeleteDialogComponent } from './business-details-delete-dialog.component';
import { businessDetailsRoute } from './business-details.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(businessDetailsRoute)],
  declarations: [
    BusinessDetailsComponent,
    BusinessDetailsDetailComponent,
    BusinessDetailsUpdateComponent,
    BusinessDetailsDeleteDialogComponent,
  ],
  entryComponents: [BusinessDetailsDeleteDialogComponent],
})
export class AwesomeimBusinessDetailsModule {}
