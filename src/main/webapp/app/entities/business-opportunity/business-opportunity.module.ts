import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { BusinessOpportunityComponent } from './business-opportunity.component';
import { BusinessOpportunityDetailComponent } from './business-opportunity-detail.component';
import { BusinessOpportunityUpdateComponent } from './business-opportunity-update.component';
import { BusinessOpportunityDeleteDialogComponent } from './business-opportunity-delete-dialog.component';
import { businessOpportunityRoute } from './business-opportunity.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(businessOpportunityRoute)],
  declarations: [
    BusinessOpportunityComponent,
    BusinessOpportunityDetailComponent,
    BusinessOpportunityUpdateComponent,
    BusinessOpportunityDeleteDialogComponent,
  ],
  entryComponents: [BusinessOpportunityDeleteDialogComponent],
})
export class AwesomeimBusinessOpportunityModule {}
