import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessOpportunity } from 'app/shared/model/business-opportunity.model';

@Component({
  selector: 'jhi-business-opportunity-detail',
  templateUrl: './business-opportunity-detail.component.html',
})
export class BusinessOpportunityDetailComponent implements OnInit {
  businessOpportunity: IBusinessOpportunity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessOpportunity }) => (this.businessOpportunity = businessOpportunity));
  }

  previousState(): void {
    window.history.back();
  }
}
