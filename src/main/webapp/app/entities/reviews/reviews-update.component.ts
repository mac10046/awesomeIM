import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReviews, Reviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';

@Component({
  selector: 'jhi-reviews-update',
  templateUrl: './reviews-update.component.html',
})
export class ReviewsUpdateComponent implements OnInit {
  isSaving = false;
  businessdetails: IBusinessDetails[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    message: [null, [Validators.required]],
    reviewDate: [null, [Validators.required]],
    rating: [null, [Validators.required]],
    designation: [],
    businessDetails: [],
  });

  constructor(
    protected reviewsService: ReviewsService,
    protected businessDetailsService: BusinessDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reviews }) => {
      if (!reviews.id) {
        const today = moment().startOf('day');
        reviews.reviewDate = today;
      }

      this.updateForm(reviews);

      this.businessDetailsService.query().subscribe((res: HttpResponse<IBusinessDetails[]>) => (this.businessdetails = res.body || []));
    });
  }

  updateForm(reviews: IReviews): void {
    this.editForm.patchValue({
      id: reviews.id,
      name: reviews.name,
      message: reviews.message,
      reviewDate: reviews.reviewDate ? reviews.reviewDate.format(DATE_TIME_FORMAT) : null,
      rating: reviews.rating,
      designation: reviews.designation,
      businessDetails: reviews.businessDetails,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reviews = this.createFromForm();
    if (reviews.id !== undefined) {
      this.subscribeToSaveResponse(this.reviewsService.update(reviews));
    } else {
      this.subscribeToSaveResponse(this.reviewsService.create(reviews));
    }
  }

  private createFromForm(): IReviews {
    return {
      ...new Reviews(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      message: this.editForm.get(['message'])!.value,
      reviewDate: this.editForm.get(['reviewDate'])!.value ? moment(this.editForm.get(['reviewDate'])!.value, DATE_TIME_FORMAT) : undefined,
      rating: this.editForm.get(['rating'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      businessDetails: this.editForm.get(['businessDetails'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReviews>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBusinessDetails): any {
    return item.id;
  }
}
