import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocDetails } from 'app/shared/model/doc-details.model';
import { DocDetailsService } from './doc-details.service';
import { DocDetailsDeleteDialogComponent } from './doc-details-delete-dialog.component';

@Component({
  selector: 'jhi-doc-details',
  templateUrl: './doc-details.component.html',
})
export class DocDetailsComponent implements OnInit, OnDestroy {
  docDetails?: IDocDetails[];
  eventSubscriber?: Subscription;

  constructor(protected docDetailsService: DocDetailsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.docDetailsService.query().subscribe((res: HttpResponse<IDocDetails[]>) => (this.docDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDocDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDocDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDocDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('docDetailsListModification', () => this.loadAll());
  }

  delete(docDetails: IDocDetails): void {
    const modalRef = this.modalService.open(DocDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.docDetails = docDetails;
  }
}
