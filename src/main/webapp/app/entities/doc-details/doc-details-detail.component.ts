import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocDetails } from 'app/shared/model/doc-details.model';

@Component({
  selector: 'jhi-doc-details-detail',
  templateUrl: './doc-details-detail.component.html',
})
export class DocDetailsDetailComponent implements OnInit {
  docDetails: IDocDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docDetails }) => (this.docDetails = docDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
