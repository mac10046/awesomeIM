import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBusinessOffer, BusinessOffer } from 'app/shared/model/business-offer.model';
import { BusinessOfferService } from './business-offer.service';
import { IBusinessOpportunity } from 'app/shared/model/business-opportunity.model';
import { BusinessOpportunityService } from 'app/entities/business-opportunity/business-opportunity.service';

@Component({
  selector: 'jhi-business-offer-update',
  templateUrl: './business-offer-update.component.html',
})
export class BusinessOfferUpdateComponent implements OnInit {
  isSaving = false;
  businessopportunities: IBusinessOpportunity[] = [];

  editForm = this.fb.group({
    id: [],
    time: [],
    message: [null, [Validators.required]],
    name: [null, [Validators.required]],
    email: [null, [Validators.required]],
    businessOpportunity: [],
  });

  constructor(
    protected businessOfferService: BusinessOfferService,
    protected businessOpportunityService: BusinessOpportunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessOffer }) => {
      if (!businessOffer.id) {
        const today = moment().startOf('day');
        businessOffer.time = today;
      }

      this.updateForm(businessOffer);

      this.businessOpportunityService.query().subscribe((res: HttpResponse<IBusinessOpportunity[]>) => {
        this.businessopportunities = res.body || [];
        if (businessOffer.id) {
          this.businessopportunities = this.businessopportunities.find(offer => offer.id === businessOffer.id);
        }
      });
    });
  }

  updateForm(businessOffer: IBusinessOffer): void {
    this.editForm.patchValue({
      id: businessOffer.id,
      time: businessOffer.time ? businessOffer.time.format(DATE_TIME_FORMAT) : null,
      message: businessOffer.message,
      name: businessOffer.name,
      email: businessOffer.email,
      businessOpportunity: businessOffer.businessOpportunity,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const businessOffer = this.createFromForm();
    if (businessOffer.id !== undefined) {
      this.subscribeToSaveResponse(this.businessOfferService.update(businessOffer));
    } else {
      this.subscribeToSaveResponse(this.businessOfferService.create(businessOffer));
    }
  }

  private createFromForm(): IBusinessOffer {
    return {
      ...new BusinessOffer(),
      id: this.editForm.get(['id'])!.value,
      time: this.editForm.get(['time'])!.value ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      message: this.editForm.get(['message'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      businessOpportunity: this.editForm.get(['businessOpportunity'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessOffer>>): void {
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

  trackById(index: number, item: IBusinessOpportunity): any {
    return item.id;
  }
}
