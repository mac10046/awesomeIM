import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoupons } from 'app/shared/model/coupons.model';
import { CouponsService } from './coupons.service';

@Component({
  templateUrl: './coupons-delete-dialog.component.html',
})
export class CouponsDeleteDialogComponent {
  coupons?: ICoupons;

  constructor(protected couponsService: CouponsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.couponsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('couponsListModification');
      this.activeModal.close();
    });
  }
}
