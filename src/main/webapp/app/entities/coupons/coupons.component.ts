import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICoupons } from 'app/shared/model/coupons.model';
import { CouponsService } from './coupons.service';
import { CouponsDeleteDialogComponent } from './coupons-delete-dialog.component';

@Component({
  selector: 'jhi-coupons',
  templateUrl: './coupons.component.html',
})
export class CouponsComponent implements OnInit, OnDestroy {
  coupons?: ICoupons[];
  eventSubscriber?: Subscription;

  constructor(protected couponsService: CouponsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.couponsService.query().subscribe((res: HttpResponse<ICoupons[]>) => (this.coupons = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCoupons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICoupons): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCoupons(): void {
    this.eventSubscriber = this.eventManager.subscribe('couponsListModification', () => this.loadAll());
  }

  delete(coupons: ICoupons): void {
    const modalRef = this.modalService.open(CouponsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.coupons = coupons;
  }
}
