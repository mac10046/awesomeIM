import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaxes } from 'app/shared/model/taxes.model';
import { TaxesService } from './taxes.service';
import { TaxesDeleteDialogComponent } from './taxes-delete-dialog.component';

@Component({
  selector: 'jhi-taxes',
  templateUrl: './taxes.component.html',
})
export class TaxesComponent implements OnInit, OnDestroy {
  taxes?: ITaxes[];
  eventSubscriber?: Subscription;

  constructor(protected taxesService: TaxesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.taxesService.query().subscribe((res: HttpResponse<ITaxes[]>) => (this.taxes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaxes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaxes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaxes(): void {
    this.eventSubscriber = this.eventManager.subscribe('taxesListModification', () => this.loadAll());
  }

  delete(taxes: ITaxes): void {
    const modalRef = this.modalService.open(TaxesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxes = taxes;
  }
}
