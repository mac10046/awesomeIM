import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoupons } from 'app/shared/model/coupons.model';

@Component({
  selector: 'jhi-coupons-detail',
  templateUrl: './coupons-detail.component.html',
})
export class CouponsDetailComponent implements OnInit {
  coupons: ICoupons | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ coupons }) => (this.coupons = coupons));
  }

  previousState(): void {
    window.history.back();
  }
}
