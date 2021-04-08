import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuotes } from 'app/shared/model/quotes.model';

@Component({
  selector: 'jhi-quotes-detail',
  templateUrl: './quotes-detail.component.html',
})
export class QuotesDetailComponent implements OnInit {
  quotes: IQuotes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quotes }) => (this.quotes = quotes));
  }

  previousState(): void {
    window.history.back();
  }
}
