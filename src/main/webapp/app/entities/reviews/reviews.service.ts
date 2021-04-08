import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReviews } from 'app/shared/model/reviews.model';

type EntityResponseType = HttpResponse<IReviews>;
type EntityArrayResponseType = HttpResponse<IReviews[]>;

@Injectable({ providedIn: 'root' })
export class ReviewsService {
  public resourceUrl = SERVER_API_URL + 'api/reviews';

  constructor(protected http: HttpClient) {}

  create(reviews: IReviews): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reviews);
    return this.http
      .post<IReviews>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reviews: IReviews): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reviews);
    return this.http
      .put<IReviews>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReviews>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReviews[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reviews: IReviews): IReviews {
    const copy: IReviews = Object.assign({}, reviews, {
      reviewDate: reviews.reviewDate && reviews.reviewDate.isValid() ? reviews.reviewDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.reviewDate = res.body.reviewDate ? moment(res.body.reviewDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reviews: IReviews) => {
        reviews.reviewDate = reviews.reviewDate ? moment(reviews.reviewDate) : undefined;
      });
    }
    return res;
  }
}
