import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxes } from 'app/shared/model/taxes.model';

@Component({
  selector: 'jhi-taxes-detail',
  templateUrl: './taxes-detail.component.html',
})
export class TaxesDetailComponent implements OnInit {
  taxes: ITaxes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxes }) => (this.taxes = taxes));
  }

  previousState(): void {
    window.history.back();
  }
}
