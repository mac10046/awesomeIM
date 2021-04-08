import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomers, Customers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';

@Component({
  selector: 'jhi-customers-update',
  templateUrl: './customers-update.component.html',
})
export class CustomersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    contactNumber: [],
    email: [null, [Validators.required]],
  });

  constructor(protected customersService: CustomersService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customers }) => {
      this.updateForm(customers);
    });
  }

  updateForm(customers: ICustomers): void {
    this.editForm.patchValue({
      id: customers.id,
      name: customers.name,
      contactNumber: customers.contactNumber,
      email: customers.email,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customers = this.createFromForm();
    if (customers.id !== undefined) {
      this.subscribeToSaveResponse(this.customersService.update(customers));
    } else {
      this.subscribeToSaveResponse(this.customersService.create(customers));
    }
  }

  private createFromForm(): ICustomers {
    return {
      ...new Customers(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      contactNumber: this.editForm.get(['contactNumber'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomers>>): void {
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
}
