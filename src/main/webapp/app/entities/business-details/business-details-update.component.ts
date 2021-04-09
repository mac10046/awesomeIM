import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IBusinessDetails, BusinessDetails } from 'app/shared/model/business-details.model';
import { BusinessDetailsService } from './business-details.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-business-details-update',
  templateUrl: './business-details-update.component.html',
})
export class BusinessDetailsUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  inceptionDateDp: any;

  editForm = this.fb.group({
    id: [],
    businessName: [null, [Validators.required]],
    registeredAddress: [null, [Validators.required]],
    description: [],
    inceptionDate: [null, [Validators.required]],
    gstin: [null, [Validators.required]],
    category: [null, [Validators.required]],
    subCategory: [null, [Validators.required]],
    email: [null, [Validators.required]],
    contactNumber: [null, [Validators.required]],
    managingPersonName: [null, [Validators.required]],
    logo: [],
    logoContentType: [],
    managingPersonImage: [],
    managingPersonImageContentType: [],
    businessType: [null, [Validators.required]],
    upi: [],
    bankName: [],
    ifscCode: [],
    branchName: [],
    user: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected businessDetailsService: BusinessDetailsService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessDetails }) => {
      this.updateForm(businessDetails);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(businessDetails: IBusinessDetails): void {
    this.editForm.patchValue({
      id: businessDetails.id,
      businessName: businessDetails.businessName,
      registeredAddress: businessDetails.registeredAddress,
      description: businessDetails.description,
      inceptionDate: businessDetails.inceptionDate,
      gstin: businessDetails.gstin,
      category: businessDetails.category,
      subCategory: businessDetails.subCategory,
      email: businessDetails.email,
      contactNumber: businessDetails.contactNumber,
      managingPersonName: businessDetails.managingPersonName,
      logo: businessDetails.logo,
      logoContentType: businessDetails.logoContentType,
      managingPersonImage: businessDetails.managingPersonImage,
      managingPersonImageContentType: businessDetails.managingPersonImageContentType,
      businessType: businessDetails.businessType,
      upi: businessDetails.upi,
      bankName: businessDetails.bankName,
      ifscCode: businessDetails.ifscCode,
      branchName: businessDetails.branchName,
      user: businessDetails.user,
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
    const businessDetails = this.createFromForm();
    if (businessDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.businessDetailsService.update(businessDetails));
    } else {
      this.subscribeToSaveResponse(this.businessDetailsService.create(businessDetails));
    }
  }

  private createFromForm(): IBusinessDetails {
    return {
      ...new BusinessDetails(),
      id: this.editForm.get(['id'])!.value,
      businessName: this.editForm.get(['businessName'])!.value,
      registeredAddress: this.editForm.get(['registeredAddress'])!.value,
      description: this.editForm.get(['description'])!.value,
      inceptionDate: this.editForm.get(['inceptionDate'])!.value,
      gstin: this.editForm.get(['gstin'])!.value,
      category: this.editForm.get(['category'])!.value,
      subCategory: this.editForm.get(['subCategory'])!.value,
      email: this.editForm.get(['email'])!.value,
      contactNumber: this.editForm.get(['contactNumber'])!.value,
      managingPersonName: this.editForm.get(['managingPersonName'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      managingPersonImageContentType: this.editForm.get(['managingPersonImageContentType'])!.value,
      managingPersonImage: this.editForm.get(['managingPersonImage'])!.value,
      businessType: this.editForm.get(['businessType'])!.value,
      upi: this.editForm.get(['upi'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      ifscCode: this.editForm.get(['ifscCode'])!.value,
      branchName: this.editForm.get(['branchName'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessDetails>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
