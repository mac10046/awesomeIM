import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBusinessDetails } from 'app/shared/model/business-details.model';

type EntityResponseType = HttpResponse<IBusinessDetails>;
type EntityArrayResponseType = HttpResponse<IBusinessDetails[]>;

@Injectable({ providedIn: 'root' })
export class BusinessDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/business-details';

  constructor(protected http: HttpClient) {}

  create(businessDetails: IBusinessDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(businessDetails);
    return this.http
      .post<IBusinessDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(businessDetails: IBusinessDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(businessDetails);
    return this.http
      .put<IBusinessDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBusinessDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBusinessDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(businessDetails: IBusinessDetails): IBusinessDetails {
    const copy: IBusinessDetails = Object.assign({}, businessDetails, {
      inceptionDate:
        businessDetails.inceptionDate && businessDetails.inceptionDate.isValid()
          ? businessDetails.inceptionDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inceptionDate = res.body.inceptionDate ? moment(res.body.inceptionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((businessDetails: IBusinessDetails) => {
        businessDetails.inceptionDate = businessDetails.inceptionDate ? moment(businessDetails.inceptionDate) : undefined;
      });
    }
    return res;
  }
}
