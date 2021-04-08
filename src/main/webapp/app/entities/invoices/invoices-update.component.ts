import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvoices, Invoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from './invoices.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';

type SelectableEntity = ICustomers | IBusinessDetails;

@Component({
  selector: 'jhi-invoices-update',
  templateUrl: './invoices-update.component.html',
})
export class InvoicesUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomers[] = [];
  businessdetails: IBusinessDetails[] = [];

  editForm = this.fb.group({
    id: [],
    gstin: [],
    invoiceDate: [null, [Validators.required]],
    terms: [],
    isPaid: [],
    amount: [],
    customers: [],
    businessDetails: [],
  });

  constructor(
    protected invoicesService: InvoicesService,
    protected customersService: CustomersService,
    protected businessDetailsService: BusinessDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoices }) => {
      if (!invoices.id) {
        const today = moment().startOf('day');
        invoices.invoiceDate = today;
      }

      this.updateForm(invoices);

      this.customersService
        .query({ filter: 'invoices-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomers[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomers[]) => {
          if (!invoices.customers || !invoices.customers.id) {
            this.customers = resBody;
          } else {
            this.customersService
              .find(invoices.customers.id)
              .pipe(
                map((subRes: HttpResponse<ICustomers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomers[]) => (this.customers = concatRes));
          }
        });

      this.businessDetailsService
        .query({ filter: 'invoices-is-null' })
        .pipe(
          map((res: HttpResponse<IBusinessDetails[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBusinessDetails[]) => {
          if (!invoices.businessDetails || !invoices.businessDetails.id) {
            this.businessdetails = resBody;
          } else {
            this.businessDetailsService
              .find(invoices.businessDetails.id)
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

  updateForm(invoices: IInvoices): void {
    this.editForm.patchValue({
      id: invoices.id,
      gstin: invoices.gstin,
      invoiceDate: invoices.invoiceDate ? invoices.invoiceDate.format(DATE_TIME_FORMAT) : null,
      terms: invoices.terms,
      isPaid: invoices.isPaid,
      amount: invoices.amount,
      customers: invoices.customers,
      businessDetails: invoices.businessDetails,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoices = this.createFromForm();
    if (invoices.id !== undefined) {
      this.subscribeToSaveResponse(this.invoicesService.update(invoices));
    } else {
      this.subscribeToSaveResponse(this.invoicesService.create(invoices));
    }
  }

  private createFromForm(): IInvoices {
    return {
      ...new Invoices(),
      id: this.editForm.get(['id'])!.value,
      gstin: this.editForm.get(['gstin'])!.value,
      invoiceDate: this.editForm.get(['invoiceDate'])!.value
        ? moment(this.editForm.get(['invoiceDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      terms: this.editForm.get(['terms'])!.value,
      isPaid: this.editForm.get(['isPaid'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      customers: this.editForm.get(['customers'])!.value,
      businessDetails: this.editForm.get(['businessDetails'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoices>>): void {
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
