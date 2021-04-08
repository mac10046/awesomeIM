import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuotes } from 'app/shared/model/quotes.model';
import { QuotesService } from './quotes.service';

@Component({
  templateUrl: './quotes-delete-dialog.component.html',
})
export class QuotesDeleteDialogComponent {
  quotes?: IQuotes;

  constructor(protected quotesService: QuotesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quotesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('quotesListModification');
      this.activeModal.close();
    });
  }
}
