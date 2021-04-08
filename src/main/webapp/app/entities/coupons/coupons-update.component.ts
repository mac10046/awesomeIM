import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICoupons, Coupons } from 'app/shared/model/coupons.model';
import { CouponsService } from './coupons.service';

@Component({
  selector: 'jhi-coupons-update',
  templateUrl: './coupons-update.component.html',
})
export class CouponsUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
  });

  constructor(protected couponsService: CouponsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ coupons }) => {
      this.updateForm(coupons);
    });
  }

  updateForm(coupons: ICoupons): void {
    this.editForm.patchValue({
      id: coupons.id,
      name: coupons.name,
      code: coupons.code,
      startDate: coupons.startDate,
      endDate: coupons.endDate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const coupons = this.createFromForm();
    if (coupons.id !== undefined) {
      this.subscribeToSaveResponse(this.couponsService.update(coupons));
    } else {
      this.subscribeToSaveResponse(this.couponsService.create(coupons));
    }
  }

  private createFromForm(): ICoupons {
    return {
      ...new Coupons(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoupons>>): void {
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
