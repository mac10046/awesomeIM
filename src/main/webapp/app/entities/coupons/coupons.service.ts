import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICoupons } from 'app/shared/model/coupons.model';

type EntityResponseType = HttpResponse<ICoupons>;
type EntityArrayResponseType = HttpResponse<ICoupons[]>;

@Injectable({ providedIn: 'root' })
export class CouponsService {
  public resourceUrl = SERVER_API_URL + 'api/coupons';

  constructor(protected http: HttpClient) {}

  create(coupons: ICoupons): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(coupons);
    return this.http
      .post<ICoupons>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(coupons: ICoupons): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(coupons);
    return this.http
      .put<ICoupons>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICoupons>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICoupons[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(coupons: ICoupons): ICoupons {
    const copy: ICoupons = Object.assign({}, coupons, {
      startDate: coupons.startDate && coupons.startDate.isValid() ? coupons.startDate.format(DATE_FORMAT) : undefined,
      endDate: coupons.endDate && coupons.endDate.isValid() ? coupons.endDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((coupons: ICoupons) => {
        coupons.startDate = coupons.startDate ? moment(coupons.startDate) : undefined;
        coupons.endDate = coupons.endDate ? moment(coupons.endDate) : undefined;
      });
    }
    return res;
  }
}
