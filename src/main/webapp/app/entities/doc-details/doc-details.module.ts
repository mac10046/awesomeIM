import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AwesomeimSharedModule } from 'app/shared/shared.module';
import { DocDetailsComponent } from './doc-details.component';
import { DocDetailsDetailComponent } from './doc-details-detail.component';
import { DocDetailsUpdateComponent } from './doc-details-update.component';
import { DocDetailsDeleteDialogComponent } from './doc-details-delete-dialog.component';
import { docDetailsRoute } from './doc-details.route';

@NgModule({
  imports: [AwesomeimSharedModule, RouterModule.forChild(docDetailsRoute)],
  declarations: [DocDetailsComponent, DocDetailsDetailComponent, DocDetailsUpdateComponent, DocDetailsDeleteDialogComponent],
  entryComponents: [DocDetailsDeleteDialogComponent],
})
export class AwesomeimDocDetailsModule {}
