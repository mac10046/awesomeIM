import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShippers } from 'app/shared/model/shippers.model';

@Component({
  selector: 'jhi-shippers-detail',
  templateUrl: './shippers-detail.component.html',
})
export class ShippersDetailComponent implements OnInit {
  shippers: IShippers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shippers }) => (this.shippers = shippers));
  }

  previousState(): void {
    window.history.back();
  }
}
