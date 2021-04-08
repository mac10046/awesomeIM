import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessOpportunity } from 'app/shared/model/business-opportunity.model';
import { BusinessOpportunityService } from './business-opportunity.service';

@Component({
  templateUrl: './business-opportunity-delete-dialog.component.html',
})
export class BusinessOpportunityDeleteDialogComponent {
  businessOpportunity?: IBusinessOpportunity;

  constructor(
    protected businessOpportunityService: BusinessOpportunityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.businessOpportunityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('businessOpportunityListModification');
      this.activeModal.close();
    });
  }
}
