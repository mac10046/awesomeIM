import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShippers } from 'app/shared/model/shippers.model';

type EntityResponseType = HttpResponse<IShippers>;
type EntityArrayResponseType = HttpResponse<IShippers[]>;

@Injectable({ providedIn: 'root' })
export class ShippersService {
  public resourceUrl = SERVER_API_URL + 'api/shippers';

  constructor(protected http: HttpClient) {}

  create(shippers: IShippers): Observable<EntityResponseType> {
    return this.http.post<IShippers>(this.resourceUrl, shippers, { observe: 'response' });
  }

  update(shippers: IShippers): Observable<EntityResponseType> {
    return this.http.put<IShippers>(this.resourceUrl, shippers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShippers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShippers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
