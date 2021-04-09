import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBusinessOpportunity, BusinessOpportunity } from 'app/shared/model/business-opportunity.model';
import { BusinessOpportunityService } from './business-opportunity.service';

@Component({
  selector: 'jhi-business-opportunity-update',
  templateUrl: './business-opportunity-update.component.html',
})
export class BusinessOpportunityUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    investmentAmount: [null, [Validators.required]],
    startDate: [],
    endDate: [],
    planDescription: [null, [Validators.required]],
  });

  constructor(
    protected businessOpportunityService: BusinessOpportunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessOpportunity }) => {
      this.updateForm(businessOpportunity);
    });
  }

  updateForm(businessOpportunity: IBusinessOpportunity): void {
    this.editForm.patchValue({
      id: businessOpportunity.id,
      title: businessOpportunity.title,
      investmentAmount: businessOpportunity.investmentAmount,
      startDate: businessOpportunity.startDate,
      endDate: businessOpportunity.endDate,
      planDescription: businessOpportunity.planDescription,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const businessOpportunity = this.createFromForm();
    if (businessOpportunity.id !== undefined) {
      this.subscribeToSaveResponse(this.businessOpportunityService.update(businessOpportunity));
    } else {
      this.subscribeToSaveResponse(this.businessOpportunityService.create(businessOpportunity));
    }
  }

  private createFromForm(): IBusinessOpportunity {
    return {
      ...new BusinessOpportunity(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      investmentAmount: this.editForm.get(['investmentAmount'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      planDescription: this.editForm.get(['planDescription'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessOpportunity>>): void {
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
}
