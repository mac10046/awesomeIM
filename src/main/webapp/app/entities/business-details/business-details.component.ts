import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from './business-details.service';
import { BusinessDetailsDeleteDialogComponent } from './business-details-delete-dialog.component';

@Component({
  selector: 'jhi-business-details',
  templateUrl: './business-details.component.html',
})
export class BusinessDetailsComponent implements OnInit, OnDestroy {
  businessDetails?: IBusinessDetails[];
  eventSubscriber?: Subscription;

  constructor(
    protected businessDetailsService: BusinessDetailsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.businessDetailsService.query().subscribe((res: HttpResponse<IBusinessDetails[]>) => (this.businessDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBusinessDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBusinessDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInBusinessDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('businessDetailsListModification', () => this.loadAll());
  }

  delete(businessDetails: IBusinessDetails): void {
    const modalRef = this.modalService.open(BusinessDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.businessDetails = businessDetails;
  }
}
