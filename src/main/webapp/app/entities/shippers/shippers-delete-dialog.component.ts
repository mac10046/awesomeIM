import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShippers } from 'app/shared/model/shippers.model';
import { ShippersService } from './shippers.service';

@Component({
  templateUrl: './shippers-delete-dialog.component.html',
})
export class ShippersDeleteDialogComponent {
  shippers?: IShippers;

  constructor(protected shippersService: ShippersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shippersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shippersListModification');
      this.activeModal.close();
    });
  }
}
