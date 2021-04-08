import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';

@Component({
  templateUrl: './reviews-delete-dialog.component.html',
})
export class ReviewsDeleteDialogComponent {
  reviews?: IReviews;

  constructor(protected reviewsService: ReviewsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reviewsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reviewsListModification');
      this.activeModal.close();
    });
  }
}
