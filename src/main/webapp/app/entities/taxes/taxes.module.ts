import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { TaxesComponent } from './taxes.component';
import { TaxesDetailComponent } from './taxes-detail.component';
import { TaxesUpdateComponent } from './taxes-update.component';
import { TaxesDeleteDialogComponent } from './taxes-delete-dialog.component';
import { taxesRoute } from './taxes.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(taxesRoute)],
  declarations: [TaxesComponent, TaxesDetailComponent, TaxesUpdateComponent, TaxesDeleteDialogComponent],
  entryComponents: [TaxesDeleteDialogComponent],
})
export class AwesomeimTaxesModule {}
