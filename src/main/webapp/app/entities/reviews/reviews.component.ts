import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { ReviewsDeleteDialogComponent } from './reviews-delete-dialog.component';

@Component({
  selector: 'jhi-reviews',
  templateUrl: './reviews.component.html',
})
export class ReviewsComponent implements OnInit, OnDestroy {
  reviews?: IReviews[];
  eventSubscriber?: Subscription;

  constructor(protected reviewsService: ReviewsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.reviewsService.query().subscribe((res: HttpResponse<IReviews[]>) => (this.reviews = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReviews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReviews): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReviews(): void {
    this.eventSubscriber = this.eventManager.subscribe('reviewsListModification', () => this.loadAll());
  }

  delete(reviews: IReviews): void {
    const modalRef = this.modalService.open(ReviewsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reviews = reviews;
  }
}
