import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPayments, Payments } from 'app/shared/model/payments.model';
import { PaymentsService } from './payments.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-payments-update',
  templateUrl: './payments-update.component.html',
})
export class PaymentsUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrders[] = [];
  paymentDateDp: any;

  editForm = this.fb.group({
    id: [],
    gatewayId: [null, [Validators.required]],
    paymentDate: [null, [Validators.required]],
    bankTxn: [],
    txnToken: [],
    responseTimestamp: [],
    checksum: [],
    txnAmount: [],
    bankName: [],
    responseCode: [],
    responseMessage: [],
    result: [],
    email: [],
    orders: [],
  });

  constructor(
    protected paymentsService: PaymentsService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payments }) => {
      if (!payments.id) {
        const today = moment().startOf('day');
        payments.responseTimestamp = today;
      }

      this.updateForm(payments);

      this.ordersService
        .query({ filter: 'payments-is-null' })
        .pipe(
          map((res: HttpResponse<IOrders[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOrders[]) => {
          if (!payments.orders || !payments.orders.id) {
            this.orders = resBody;
          } else {
            this.ordersService
              .find(payments.orders.id)
              .pipe(
                map((subRes: HttpResponse<IOrders>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOrders[]) => (this.orders = concatRes));
          }
        });
    });
  }

  updateForm(payments: IPayments): void {
    this.editForm.patchValue({
      id: payments.id,
      gatewayId: payments.gatewayId,
      paymentDate: payments.paymentDate,
      bankTxn: payments.bankTxn,
      txnToken: payments.txnToken,
      responseTimestamp: payments.responseTimestamp ? payments.responseTimestamp.format(DATE_TIME_FORMAT) : null,
      checksum: payments.checksum,
      txnAmount: payments.txnAmount,
      bankName: payments.bankName,
      responseCode: payments.responseCode,
      responseMessage: payments.responseMessage,
      result: payments.result,
      email: payments.email,
      orders: payments.orders,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payments = this.createFromForm();
    if (payments.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentsService.update(payments));
    } else {
      this.subscribeToSaveResponse(this.paymentsService.create(payments));
    }
  }

  private createFromForm(): IPayments {
    return {
      ...new Payments(),
      id: this.editForm.get(['id'])!.value,
      gatewayId: this.editForm.get(['gatewayId'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value,
      bankTxn: this.editForm.get(['bankTxn'])!.value,
      txnToken: this.editForm.get(['txnToken'])!.value,
      responseTimestamp: this.editForm.get(['responseTimestamp'])!.value
        ? moment(this.editForm.get(['responseTimestamp'])!.value, DATE_TIME_FORMAT)
        : undefined,
      checksum: this.editForm.get(['checksum'])!.value,
      txnAmount: this.editForm.get(['txnAmount'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      responseCode: this.editForm.get(['responseCode'])!.value,
      responseMessage: this.editForm.get(['responseMessage'])!.value,
      result: this.editForm.get(['result'])!.value,
      email: this.editForm.get(['email'])!.value,
      orders: this.editForm.get(['orders'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayments>>): void {
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

  trackById(index: number, item: IOrders): any {
    return item.id;
  }
}
