import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBusinessOffer } from 'app/shared/model/business-offer.model';

type EntityResponseType = HttpResponse<IBusinessOffer>;
type EntityArrayResponseType = HttpResponse<IBusinessOffer[]>;

@Injectable({ providedIn: 'root' })
export class BusinessOfferService {
  public resourceUrl = SERVER_API_URL + 'api/business-offers';

  constructor(protected http: HttpClient) {}

  create(businessOffer: IBusinessOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(businessOffer);
    return this.http
      .post<IBusinessOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(businessOffer: IBusinessOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(businessOffer);
    return this.http
      .put<IBusinessOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBusinessOffer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBusinessOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(businessOffer: IBusinessOffer): IBusinessOffer {
    const copy: IBusinessOffer = Object.assign({}, businessOffer, {
      time: businessOffer.time && businessOffer.time.isValid() ? businessOffer.time.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.time = res.body.time ? moment(res.body.time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((businessOffer: IBusinessOffer) => {
        businessOffer.time = businessOffer.time ? moment(businessOffer.time) : undefined;
      });
    }
    return res;
  }
}
