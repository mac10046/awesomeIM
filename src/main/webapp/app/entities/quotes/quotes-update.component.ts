import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IQuotes, Quotes } from 'app/shared/model/quotes.model';
import { QuotesService } from './quotes.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';

type SelectableEntity = ICustomers | IBusinessDetails;

@Component({
  selector: 'jhi-quotes-update',
  templateUrl: './quotes-update.component.html',
})
export class QuotesUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomers[] = [];
  businessdetails: IBusinessDetails[] = [];

  editForm = this.fb.group({
    id: [],
    gstin: [],
    quoteDate: [null, [Validators.required]],
    terms: [],
    amount: [],
    customers: [],
    businessDetails: [],
  });

  constructor(
    protected quotesService: QuotesService,
    protected customersService: CustomersService,
    protected businessDetailsService: BusinessDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quotes }) => {
      if (!quotes.id) {
        const today = moment().startOf('day');
        quotes.quoteDate = today;
      }

      this.updateForm(quotes);

      this.customersService
        .query({ filter: 'quotes-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomers[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomers[]) => {
          if (!quotes.customers || !quotes.customers.id) {
            this.customers = resBody;
          } else {
            this.customersService
              .find(quotes.customers.id)
              .pipe(
                map((subRes: HttpResponse<ICustomers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomers[]) => (this.customers = concatRes));
          }
        });

      this.businessDetailsService
        .query({ filter: 'quotes-is-null' })
        .pipe(
          map((res: HttpResponse<IBusinessDetails[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBusinessDetails[]) => {
          if (!quotes.businessDetails || !quotes.businessDetails.id) {
            this.businessdetails = resBody;
          } else {
            this.businessDetailsService
              .find(quotes.businessDetails.id)
              .pipe(
                map((subRes: HttpResponse<IBusinessDetails>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBusinessDetails[]) => (this.businessdetails = concatRes));
          }
        });
    });
  }

  updateForm(quotes: IQuotes): void {
    this.editForm.patchValue({
      id: quotes.id,
      gstin: quotes.gstin,
      quoteDate: quotes.quoteDate ? quotes.quoteDate.format(DATE_TIME_FORMAT) : null,
      terms: quotes.terms,
      amount: quotes.amount,
      customers: quotes.customers,
      businessDetails: quotes.businessDetails,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quotes = this.createFromForm();
    if (quotes.id !== undefined) {
      this.subscribeToSaveResponse(this.quotesService.update(quotes));
    } else {
      this.subscribeToSaveResponse(this.quotesService.create(quotes));
    }
  }

  private createFromForm(): IQuotes {
    return {
      ...new Quotes(),
      id: this.editForm.get(['id'])!.value,
      gstin: this.editForm.get(['gstin'])!.value,
      quoteDate: this.editForm.get(['quoteDate'])!.value ? moment(this.editForm.get(['quoteDate'])!.value, DATE_TIME_FORMAT) : undefined,
      terms: this.editForm.get(['terms'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      customers: this.editForm.get(['customers'])!.value,
      businessDetails: this.editForm.get(['businessDetails'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuotes>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
