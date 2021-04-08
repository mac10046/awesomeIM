import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICart, Cart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { ICoupons } from 'app/shared/model/coupons.model';
import { CouponsService } from 'app/entities/coupons/coupons.service';

@Component({
  selector: 'jhi-cart-update',
  templateUrl: './cart-update.component.html',
})
export class CartUpdateComponent implements OnInit {
  isSaving = false;
  coupons: ICoupons[] = [];

  editForm = this.fb.group({
    id: [],
    coupons: [],
  });

  constructor(
    protected cartService: CartService,
    protected couponsService: CouponsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cart }) => {
      this.updateForm(cart);

      this.couponsService
        .query({ filter: 'cart-is-null' })
        .pipe(
          map((res: HttpResponse<ICoupons[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICoupons[]) => {
          if (!cart.coupons || !cart.coupons.id) {
            this.coupons = resBody;
          } else {
            this.couponsService
              .find(cart.coupons.id)
              .pipe(
                map((subRes: HttpResponse<ICoupons>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICoupons[]) => (this.coupons = concatRes));
          }
        });
    });
  }

  updateForm(cart: ICart): void {
    this.editForm.patchValue({
      id: cart.id,
      coupons: cart.coupons,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cart = this.createFromForm();
    if (cart.id !== undefined) {
      this.subscribeToSaveResponse(this.cartService.update(cart));
    } else {
      this.subscribeToSaveResponse(this.cartService.create(cart));
    }
  }

  private createFromForm(): ICart {
    return {
      ...new Cart(),
      id: this.editForm.get(['id'])!.value,
      coupons: this.editForm.get(['coupons'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>): void {
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

  trackById(index: number, item: ICoupons): any {
    return item.id;
  }
}
