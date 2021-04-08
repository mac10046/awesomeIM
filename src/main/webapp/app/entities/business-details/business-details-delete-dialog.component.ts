import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from './business-details.service';

@Component({
  templateUrl: './business-details-delete-dialog.component.html',
})
export class BusinessDetailsDeleteDialogComponent {
  businessDetails?: IBusinessDetails;

  constructor(
    protected businessDetailsService: BusinessDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.businessDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('businessDetailsListModification');
      this.activeModal.close();
    });
  }
}
