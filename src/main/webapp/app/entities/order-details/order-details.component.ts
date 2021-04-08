import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderDetails } from 'app/shared/model/order-details.model';
import { OrderDetailsService } from './order-details.service';
import { OrderDetailsDeleteDialogComponent } from './order-details-delete-dialog.component';

@Component({
  selector: 'jhi-order-details',
  templateUrl: './order-details.component.html',
})
export class OrderDetailsComponent implements OnInit, OnDestroy {
  orderDetails?: IOrderDetails[];
  eventSubscriber?: Subscription;

  constructor(
    protected orderDetailsService: OrderDetailsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.orderDetailsService.query().subscribe((res: HttpResponse<IOrderDetails[]>) => (this.orderDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderDetailsListModification', () => this.loadAll());
  }

  delete(orderDetails: IOrderDetails): void {
    const modalRef = this.modalService.open(OrderDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderDetails = orderDetails;
  }
}
