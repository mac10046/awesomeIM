import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessOpportunity } from 'app/shared/model/business-opportunity.model';
import { BusinessOpportunityService } from './business-opportunity.service';
import { BusinessOpportunityDeleteDialogComponent } from './business-opportunity-delete-dialog.component';

@Component({
  selector: 'jhi-business-opportunity',
  templateUrl: './business-opportunity.component.html',
})
export class BusinessOpportunityComponent implements OnInit, OnDestroy {
  businessOpportunities?: IBusinessOpportunity[];
  eventSubscriber?: Subscription;

  constructor(
    protected businessOpportunityService: BusinessOpportunityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.businessOpportunityService
      .query()
      .subscribe((res: HttpResponse<IBusinessOpportunity[]>) => (this.businessOpportunities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBusinessOpportunities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBusinessOpportunity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBusinessOpportunities(): void {
    this.eventSubscriber = this.eventManager.subscribe('businessOpportunityListModification', () => this.loadAll());
  }

  delete(businessOpportunity: IBusinessOpportunity): void {
    const modalRef = this.modalService.open(BusinessOpportunityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.businessOpportunity = businessOpportunity;
  }
}
