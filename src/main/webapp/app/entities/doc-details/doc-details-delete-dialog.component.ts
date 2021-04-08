import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocDetails } from 'app/shared/model/doc-details.model';
import { DocDetailsService } from './doc-details.service';

@Component({
  templateUrl: './doc-details-delete-dialog.component.html',
})
export class DocDetailsDeleteDialogComponent {
  docDetails?: IDocDetails;

  constructor(
    protected docDetailsService: DocDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.docDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('docDetailsListModification');
      this.activeModal.close();
    });
  }
}
