import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { QuotesComponent } from './quotes.component';
import { QuotesDetailComponent } from './quotes-detail.component';
import { QuotesUpdateComponent } from './quotes-update.component';
import { QuotesDeleteDialogComponent } from './quotes-delete-dialog.component';
import { quotesRoute } from './quotes.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(quotesRoute)],
  declarations: [QuotesComponent, QuotesDetailComponent, QuotesUpdateComponent, QuotesDeleteDialogComponent],
  entryComponents: [QuotesDeleteDialogComponent],
})
export class AwesomeimQuotesModule {}
