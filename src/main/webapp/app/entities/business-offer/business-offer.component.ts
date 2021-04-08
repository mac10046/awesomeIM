import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessOffer } from 'app/shared/model/business-offer.model';
import { BusinessOfferService } from './business-offer.service';
import { BusinessOfferDeleteDialogComponent } from './business-offer-delete-dialog.component';

@Component({
  selector: 'jhi-business-offer',
  templateUrl: './business-offer.component.html',
})
export class BusinessOfferComponent implements OnInit, OnDestroy {
  businessOffers?: IBusinessOffer[];
  eventSubscriber?: Subscription;

  constructor(
    protected businessOfferService: BusinessOfferService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.businessOfferService.query().subscribe((res: HttpResponse<IBusinessOffer[]>) => (this.businessOffers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBusinessOffers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBusinessOffer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBusinessOffers(): void {
    this.eventSubscriber = this.eventManager.subscribe('businessOfferListModification', () => this.loadAll());
  }

  delete(businessOffer: IBusinessOffer): void {
    const modalRef = this.modalService.open(BusinessOfferDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.businessOffer = businessOffer;
  }
}
