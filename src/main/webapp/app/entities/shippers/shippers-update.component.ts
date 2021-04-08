import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IShippers, Shippers } from 'app/shared/model/shippers.model';
import { ShippersService } from './shippers.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

@Component({
  selector: 'jhi-shippers-update',
  templateUrl: './shippers-update.component.html',
})
export class ShippersUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    contactNumber: [],
    address: [],
  });

  constructor(
    protected shippersService: ShippersService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shippers }) => {
      this.updateForm(shippers);

      this.addressService
        .query({ filter: 'shippers-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!shippers.address || !shippers.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(shippers.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });
    });
  }

  updateForm(shippers: IShippers): void {
    this.editForm.patchValue({
      id: shippers.id,
      name: shippers.name,
      contactNumber: shippers.contactNumber,
      address: shippers.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shippers = this.createFromForm();
    if (shippers.id !== undefined) {
      this.subscribeToSaveResponse(this.shippersService.update(shippers));
    } else {
      this.subscribeToSaveResponse(this.shippersService.create(shippers));
    }
  }

  private createFromForm(): IShippers {
    return {
      ...new Shippers(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      contactNumber: this.editForm.get(['contactNumber'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShippers>>): void {
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

  trackById(index: number, item: IAddress): any {
    return item.id;
  }
}
