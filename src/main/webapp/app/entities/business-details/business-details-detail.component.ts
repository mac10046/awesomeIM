import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBusinessDetails } from 'app/shared/model/business-details.model';

@Component({
  selector: 'jhi-business-details-detail',
  templateUrl: './business-details-detail.component.html',
})
export class BusinessDetailsDetailComponent implements OnInit {
  businessDetails: IBusinessDetails | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessDetails }) => (this.businessDetails = businessDetails));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
