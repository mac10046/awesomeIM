import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayments } from 'app/shared/model/payments.model';
import { PaymentsService } from './payments.service';

@Component({
  templateUrl: './payments-delete-dialog.component.html',
})
export class PaymentsDeleteDialogComponent {
  payments?: IPayments;

  constructor(protected paymentsService: PaymentsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentsListModification');
      this.activeModal.close();
    });
  }
}
