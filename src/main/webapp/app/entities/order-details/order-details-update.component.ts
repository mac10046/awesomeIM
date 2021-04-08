import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderDetails, OrderDetails } from 'app/shared/model/order-details.model';
import { OrderDetailsService } from './order-details.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-order-details-update',
  templateUrl: './order-details-update.component.html',
})
export class OrderDetailsUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrders[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    qty: [null, [Validators.required]],
    price: [null, [Validators.required]],
    discount: [],
    taxes: [],
    isTaxPercent: [],
    orders: [],
  });

  constructor(
    protected orderDetailsService: OrderDetailsService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderDetails }) => {
      this.updateForm(orderDetails);

      this.ordersService.query().subscribe((res: HttpResponse<IOrders[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(orderDetails: IOrderDetails): void {
    this.editForm.patchValue({
      id: orderDetails.id,
      description: orderDetails.description,
      qty: orderDetails.qty,
      price: orderDetails.price,
      discount: orderDetails.discount,
      taxes: orderDetails.taxes,
      isTaxPercent: orderDetails.isTaxPercent,
      orders: orderDetails.orders,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderDetails = this.createFromForm();
    if (orderDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.orderDetailsService.update(orderDetails));
    } else {
      this.subscribeToSaveResponse(this.orderDetailsService.create(orderDetails));
    }
  }

  private createFromForm(): IOrderDetails {
    return {
      ...new OrderDetails(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      qty: this.editForm.get(['qty'])!.value,
      price: this.editForm.get(['price'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      taxes: this.editForm.get(['taxes'])!.value,
      isTaxPercent: this.editForm.get(['isTaxPercent'])!.value,
      orders: this.editForm.get(['orders'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderDetails>>): void {
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
