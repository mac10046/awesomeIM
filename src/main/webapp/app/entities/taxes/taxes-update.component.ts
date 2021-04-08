import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITaxes, Taxes } from 'app/shared/model/taxes.model';
import { TaxesService } from './taxes.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';

@Component({
  selector: 'jhi-taxes-update',
  templateUrl: './taxes-update.component.html',
})
export class TaxesUpdateComponent implements OnInit {
  isSaving = false;
  products: IProducts[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    isPercent: [null, [Validators.required]],
    figure: [null, [Validators.required]],
    description: [],
    products: [],
  });

  constructor(
    protected taxesService: TaxesService,
    protected productsService: ProductsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxes }) => {
      this.updateForm(taxes);

      this.productsService
        .query({ filter: 'taxes-is-null' })
        .pipe(
          map((res: HttpResponse<IProducts[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProducts[]) => {
          if (!taxes.products || !taxes.products.id) {
            this.products = resBody;
          } else {
            this.productsService
              .find(taxes.products.id)
              .pipe(
                map((subRes: HttpResponse<IProducts>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProducts[]) => (this.products = concatRes));
          }
        });
    });
  }

  updateForm(taxes: ITaxes): void {
    this.editForm.patchValue({
      id: taxes.id,
      name: taxes.name,
      isPercent: taxes.isPercent,
      figure: taxes.figure,
      description: taxes.description,
      products: taxes.products,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxes = this.createFromForm();
    if (taxes.id !== undefined) {
      this.subscribeToSaveResponse(this.taxesService.update(taxes));
    } else {
      this.subscribeToSaveResponse(this.taxesService.create(taxes));
    }
  }

  private createFromForm(): ITaxes {
    return {
      ...new Taxes(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      isPercent: this.editForm.get(['isPercent'])!.value,
      figure: this.editForm.get(['figure'])!.value,
      description: this.editForm.get(['description'])!.value,
      products: this.editForm.get(['products'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxes>>): void {
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

  trackById(index: number, item: IProducts): any {
    return item.id;
  }
}
