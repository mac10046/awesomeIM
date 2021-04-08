import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessOffer } from 'app/shared/model/business-offer.model';

@Component({
  selector: 'jhi-business-offer-detail',
  templateUrl: './business-offer-detail.component.html',
})
export class BusinessOfferDetailComponent implements OnInit {
  businessOffer: IBusinessOffer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessOffer }) => (this.businessOffer = businessOffer));
  }

  previousState(): void {
    window.history.back();
  }
}
