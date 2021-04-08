import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IShipping, Shipping } from 'app/shared/model/shipping.model';
import { ShippingService } from './shipping.service';
import { IShippers } from 'app/shared/model/shippers.model';
import { ShippersService } from 'app/entities/shippers/shippers.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

type SelectableEntity = IShippers | IAddress | IOrders;

@Component({
  selector: 'jhi-shipping-update',
  templateUrl: './shipping-update.component.html',
})
export class ShippingUpdateComponent implements OnInit {
  isSaving = false;
  shippers: IShippers[] = [];
  addresses: IAddress[] = [];
  orders: IOrders[] = [];
  shipDateDp: any;

  editForm = this.fb.group({
    id: [],
    shipDate: [null, [Validators.required]],
    trackingId: [null, [Validators.required]],
    shippers: [],
    address: [],
    orders: [],
  });

  constructor(
    protected shippingService: ShippingService,
    protected shippersService: ShippersService,
    protected addressService: AddressService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipping }) => {
      this.updateForm(shipping);

      this.shippersService
        .query({ filter: 'shipping-is-null' })
        .pipe(
          map((res: HttpResponse<IShippers[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IShippers[]) => {
          if (!shipping.shippers || !shipping.shippers.id) {
            this.shippers = resBody;
          } else {
            this.shippersService
              .find(shipping.shippers.id)
              .pipe(
                map((subRes: HttpResponse<IShippers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IShippers[]) => (this.shippers = concatRes));
          }
        });

      this.addressService
        .query({ filter: 'shipping-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!shipping.address || !shipping.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(shipping.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });

      this.ordersService.query().subscribe((res: HttpResponse<IOrders[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(shipping: IShipping): void {
    this.editForm.patchValue({
      id: shipping.id,
      shipDate: shipping.shipDate,
      trackingId: shipping.trackingId,
      shippers: shipping.shippers,
      address: shipping.address,
      orders: shipping.orders,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipping = this.createFromForm();
    if (shipping.id !== undefined) {
      this.subscribeToSaveResponse(this.shippingService.update(shipping));
    } else {
      this.subscribeToSaveResponse(this.shippingService.create(shipping));
    }
  }

  private createFromForm(): IShipping {
    return {
      ...new Shipping(),
      id: this.editForm.get(['id'])!.value,
      shipDate: this.editForm.get(['shipDate'])!.value,
      trackingId: this.editForm.get(['trackingId'])!.value,
      shippers: this.editForm.get(['shippers'])!.value,
      address: this.editForm.get(['address'])!.value,
      orders: this.editForm.get(['orders'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipping>>): void {
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
