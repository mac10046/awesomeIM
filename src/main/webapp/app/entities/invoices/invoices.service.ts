import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoices } from 'app/shared/model/invoices.model';

type EntityResponseType = HttpResponse<IInvoices>;
type EntityArrayResponseType = HttpResponse<IInvoices[]>;

@Injectable({ providedIn: 'root' })
export class InvoicesService {
  public resourceUrl = SERVER_API_URL + 'api/invoices';

  constructor(protected http: HttpClient) {}

  create(invoices: IInvoices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoices);
    return this.http
      .post<IInvoices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoices: IInvoices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoices);
    return this.http
      .put<IInvoices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoices>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoices[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invoices: IInvoices): IInvoices {
    const copy: IInvoices = Object.assign({}, invoices, {
      invoiceDate: invoices.invoiceDate && invoices.invoiceDate.isValid() ? invoices.invoiceDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.invoiceDate = res.body.invoiceDate ? moment(res.body.invoiceDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoices: IInvoices) => {
        invoices.invoiceDate = invoices.invoiceDate ? moment(invoices.invoiceDate) : undefined;
      });
    }
    return res;
  }
}
