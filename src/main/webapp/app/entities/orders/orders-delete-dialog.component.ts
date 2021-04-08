import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';

@Component({
  templateUrl: './orders-delete-dialog.component.html',
})
export class OrdersDeleteDialogComponent {
  orders?: IOrders;

  constructor(protected ordersService: OrdersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ordersListModification');
      this.activeModal.close();
    });
  }
}
