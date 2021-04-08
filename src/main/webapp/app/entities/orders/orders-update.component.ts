import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrders, Orders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';

@Component({
  selector: 'jhi-orders-update',
  templateUrl: './orders-update.component.html',
})
export class OrdersUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomers[] = [];

  editForm = this.fb.group({
    id: [],
    orderDate: [null, [Validators.required]],
    isPaid: [],
    amount: [],
    customers: [],
  });

  constructor(
    protected ordersService: OrdersService,
    protected customersService: CustomersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orders }) => {
      if (!orders.id) {
        const today = moment().startOf('day');
        orders.orderDate = today;
      }

      this.updateForm(orders);

      this.customersService
        .query({ filter: 'orders-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomers[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomers[]) => {
          if (!orders.customers || !orders.customers.id) {
            this.customers = resBody;
          } else {
            this.customersService
              .find(orders.customers.id)
              .pipe(
                map((subRes: HttpResponse<ICustomers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomers[]) => (this.customers = concatRes));
          }
        });
    });
  }

  updateForm(orders: IOrders): void {
    this.editForm.patchValue({
      id: orders.id,
      orderDate: orders.orderDate ? orders.orderDate.format(DATE_TIME_FORMAT) : null,
      isPaid: orders.isPaid,
      amount: orders.amount,
      customers: orders.customers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orders = this.createFromForm();
    if (orders.id !== undefined) {
      this.subscribeToSaveResponse(this.ordersService.update(orders));
    } else {
      this.subscribeToSaveResponse(this.ordersService.create(orders));
    }
  }

  private createFromForm(): IOrders {
    return {
      ...new Orders(),
      id: this.editForm.get(['id'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value ? moment(this.editForm.get(['orderDate'])!.value, DATE_TIME_FORMAT) : undefined,
      isPaid: this.editForm.get(['isPaid'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      customers: this.editForm.get(['customers'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>): void {
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

  trackById(index: number, item: ICustomers): any {
    return item.id;
  }
}
