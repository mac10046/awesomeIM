import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBusinessOpportunity } from 'app/shared/model/business-opportunity.model';

type EntityResponseType = HttpResponse<IBusinessOpportunity>;
type EntityArrayResponseType = HttpResponse<IBusinessOpportunity[]>;

@Injectable({ providedIn: 'root' })
export class BusinessOpportunityService {
  public resourceUrl = SERVER_API_URL + 'api/business-opportunities';

  constructor(protected http: HttpClient) {}

  create(businessOpportunity: IBusinessOpportunity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(businessOpportunity);
    return this.http
      .post<IBusinessOpportunity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(businessOpportunity: IBusinessOpportunity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(businessOpportunity);
    return this.http
      .put<IBusinessOpportunity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBusinessOpportunity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBusinessOpportunity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(businessOpportunity: IBusinessOpportunity): IBusinessOpportunity {
    const copy: IBusinessOpportunity = Object.assign({}, businessOpportunity, {
      startDate:
        businessOpportunity.startDate && businessOpportunity.startDate.isValid()
          ? businessOpportunity.startDate.format(DATE_FORMAT)
          : undefined,
      endDate:
        businessOpportunity.endDate && businessOpportunity.endDate.isValid() ? businessOpportunity.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((businessOpportunity: IBusinessOpportunity) => {
        businessOpportunity.startDate = businessOpportunity.startDate ? moment(businessOpportunity.startDate) : undefined;
        businessOpportunity.endDate = businessOpportunity.endDate ? moment(businessOpportunity.endDate) : undefined;
      });
    }
    return res;
  }
}
