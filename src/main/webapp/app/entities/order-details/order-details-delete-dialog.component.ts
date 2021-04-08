import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderDetails } from 'app/shared/model/order-details.model';
import { OrderDetailsService } from './order-details.service';

@Component({
  templateUrl: './order-details-delete-dialog.component.html',
})
export class OrderDetailsDeleteDialogComponent {
  orderDetails?: IOrderDetails;

  constructor(
    protected orderDetailsService: OrderDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderDetailsListModification');
      this.activeModal.close();
    });
  }
}
