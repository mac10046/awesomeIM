import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProducts, Products } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IBusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from 'app/entities/business-details/business-details.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart/cart.service';
import { IShipping } from 'app/shared/model/shipping.model';
import { ShippingService } from 'app/entities/shipping/shipping.service';

type SelectableEntity = IBusinessDetails | ICart | IShipping;

@Component({
  selector: 'jhi-products-update',
  templateUrl: './products-update.component.html',
})
export class ProductsUpdateComponent implements OnInit {
  isSaving = false;
  businessdetails: IBusinessDetails[] = [];
  carts: ICart[] = [];
  shippings: IShipping[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    price: [],
    weight: [],
    dimension: [],
    photo1: [],
    photo1ContentType: [],
    photo2: [],
    photo2ContentType: [],
    photo3: [],
    photo3ContentType: [],
    isPhysicalProduct: [],
    maintainInventory: [],
    businessDetails: [],
    cart: [],
    shipping: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productsService: ProductsService,
    protected businessDetailsService: BusinessDetailsService,
    protected cartService: CartService,
    protected shippingService: ShippingService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ products }) => {
      this.updateForm(products);

      this.businessDetailsService.query().subscribe((res: HttpResponse<IBusinessDetails[]>) => (this.businessdetails = res.body || []));

      this.cartService.query().subscribe((res: HttpResponse<ICart[]>) => (this.carts = res.body || []));

      this.shippingService.query().subscribe((res: HttpResponse<IShipping[]>) => (this.shippings = res.body || []));
    });
  }

  updateForm(products: IProducts): void {
    this.editForm.patchValue({
      id: products.id,
      name: products.name,
      description: products.description,
      price: products.price,
      weight: products.weight,
      dimension: products.dimension,
      photo1: products.photo1,
      photo1ContentType: products.photo1ContentType,
      photo2: products.photo2,
      photo2ContentType: products.photo2ContentType,
      photo3: products.photo3,
      photo3ContentType: products.photo3ContentType,
      isPhysicalProduct: products.isPhysicalProduct,
      maintainInventory: products.maintainInventory,
      businessDetails: products.businessDetails,
      cart: products.cart,
      shipping: products.shipping,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('awesomeimApp.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const products = this.createFromForm();
    if (products.id !== undefined) {
      this.subscribeToSaveResponse(this.productsService.update(products));
    } else {
      this.subscribeToSaveResponse(this.productsService.create(products));
    }
  }

  private createFromForm(): IProducts {
    return {
      ...new Products(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      price: this.editForm.get(['price'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      dimension: this.editForm.get(['dimension'])!.value,
      photo1ContentType: this.editForm.get(['photo1ContentType'])!.value,
      photo1: this.editForm.get(['photo1'])!.value,
      photo2ContentType: this.editForm.get(['photo2ContentType'])!.value,
      photo2: this.editForm.get(['photo2'])!.value,
      photo3ContentType: this.editForm.get(['photo3ContentType'])!.value,
      photo3: this.editForm.get(['photo3'])!.value,
      isPhysicalProduct: this.editForm.get(['isPhysicalProduct'])!.value,
      maintainInventory: this.editForm.get(['maintainInventory'])!.value,
      businessDetails: this.editForm.get(['businessDetails'])!.value,
      cart: this.editForm.get(['cart'])!.value,
      shipping: this.editForm.get(['shipping'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducts>>): void {
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
