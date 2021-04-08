import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxes } from 'app/shared/model/taxes.model';
import { TaxesService } from './taxes.service';

@Component({
  templateUrl: './taxes-delete-dialog.component.html',
})
export class TaxesDeleteDialogComponent {
  taxes?: ITaxes;

  constructor(protected taxesService: TaxesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taxesListModification');
      this.activeModal.close();
    });
  }
}
