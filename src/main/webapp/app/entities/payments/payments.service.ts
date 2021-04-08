import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPayments } from 'app/shared/model/payments.model';

type EntityResponseType = HttpResponse<IPayments>;
type EntityArrayResponseType = HttpResponse<IPayments[]>;

@Injectable({ providedIn: 'root' })
export class PaymentsService {
  public resourceUrl = SERVER_API_URL + 'api/payments';

  constructor(protected http: HttpClient) {}

  create(payments: IPayments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payments);
    return this.http
      .post<IPayments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payments: IPayments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payments);
    return this.http
      .put<IPayments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayments[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(payments: IPayments): IPayments {
    const copy: IPayments = Object.assign({}, payments, {
      paymentDate: payments.paymentDate && payments.paymentDate.isValid() ? payments.paymentDate.format(DATE_FORMAT) : undefined,
      responseTimestamp:
        payments.responseTimestamp && payments.responseTimestamp.isValid() ? payments.responseTimestamp.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paymentDate = res.body.paymentDate ? moment(res.body.paymentDate) : undefined;
      res.body.responseTimestamp = res.body.responseTimestamp ? moment(res.body.responseTimestamp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payments: IPayments) => {
        payments.paymentDate = payments.paymentDate ? moment(payments.paymentDate) : undefined;
        payments.responseTimestamp = payments.responseTimestamp ? moment(payments.responseTimestamp) : undefined;
      });
    }
    return res;
  }
}
