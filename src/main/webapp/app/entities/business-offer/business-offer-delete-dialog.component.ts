import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessOffer } from 'app/shared/model/business-offer.model';
import { BusinessOfferService } from './business-offer.service';

@Component({
  templateUrl: './business-offer-delete-dialog.component.html',
})
export class BusinessOfferDeleteDialogComponent {
  businessOffer?: IBusinessOffer;

  constructor(
    protected businessOfferService: BusinessOfferService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.businessOfferService.delete(id).subscribe(() => {
      this.eventManager.broadcast('businessOfferListModification');
      this.activeModal.close();
    });
  }
}
