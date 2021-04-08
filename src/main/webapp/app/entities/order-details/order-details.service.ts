import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderDetails } from 'app/shared/model/order-details.model';

type EntityResponseType = HttpResponse<IOrderDetails>;
type EntityArrayResponseType = HttpResponse<IOrderDetails[]>;

@Injectable({ providedIn: 'root' })
export class OrderDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/order-details';

  constructor(protected http: HttpClient) {}

  create(orderDetails: IOrderDetails): Observable<EntityResponseType> {
    return this.http.post<IOrderDetails>(this.resourceUrl, orderDetails, { observe: 'response' });
  }

  update(orderDetails: IOrderDetails): Observable<EntityResponseType> {
    return this.http.put<IOrderDetails>(this.resourceUrl, orderDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
