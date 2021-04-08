import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuotes } from 'app/shared/model/quotes.model';

type EntityResponseType = HttpResponse<IQuotes>;
type EntityArrayResponseType = HttpResponse<IQuotes[]>;

@Injectable({ providedIn: 'root' })
export class QuotesService {
  public resourceUrl = SERVER_API_URL + 'api/quotes';

  constructor(protected http: HttpClient) {}

  create(quotes: IQuotes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quotes);
    return this.http
      .post<IQuotes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(quotes: IQuotes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quotes);
    return this.http
      .put<IQuotes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuotes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuotes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(quotes: IQuotes): IQuotes {
    const copy: IQuotes = Object.assign({}, quotes, {
      quoteDate: quotes.quoteDate && quotes.quoteDate.isValid() ? quotes.quoteDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.quoteDate = res.body.quoteDate ? moment(res.body.quoteDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((quotes: IQuotes) => {
        quotes.quoteDate = quotes.quoteDate ? moment(quotes.quoteDate) : undefined;
      });
    }
    return res;
  }
}
